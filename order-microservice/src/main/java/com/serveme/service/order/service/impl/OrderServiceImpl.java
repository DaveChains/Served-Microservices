package com.serveme.service.order.service.impl;

import com.serveme.service.order.api.controller.RestaurantOrderAPI;
import com.serveme.service.order.api.dto.input.CreationOrderInputDTO;
import com.serveme.service.order.api.dto.input.OrderedItemInputDTO;
import com.serveme.service.order.api.errors.Errors;
import com.serveme.service.order.builder.OrderBuilder;
import com.serveme.service.order.builder.OrderItemBuilder;
import com.serveme.service.order.builder.RestaurantOrderBuilder;
import com.serveme.service.order.dao.impl.InvoiceDao;
import com.serveme.service.order.dao.impl.OrderDao;
import com.serveme.service.order.domain.*;
import com.serveme.service.order.enums.OrderStatus;
import com.serveme.service.order.enums.SplitBillInvitationStatus;
import com.serveme.service.order.exceptions.*;
import com.serveme.service.order.external.dto.output.DevicesDTO;
import com.serveme.service.order.external.dto.output.DishExDTO;
import com.serveme.service.order.external.dto.output.RestaurantExDTO;
import com.serveme.service.order.external.dto.output.UserExDTO;
import com.serveme.service.order.external.service.NotificationService;
import com.serveme.service.order.external.service.PaymentService;
import com.serveme.service.order.external.service.RestaurantService;
import com.serveme.service.order.external.service.UserService;
import com.serveme.service.order.service.OrderService;
import com.serveme.service.order.util.ArrayUtil;
import com.serveme.service.order.util.api.httpExceptions.PaymentRequiredException;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.jaxrs.JAXRSContract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Davids-iMac on 21/11/15.
 */


@Service
public class OrderServiceImpl implements OrderService {

    private static int TEST_RESTAURANT = 33;

    @Value("${order.time.margin.min}")
    protected int orderTimeMinMargin;

    @Value("${order.time.margin.max}")
    protected int orderTimeMaxMargin;

    @Value("${order.timetoeat}")
    protected int timeToEat;

    @Value("${order.rememberMinutesBefore}")
    protected int rememberMinutesBefore;

    @Value("${order.tablefor.max}")
    protected int orderMaxTableFor;

    @Value("${order.minimumvalue}")
    protected double minimumOrderValue;
    @Inject
    protected RestaurantService restaurantService;
    @Inject
    protected UserService userService;
    @Inject
    protected OrderDao orderDao;
    @Inject
    protected InvoiceDao invoiceDao;
    @Inject
    protected NotificationService notificationService;
    @Inject
    protected RestaurantOrderAPI restaurantOrderAPI;
    Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());
    @Value("${service.payment.host}")
    private String paymentServiceHost;
    @Value("${service.payment.port}")
    private String paymentServicePort;
    private PaymentService paymentService;


    //TODO change this to constructor injection
    @PostConstruct
    public void postConstructor() {
        String url = String.format(
                "http://%s:%s",
                this.paymentServiceHost,
                this.paymentServicePort
        );

        paymentService = Feign.builder()
                .contract(new JAXRSContract())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(PaymentService.class,url);
    }

    /**********************************
     * USER METHODS
     **********************************/

    @Override
    public Order getUserCurrentOrder(long userId) {
        return orderDao.findCurrentOrderByUser(userId);
    }

    @Override
    public Order createOrder(UserExDTO user, long restaurantId, CreationOrderInputDTO orderInputDTO, String token) {

        //Checking user payment method
        if (restaurantId != TEST_RESTAURANT
                && userService.getPaymentMethodForUser(token, user.getId()) == null) {
            throw new PaymentRequiredException("User " + user.getId() + " has no payment method", Errors.PAYMENT_REQUIRED.getError());
        }

        //Getting and checking restaurant
        RestaurantExDTO restaurant = restaurantService.getRestaurant(restaurantId);
        logger.log(Level.INFO, "Creating order for restaurant:\n" + restaurant.toString());
        //Checking if restaurant is not null and does contain menu
        checkRestaurant(restaurantId, restaurant);

        //Checking and retrieving the arrival time
        long arrivalTime = getArrivalTime(orderInputDTO);

        //Checking if restaurant is open for 30 minutes after arrivalTime
        long arrivalPlusEatingTime = arrivalTime + (timeToEat * 60 * 1000);
        if (!restaurantService.isRestaurantOpen(restaurantId, arrivalPlusEatingTime)) {
            throw new RestaurantClosedException(restaurantId, arrivalPlusEatingTime);
        }

        //Checking if restaurant is currently online
        if (!restaurantService.isRestaurantOnline(restaurantId)) {
            throw new RestaurantOfflineException(restaurantId);
        }

        //Checking and retrieving the table for
        int tableFor = getTableFor(orderInputDTO, restaurant.getMaxPeopleByTable());

        //Build list of dish
        List<OrderItem> items = buildOrderedDishList(orderInputDTO, restaurant.getMenu());

        //Checking minimum value for order
        if (!hasMinimumValue(items)) {
            throw new OrderTooSmallException(restaurantId);
        }

        //Build and save order
        Order order = buildOrder(user, orderInputDTO, arrivalTime, tableFor, items, restaurant);
        order.setTestMode(user.isTestUser());
        order.setSubtotal(calcSubtotal(order.getItems()));
        List<Long> finishedOrders = orderDao.countFinishedOrdersByUserAndRestaurantWithSinceDays(user.getId(), restaurantId);
        order.setDiscountPerc(DiscountDomain.calcDiscountPercentage(finishedOrders, restaurantService.getDiscounts(restaurantId)));
        order.setUniqueId(orderDao.getNextIdByRestaurant(restaurantId));
        final Order orderSaved = orderDao.create(order);

        // *** Auto-Accept & finish order from demo restaurant (only for iOS App Store approval)
        if (restaurant.getId() != TEST_RESTAURANT) {
            return orderSaved;
        }

        // AutoAccept after 10 seconds
        Timer timer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                restaurantOrderAPI.adminAcceptOrder(orderSaved.getId());
            }
        });
        timer.setRepeats(false);
        timer.start();

        // AutoFinish after 35 seconds
        Timer timer2 = new Timer(45000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                restaurantOrderAPI.adminFinishOrder(orderSaved.getId(), new BigDecimal(0));
            }
        });
        timer2.setRepeats(false);
        timer2.start();

        return orderSaved;
    }

    private BigDecimal getDiscountPerc(List<Long> finishedOrders, List<DiscountDomain> discounts) {

        Collections.sort(discounts, new Comparator<DiscountDomain>() {
            @Override
            public int compare(DiscountDomain lhs, DiscountDomain rhs) {
                return lhs.getPercentage() > rhs.getPercentage() ? -1 : (lhs.getPercentage() > rhs.getPercentage()) ? 1 : 0;
            }
        });

        for (DiscountDomain discount : discounts) {
            int orderCount = finishedOrders.size();
            if (discount.getDaysCount() > 0) {
                for (long daysSince : finishedOrders) {
                    if (daysSince > discount.getDaysCount()) {
                        orderCount--;
                    }
                }
            }
            if (orderCount + 1 >= discount.getInitOrder()
                    && orderCount + 1 <= discount.getEndOrder()) {
                return new BigDecimal(discount.getPercentage() / 100.00);
            }
        }
        return new BigDecimal(0.0);
    }

    private boolean hasMinimumValue(List<OrderItem> items) {
        BigDecimal total = new BigDecimal(0.0);
        for (OrderItem item : items) {
            total = total.add(item.getDishPrice().multiply(new BigDecimal(item.getNum())));
            if (item.getExtras() != null) {
                for (ExtraDomain extra : item.getExtras()) {
                    total = total.add(extra.getPrice());
                }
            }
        }

        return (total.doubleValue() >= minimumOrderValue);
    }


    @Override
    public void rateOrder(long userId, String orderId, int rating, String comments, String improvement) {

        Order order = this.getOrderForReview(orderId);

        List<OrderPeople> invitedPeople = order.getPeople();

        if (invitedPeople == null) throw new NotInvitedToOrderException(orderId);

        Optional<OrderPeople> invitedPeopleResult = invitedPeople.stream().filter(invitedGuy -> invitedGuy.getUserId() == userId).findFirst();

        if (!invitedPeopleResult.isPresent()) throw new NotInvitedToOrderException(orderId);

        OrderPeople invitedUser = invitedPeopleResult.get();

        if (invitedUser.getRating() >= 0) throw new OrderAlreadyRatedException(orderId);

        if (rating <= 0) rating = 0;
        if (rating >= 5) rating = 5;

        invitedUser.setRating(rating);
        invitedUser.setReview(comments);
        invitedUser.setReviewImprovement(improvement);
        order.setStatus(OrderStatus.CLIENT_REVIEWED);

        this.orderDao.update(order);

    }

    /**
     * @param tip is now ignored
     */
    @Override
    public void updateTipAndPayServiceCharge(String orderId, BigDecimal tip, boolean payServiceCharge) {

        Order order = this.getOrderForReview(orderId);
        order.setTip(new BigDecimal(0.0));
        order.setPayServiceCharge(payServiceCharge);
        BigDecimal tmp = !payServiceCharge ? order.getTotal().subtract(order.getServiceCharge()) : order.getTotal();
        if (!payServiceCharge) {
            order.setServiceCharge(new BigDecimal(0));
            order.setServiceChargePerc(new BigDecimal(0));
        }
        order.setTotal(tmp);
        this.orderDao.update(order);

    }

    public List<List<Long>> getNumberOfOrdersByRestaurant(long userId, List<Long> resraurantIds) {
        List<List<Long>> ordersCount = new ArrayList<List<Long>>();
        for (int i = 0; i < resraurantIds.size(); i++) {
            ordersCount.add(orderDao.countFinishedOrdersByUserAndRestaurantWithSinceDays(userId, resraurantIds.get(i)));
        }
        return ordersCount;
    }

    @Override
    public Map<String, List> splitBill(Order order, List<String> phones) {

        List<UserExDTO> users = userService.getUserListFromPhone(phones);

        if (users != null) {


            /**
             * finding who phones are not still users
             */
            List<String> notFoundPhones = new LinkedList<>();
            phones.forEach(phone -> {
                if (users.stream().noneMatch(user -> user.getPhoneNumber() != null && user.getPhoneNumber().equals(phone))) {
                    notFoundPhones.add(phone);
                }

            });


            /**
             * Getting list of active orders the invited users
             */
            List<Long> userIdList = new LinkedList<>();
            users.forEach(user -> userIdList.add(user.getId()));
            List<Order> orderFromUsers = orderDao.getActiveOrdersFromUserList(userIdList);

            /**
             * User is added to "canBeInvited" if hasn't got any active order and it's not already in the this order,
             * otherwise is added to "alreadyInvitedToAnOrder"
             */
            List<UserExDTO> canBeInvited = new LinkedList<>();
            List<UserExDTO> alreadyInvitedToAnOrder = new LinkedList<>();
            checkUserInvitationAvailabillity(order, users, orderFromUsers, canBeInvited, alreadyInvitedToAnOrder);

            if (!ArrayUtil.nullEmpty(canBeInvited)) {

                /**
                 * Updating order with new invitations
                 */
                addInvitationsToOrder(order, canBeInvited);
                if (!orderDao.update(order)) {
                    throw new RuntimeException("Internal problem, users couldn't be invited");
                }
            }
            Map<String, List> result = new HashMap<>();
            result.put("not_found", notFoundPhones);
            result.put("with_active_order", alreadyInvitedToAnOrder);
            result.put("invited", canBeInvited);
            return result;

        } else {
            /**
             * No phone has been found in the user list
             */
            Map<String, List> result = new HashMap<>();
            result.put("not_found", phones);
            result.put("with_active_order", new LinkedList<>());
            result.put("invited", new LinkedList<>());
            return result;

        }

    }

    protected void checkUserInvitationAvailabillity(Order order, List<UserExDTO> users, List<Order> orderFromUsers, List<UserExDTO> canBeInvited, List<UserExDTO> alreadyInvitedToAnOrder) {
        users.forEach(user -> {
            if ((!ArrayUtil.nullEmpty(orderFromUsers)) && orderFromUsers.stream().anyMatch(orderItem -> (!ArrayUtil.nullEmpty(orderItem.getPeople())) && orderItem.getPeople().stream().anyMatch(people -> people.getUserId() == user.getId()))) {
                alreadyInvitedToAnOrder.add(user);

            } else {

                if ((!ArrayUtil.nullEmpty(order.getPeople())) && order.getPeople().stream().noneMatch(orderPeople -> orderPeople.getUserId() == user.getId())) {
                    canBeInvited.add(user);

                } else {
                    alreadyInvitedToAnOrder.add(user);

                }
            }
        });
    }

    protected void addInvitationsToOrder(Order order, List<UserExDTO> canBeInvited) {
        if (ArrayUtil.nullEmpty(order.getPeople())) {
            order.setPeople(new ArrayList<>());
        }

        canBeInvited.forEach(userToInvite -> {
            OrderPeople newOrderPeople = new OrderPeople();
            newOrderPeople.setUserId(userToInvite.getId());
            newOrderPeople.setStatus(SplitBillInvitationStatus.PENDING);
            newOrderPeople.setFound(true);
            newOrderPeople.setInvitedAt(String.valueOf(System.currentTimeMillis()));
            newOrderPeople.setAcceptedAt(String.valueOf(System.currentTimeMillis()));
            newOrderPeople.setUserName(userToInvite.getFirstName());
            newOrderPeople.setUserSurname(userToInvite.getSurname());
            newOrderPeople.setUserPhoneNumber(userToInvite.getPhoneNumber());
            order.getPeople().add(newOrderPeople);

        });
    }


    @Override
    public Order getById(String id, long userId) {
        return orderDao.findByIdAndUser(id, userId);
    }

    @Override
    public Order getById(String id) {
        return orderDao.getById(id);
    }


    @Override
    public long countUserHistory(long userId) {
        return orderDao.countFinishedOrdersByUser(userId);
    }

    @Override
    public List<Order> getUserHistory(long userId, int from, int size) {
        return orderDao.findHistoryByUser(userId, from, size);
    }


    /**********************************
     * RESTAURANT METHODS
     **********************************/

    @Override
    public long countRestaurantHistory(long restaurantId) {
        return orderDao.countFinishedOrdersByRestaurant(restaurantId);
    }

    @Override
    public long countRestaurantPending(long restaurantId) {
        return orderDao.countPendingOrdersByRestaurant(restaurantId);
    }

    @Override
    public long countRestaurantAccepted(long restaurantId) {
        return orderDao.countAcceptedOrdersByRestaurant(restaurantId);
    }

    @Override
    public long countRestaurantActive(long restaurantId) {
        return orderDao.countActiveOrdersByRestaurant(restaurantId);
    }

    @Override
    public long countRestaurantFinished(long restaurantId, long startOfDay) {
        return orderDao.countFinishedOrdersByRestaurant(restaurantId, startOfDay);
    }

    @Override
    public List<Order> getRestaurantHistory(long restaurantId, int from, int size) {
        return orderDao.findHistoryByRestaurant(restaurantId, from, size);
    }

    @Override
    public List<Order> getRestaurantPendingOrders(long restaurantId, int from, int size) {
        return orderDao.findPendingListByRestaurant(restaurantId, from, size);
    }

    @Override
    public List<Order> getRestaurantAcceptedOrders(long restaurantId, int from, int size) {
        return orderDao.findAcceptedListByRestaurant(restaurantId, from, size);
    }

    @Override
    public List<Order> getRestaurantActiveOrders(long restaurantId, int from, int size) {
        return orderDao.findActiveListByRestaurant(restaurantId, from, size);
    }

    @Override
    public List<Order> getRestaurantFinishedOrders(long restaurantId, int from, int size, long startOfDay) {
        return orderDao.findFinishedListByRestaurant(restaurantId, from, size, startOfDay);
    }

    @Override
    public List<Order> getRestaurantFinishedOrders(long restaurantId, long initDate, long endDate) {
        return orderDao.findFinishedListByRestaurant(restaurantId, initDate, endDate);
    }

    @Override
    public List<Order> getAllHistory(int from, int size) {
        return orderDao.findAllHistory(from, size);
    }

    @Override
    public List<Order> getAllPendingOrders(int from, int size) {
        return orderDao.findAllPendingList(from, size);
    }

    @Override
    public List<Order> getAllAcceptedOrders(int from, int size) {
        return orderDao.findAllAcceptedList(from, size);
    }

    @Override
    public List<Order> getAllActiveOrders(int from, int size) {
        return orderDao.findAllActiveList(from, size);
    }

    @Override
    public boolean acceptOrder(String orderId, long restaurantId) {

        Order order = this.orderDao.getById(orderId);
        logger.log(Level.INFO, "Accepting Order: " + orderId);

        if (order == null) throw new OrderNotFoundException(orderId);
        if (order.getRestaurant().getId() != restaurantId) throw new RestaurantNotAuthorizedException(orderId);

        if (OrderStatus.PENDING.equals(order.getStatus())) {
            logger.log(Level.INFO, orderId + " PENDING...becoming accepted");
            order = calcAndSetAccepted(order);
            return this.orderDao.update(order);
        } else if (OrderStatus.ACCEPTED.equals(order.getStatus())) {
            logger.log(Level.INFO, orderId + " already ACCEPTED");
            return true;
        } else {
            throw new OrderCannotBeAcceptedException(order.getStatus());
        }
    }

    @Override
    public boolean declineOrder(String orderId, long restaurantId) {
        Order order = this.orderDao.getById(orderId);
        if (order == null) throw new OrderNotFoundException(orderId);
        if (order.getRestaurant().getId() != restaurantId) throw new RestaurantNotAuthorizedException(orderId);

        if (OrderStatus.PENDING.toString().equals(order.getStatus().toString())) {
            order.setStatus(OrderStatus.DECLINED);
            order.setDeclinedAt(String.valueOf(new Date().getTime()));
            return this.orderDao.update(order);
        } else if (OrderStatus.DECLINED.equals(order.getStatus())) {
            return true;
        } else {
            throw new OrderCannotBeDeclinedException(order.getStatus());
        }
    }

    @Override
    public boolean cancelOrder(String orderId, long restaurantId) {
        Order order = this.orderDao.getById(orderId);
        if (order == null) throw new OrderNotFoundException(orderId);
        if (order.getRestaurant().getId() != restaurantId) throw new RestaurantNotAuthorizedException(orderId);

        if (OrderStatus.ACCEPTED.toString().equals(order.getStatus().toString())
                && Long.parseLong(order.getRequestedAt()) + (5 * 60 * 1000) > System.currentTimeMillis()) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setCancelledAt(String.valueOf(new Date().getTime()));
            return this.orderDao.update(order);
        } else if (OrderStatus.CANCELLED.equals(order.getStatus())) {
            return true;
        } else {
            throw new OrderCannotBeDeclinedException(order.getStatus());
        }
    }

    @Override
    public boolean finishOrder(String orderId, long restaurantId, BigDecimal orderedAtRestaurant) {
        Order order = this.orderDao.getById(orderId);
        if (order == null) throw new OrderNotFoundException(orderId);
        if (order.getRestaurant().getId() != restaurantId) throw new RestaurantNotAuthorizedException(orderId);

        if (OrderStatus.ACCEPTED.equals(order.getStatus())) {
            order = calcAndSetFinished(order, orderedAtRestaurant);
            return this.orderDao.update(order);
        } else if (OrderStatus.FINISHED.equals(order.getStatus())) {
            return true;
        } else {
            throw new OrderCannotBeFinishedException(order.getStatus());
        }
    }

    @Override
    public List<Invoice> getInvoices(long restaurantId, int from, int limit) {
        return invoiceDao.findByRestaurant(restaurantId, from, limit);
    }

    @Override
    public List<Order> getNotCharged(int max) {
        return orderDao.findAllNotCharged(max);
    }

    @Override
    public void saveCharge(Order order, BigDecimal amount, String confirmation) {
        order.setOrderCharged(true);
        order.setDateOfCharge(String.valueOf(new Date().getTime()));
        order.setAmountCharged(amount.divide(new BigDecimal(100)));
        order.setChargeConfirmationNumber(confirmation);
        orderDao.update(order);
    }


    /********************************************************
     * ADMIN METHODS
     *********************************************************/
    @Override
    public boolean acceptOrder(String orderId) {

        logger.log(Level.INFO, orderId + " : trying to be accepted");
        Order order = this.orderDao.getById(orderId);
        logger.log(Level.INFO, "ORDER \n" + order);
        if (order == null) throw new OrderNotFoundException(orderId);

        if (OrderStatus.PENDING.equals(order.getStatus())) {
            logger.log(Level.INFO, orderId + " Status : pending.\nAccepting");
            order = calcAndSetAccepted(order);
            return this.orderDao.update(order);
        } else if (OrderStatus.ACCEPTED.equals(order.getStatus())) {
            logger.log(Level.INFO, orderId + " already accepted");
            return true;
        } else {
            throw new OrderCannotBeAcceptedException(order.getStatus());
        }
    }

    @Override
    public boolean declineOrder(String orderId) {
        Order order = this.orderDao.getById(orderId);
        if (order == null) throw new OrderNotFoundException(orderId);

        if (OrderStatus.PENDING.toString().equals(order.getStatus().toString())) {
            order.setStatus(OrderStatus.DECLINED);
            order.setDeclinedAt(String.valueOf(new Date().getTime()));
            return this.orderDao.update(order);
        } else if (OrderStatus.DECLINED.equals(order.getStatus())) {
            return true;
        } else {
            throw new OrderCannotBeDeclinedException(order.getStatus());
        }
    }

    @Override
    public boolean cancelOrder(String orderId) {
        Order order = this.orderDao.getById(orderId);
        if (order == null) throw new OrderNotFoundException(orderId);

        if (OrderStatus.ACCEPTED.toString().equals(order.getStatus().toString())) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setCancelledAt(String.valueOf(new Date().getTime()));
            return this.orderDao.update(order);
        } else if (OrderStatus.CANCELLED.equals(order.getStatus())) {
            return true;
        } else {
            throw new OrderCannotBeCanceledException(order.getStatus());
        }
    }

    @Override
    public boolean finishOrder(String orderId, BigDecimal orderedAtRestaurant) {
        Order order = this.orderDao.getById(orderId);
        if (order == null) throw new OrderNotFoundException(orderId);

        //Todo refresh restaurant is necessary?
        if (OrderStatus.ACCEPTED.equals(order.getStatus())) {
            order = calcAndSetFinished(order, orderedAtRestaurant);
            return this.orderDao.update(order);
        } else if (OrderStatus.FINISHED.equals(order.getStatus())) {
            return true;
        } else {
            throw new OrderCannotBeFinishedException(order.getStatus());
        }
    }

    @Override
    public void generateInvoicesForAllRestaurants() {
        logger.log(Level.INFO, "NO generating invoices because is deprecated...");
//        RestaurantsIdsDTO restaurants = restaurantService.getAllRestaurants();
//        long generationDate = System.currentTimeMillis();
//        for (int i = 0; i < restaurants.getRestaurantIds().size(); i++) {
//            for (int j = 0; j < 3; j++) {
//                boolean success = generateNewInvoice(generationDate, restaurants.getRestaurantIds().get(i));
//                if (success) {
//                    logger.log(Level.INFO, "Invoice generated for restaurant id " + restaurants.getRestaurantIds().get(i));
//                    break;
//                }
//            }
//        }
    }

    @Override
    public void invoiceFinishedOrders(int bulkSize) {

        List<Order> finishedOrders = orderDao.findPendingToSynchronize(bulkSize);

        if (finishedOrders != null) {
            Function<Order, Order> invoiceFunc = (order) -> {
                try {
                    paymentService.invoiceOrder(order);
                    return order;
                } catch (Exception ex) {
                    logger.log(Level.WARNING, ex.getMessage(), ex);
                    return null;
                }
            };
            finishedOrders
                    .stream()
                    .map(invoiceFunc)
                    .forEach(this::updatePaymentSync);
        }
    }


    private void updatePaymentSync(Order order) {
        if (order != null) {
            order.setPaymentSync(true);
            orderDao.update(order);
        }
    }

    /*
        @Override
        @Scheduled(fixedDelay = 300000)
        public void sendEmailsToFinishedOrders() {
            logger.log(Level.INFO, "Registering emails to finished orders...");
            List<Order> orders = orderDao.findAllEmailNotSent();
            for (int i = 0; i < orders.size(); i++) {
                logger.log(Level.INFO, "Registering email.");
                Mail mail = generateMail(orders.get(i));
                Object object = notificationService.registerMail(mail);
                orders.get(i).setEmailSent(true);
                orderDao.update(orders.get(i));
            }
        }
    */
    public void setInvoiceToTransferred(String invoiceId) {
        Invoice invoice = invoiceDao.findById(invoiceId);
        invoice.setTransferred(true);
        invoiceDao.update(invoice);
    }

    public void rememberOrder(DevicesDTO devices, Order order, int minutesRemainingToBookingTime) {
        String title = "Your order at " + order.getRestaurant().getName();
        String message = "Your booking at " + order.getRestaurant().getName() +
                " is in " + rememberMinutesBefore + " minutes. We are waiting for you!";
        int timerTime = (minutesRemainingToBookingTime - rememberMinutesBefore) * 60;
        Timer timer = new Timer(timerTime * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Order updatedOrder = orderDao.getById(order.getId());
                if (!updatedOrder.getStatus().equals(OrderStatus.ACCEPTED)) {
                    return;
                }
                notificationService.notificateRememberOrder(devices, title, message);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void autocancelOrderIfNoResponseFromRestaurant(
            DevicesDTO devices,
            DevicesDTO restaurantDevice,
            DevicesDTO adminDevices,
            Order order) {
        Timer timer = new Timer(60 * 5 * 1000, event -> {
            Order updatedOrder = orderDao.getById(order.getId());
            if (!updatedOrder.getStatus().equals(OrderStatus.PENDING)) {
                return;
            }
            updatedOrder.setStatus(OrderStatus.NO_ANSWER);
            orderDao.update(updatedOrder);
            notificationService.notificateOrderAutoCanceled(
                    devices,
                    restaurantDevice,
                    adminDevices,
                    order.getId());
        });
        timer.setRepeats(false);
        timer.start();
    }

    /********************************************************
     * PRIVATE METHODS
     *********************************************************/

    protected boolean generateNewInvoice(long generationDate, long restaurantId) {
        List<Order> invoiceOrders = getOrdersForNewInvoice(restaurantId);
        invoiceOrders = Order.removeTestOrders(invoiceOrders);
        Invoice invoice = getInvoice(generationDate, invoiceOrders, restaurantId);
        if (invoice == null) {
            return false;
        }
        invoice = invoiceDao.create(invoice);
        return invoice != null;
    }

    protected Mail generateMail(Order order) {
        UserExDTO user = userService.getUserFromId(order.getOwnerId());
        if ("".equals(user.getEmail()) || user.getEmail() == null) {
            return null;
        }
        Mail mail = new Mail();
        mail.setFrom("Order Service");
        mail.setReceiver(user.getEmail());
        mail.setType("Order Bill");
        mail.setSubject("Bill for your order at " + order.getRestaurant().getName());
        mail.setContent(generateMailContent(order));

        return mail;
    }

    protected String generateMailContent(Order order) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String contentHTML = "This is the bill for your order at " + order.getRestaurant().getName() + " on " + dateFormat.format(date) + ".";
        contentHTML += "<br/><br/>";
        for (int i = 0; i < order.getItems().size(); i++) {
            OrderItem item = order.getItems().get(i);
            BigDecimal total = item.getDishPrice().multiply(BigDecimal.valueOf(item.getNum())).setScale(2, RoundingMode.HALF_UP);
            contentHTML += item.getNum() + "x " + item.getDishName() + ": £" + total + "<br/>";
        }
        contentHTML += "<br/>Subtotal: £" + order.getSubtotal().setScale(2, RoundingMode.HALF_UP);
        contentHTML += "<br/>Service Charge: £" + order.getServiceCharge().setScale(2, RoundingMode.HALF_UP);
        contentHTML += "<br/><strong>TOTAL: £" + order.getTotal().setScale(2, RoundingMode.HALF_UP) + "</strong>";
        return contentHTML;
    }

    protected Invoice getInvoice(long generationDate, List<Order> orders, long restaurantId) {
        BigDecimal total = new BigDecimal(0);
        BigDecimal totalTips = new BigDecimal(0);
        BigDecimal totalService = new BigDecimal(0);
        List<InvoiceLine> invoiceLines = new ArrayList<InvoiceLine>();
        for (Order order : orders) {
            InvoiceLine invoiceLine = getInvoiceLine(order);
            invoiceLines.add(invoiceLine);
            total = total.add(order.getTotal());
            if (order.getTip() != null) {
                totalTips = totalTips.add(order.getTip());
            }
            if (order.getServiceCharge() != null) {
                totalService = totalService.add(order.getServiceCharge());
            }
        }
        Invoice invoice = new Invoice();
        invoice.setTransferred(false);
        invoice.setTotalOrders(orders.size());
        invoice.setInvoiceItems(invoiceLines);
        invoice.setTotal(total);
        invoice.setTotalService(totalService);
        invoice.setTotalTips(totalTips);
        invoice.setGenerationDate(String.valueOf(generationDate));
        invoice.setInitDate(String.valueOf(generationDate - 7 * 24 * 60 * 60 * 1000)); //one week
        invoice.setEndDate(String.valueOf(generationDate));
        invoice.setRestaurantId(restaurantId);
        return invoice;
    }

    protected InvoiceLine getInvoiceLine(Order order) {

        InvoiceLine invoiceLine = new InvoiceLine();
        invoiceLine.setOrderId(order.getId());
        invoiceLine.setServiceTotal(order.getServiceCharge());
        invoiceLine.setTipTotal(order.getTip());
        invoiceLine.setTotal(order.getTotal());
        invoiceLine.setDate(order.getFinishedAt());

        return invoiceLine;
    }

    protected List<Order> getOrdersForNewInvoice(long restaurantId) {
        List<Invoice> lastInvoices = invoiceDao.findByRestaurant(restaurantId, 0, 1);
        if (lastInvoices.size() > 0) {
            Invoice lastGeneratedInvoice = lastInvoices.get(0);
            Long initDateForOrders = Long.parseLong(lastGeneratedInvoice.getGenerationDate()) + 1;
            Long endDateForOrders = new Date().getTime();
            List<Order> ordersThatWillBeIncludedInTheInvoice = orderDao.findFinishedListByRestaurant(restaurantId,
                    initDateForOrders, endDateForOrders);
            return ordersThatWillBeIncludedInTheInvoice;
        }

        List<Order> ordersThatWillBeIncludedInTheInvoice = orderDao.findAllHistory(0, Integer.MAX_VALUE);
        return ordersThatWillBeIncludedInTheInvoice;
    }

    protected void checkRestaurant(long restaurantId, RestaurantExDTO restaurant) {
        if (restaurant == null) throw new RestaurantNotFoundException(restaurantId);
        if (restaurant.getMenu() == null) throw new NoMenuException(restaurantId);
    }

    private Order calcAndSetAccepted(Order order) {
        //refreshing restaurant data if needed
        if (order.getRestaurant() == null) {
            order.setRestaurant(restaurantService.getRestaurantData(order.getRestaurant().getId()));
        }
        if (order.getServiceChargePerc() == null) {
            order.setServiceChargePerc(restaurantService.getRestaurantData(order.getRestaurant().getId()).getServiceChargePerc());
        }

        order.setSubtotal(calcSubtotal(order.getItems()));
        order.setServiceCharge(order.getSubtotal().multiply(order.getServiceChargePerc().divide(new BigDecimal(100))));
        order.setTotal(order.getSubtotal().add(order.getServiceCharge()));

        order.setStatus(OrderStatus.ACCEPTED);
        order.setAcceptedAt(String.valueOf(new Date().getTime()));
        return order;
    }

    private Order calcAndSetFinished(Order order, BigDecimal orderedAtRestaurant) {
        //refreshing restaurant data if needed
        //todo what is the logic of this :S
        if (order.getRestaurant() == null) {
            order.setRestaurant(restaurantService.getRestaurantData(order.getRestaurant().getId()));
        }
        if (order.getServiceChargePerc() == null) {
            order.setServiceChargePerc(restaurantService.getRestaurantData(order.getRestaurant().getId()).getServiceChargePerc());
        }

        if (orderedAtRestaurant != null && orderedAtRestaurant.doubleValue() > 0.0) {
            order.setOrderedAtRestaurant(orderedAtRestaurant);
        } else {
            order.setOrderedAtRestaurant(new BigDecimal(0.0));
        }

        order.setEmailSent(false);
        order.setStatus(OrderStatus.FINISHED);
        order.setFinishedAt(String.valueOf(new Date().getTime()));

        //todo remove this code if orderedAtRestaurant is 0?
        order.setSubtotal(calcSubtotal(order.getItems()).add(order.getOrderedAtRestaurant()));
        order.setServiceCharge(order.getSubtotal().multiply(order.getServiceChargePerc().divide(new BigDecimal(100))));
        if (order.getDiscountPerc() == null) order.setDiscountPerc(new BigDecimal(0.0));
        BigDecimal tmp = order.getSubtotal();
        BigDecimal discount = tmp.multiply(order.getDiscountPerc()).divide(new BigDecimal(100.0));
        order.setDiscount(discount);
        order.setTotal(tmp.subtract(discount).add(order.getServiceCharge()));
        //end todo
        return order;
    }

    private BigDecimal calcSubtotal(List<OrderItem> items) {
        BigDecimal subtotal = new BigDecimal(0);
        for (OrderItem item : items) {
            subtotal = subtotal.add(item.getDishPrice().multiply(new BigDecimal(item.getNum())));
            if (item.getExtras() != null) {
                for (ExtraDomain extraDomain : item.getExtras()) {
                    subtotal = subtotal.add(extraDomain.getPrice());
                }
            }
        }
        return subtotal;
    }

    protected long getArrivalTime(CreationOrderInputDTO orderInputDTO) {
        /**
         * Checking arrival time
         */
        if (orderInputDTO.getArrivalMinutes() < orderTimeMinMargin) {
            throw new ArrivalMinTimeException(orderTimeMinMargin, orderInputDTO.getArrivalMinutes());

        } else if (orderInputDTO.getArrivalMinutes() > orderTimeMaxMargin) {
            throw new ArrivalMaxTimeException(orderTimeMaxMargin, orderInputDTO.getArrivalMinutes());

        }
        long roundedFinalTime = getRoundedCurrentTime(new Date()) + orderInputDTO.getArrivalMinutes() * 60 * 1000;
        return getRoundedCurrentTime(new Date(roundedFinalTime));
    }

    protected long getRoundedCurrentTime(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        int unroundedMinutes = calendar.get(Calendar.MINUTE);
        int mod = unroundedMinutes % 5;
        calendar.set(Calendar.MINUTE, unroundedMinutes - mod);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


    protected int getTableFor(CreationOrderInputDTO orderInputDTO, int maxPeopleByTable) {
        int tableFor = 1;
        if (orderInputDTO.getTableFor() > 0) tableFor = orderInputDTO.getTableFor();
        if (orderInputDTO.getTableFor() > maxPeopleByTable) tableFor = maxPeopleByTable;
        return tableFor;
    }


    protected List<OrderItem> buildOrderedDishList(CreationOrderInputDTO orderInputDTO, Map<String, List<DishExDTO>> menu) {

        List<DishExDTO> dishList = menuAsDishList(menu);
        List<OrderedItemInputDTO> orderDishList = orderInputDTO.getItems();

        //checking all the ordered dishes are in the menu
        for (int i = 0; i < orderDishList.size(); i++) {
            DishExDTO item = new DishExDTO();
            item.setId(orderDishList.get(i).getDishId());
            if (!dishList.contains(item)) {
                throw new NoMatchingDishException(item.getId());
            }
        }

        //Build the list
        List<OrderItem> items = new LinkedList<>();
        orderInputDTO.getItems().forEach(item -> {
            DishExDTO dish = dishList.get(dishList.indexOf(new DishExDTO(item.getDishId())));
            //todo validate extras
            if (!dish.isAvailable()) {
                throw new DishNotAvailableException(dish.getId());
            }
            ArrayList<ExtraDomain> extras = new ArrayList<ExtraDomain>();
            if (item.getExtras() != null) {
                for (Long extraId : item.getExtras()) {
                    ExtraDomain newExtra = findExtra(dish.getExtras(), extraId);
                    if (newExtra != null) extras.add(newExtra);
                }
            }

            OrderItem orderItem = new OrderItemBuilder()
                    .setDish(dish)
                    .setNum(item.getNum())
                    .setComments(item.getComments())
                    .setStarter(item.isStarter())
                    .setOption(item.getOption())
                    .setExtras(extras)
                    .build();

            items.add(orderItem);
        });
        return items;
    }

    private ExtraDomain findExtra(List<ExtraDomain> extras, long extraId) {
        for (ExtraDomain extra : extras) {
            if (extra.getId() == extraId)
                return extra;
        }
        return null;
    }

    protected Order buildOrder(UserExDTO user, CreationOrderInputDTO orderInputDTO, long arrivalTime, int tableFor, List<OrderItem> items, RestaurantExDTO restaurant) {

        RestaurantOrder restaurantOrder = new RestaurantOrderBuilder().setRestaurant(restaurant).build();

        return new OrderBuilder()
                .setArrivalTime(arrivalTime)
                .setTableFor(tableFor)
                .setRestaurant(restaurantOrder)
                .setItems(items)
                .setSplitBillInvitations(getFirstSplitBillInvitations(user))
                .setOwner(user)
                .setComments(orderInputDTO.getComments())
                .setObservation(orderInputDTO.getObservation())
                .build();
    }

    protected List<DishExDTO> menuAsDishList(Map<String, List<DishExDTO>> menu) {

        List<DishExDTO> dishList = new ArrayList<>();

        Iterator<Map.Entry<String, List<DishExDTO>>> iter = menu.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, List<DishExDTO>> entry = iter.next();
            dishList.addAll(entry.getValue());
        }
        return dishList;

    }

    protected Order getOrderForReview(String orderId) {
        Order order = this.orderDao.getById(orderId);
        if (order == null)
            throw new OrderNotFoundException(orderId);

        if (!OrderStatus.FINISHED.equals(order.getStatus())) throw new CannotReviewOrderYetException(order.getStatus());
        return order;
    }

    protected List<OrderPeople> getFirstSplitBillInvitations(UserExDTO user) {
        List<OrderPeople> splitBillInvitation = new LinkedList<>();
        OrderPeople splitBill = new OrderPeople();
        splitBill.setUserId(user.getId());
        splitBill.setStatus(SplitBillInvitationStatus.ACCEPTED);
        splitBill.setFound(true);
        splitBill.setInvitedAt(String.valueOf(System.currentTimeMillis()));
        splitBill.setAcceptedAt(String.valueOf(System.currentTimeMillis()));
        splitBill.setUserName(user.getFirstName());
        splitBill.setUserSurname(user.getSurname());
        splitBill.setUserPhoneNumber(user.getPhoneNumber());
        splitBillInvitation.add(splitBill);
        return splitBillInvitation;
    }
}
