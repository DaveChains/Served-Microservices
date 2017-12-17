package com.serveme.payment.service.impl;

import com.serveme.payment.domain.CustomerCharge;
import com.serveme.payment.domain.PaymentDetail;
import com.serveme.payment.enums.ChargeResult;
import com.serveme.payment.enums.ChargeStatus;
import com.serveme.payment.exceptions.PaymentProviderConflictException;
import com.serveme.payment.exceptions.TooManyTriesException;
import com.serveme.payment.payment.ChargeResultWrapper;
import com.serveme.payment.payment.PaymentProvider;
import com.serveme.payment.payment.PaymentProviderFactory;
import com.serveme.payment.persistence.CustomerChargeRepository;
import com.serveme.payment.service.PaymentDetailService;
import com.serveme.payment.service.PaymentProcessorService;
import com.serveme.payment.util.DataUtil;
import com.serveme.payment.util.EnvironmentUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentProcessorServiceImpl implements PaymentProcessorService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final PaymentProvider paymentProvider;


    private final EnvironmentUtil environmentUtil;

    private final CustomerChargeRepository customerChargeRepository;

    private final PaymentDetailService paymentDetailService;

    private final int chargeBulkSize;
    private final int chargeTriesLimit;

    @Inject
    public PaymentProcessorServiceImpl(
            CustomerChargeRepository customerChargeRepository,
            PaymentProviderFactory paymentProviderFactory,
            EnvironmentUtil environmentUtil,
            PaymentDetailService paymentDetailService,
            @Value("${charge.bulkSize: 10}") int chargeBulkSize,
            @Value("${charge.tries.limit: 10}") int chargeTriesLimit){
        this.environmentUtil            = environmentUtil;
        this.paymentProvider = paymentProviderFactory.defaultInstance();
        this.customerChargeRepository = customerChargeRepository;
        this.chargeBulkSize = chargeBulkSize;
        this.paymentDetailService = paymentDetailService;
        this.chargeTriesLimit = chargeTriesLimit;

    }

    @Override
    public List<CustomerCharge> defaultNoChargedList() {
        return noChargedList(chargeBulkSize);
    }

    //TODO FAILED TOO
    @Override
    public List<CustomerCharge> noChargedList(int size) {
        return customerChargeRepository.nextToProcess(chargeTriesLimit, size);
    }

    @Override
    public void defaultProcessNextCharges(){
        processChargeList(defaultNoChargedList());
    }

    @Override
    public void processChargeList(List<CustomerCharge> chargeList){
        if(chargeList.isEmpty()) {
            logger.info("NO charges pending to be processed");
            return;
        }
        List<Long> userIds = chargeList
                .stream()
                .map(CustomerCharge::getUserId)
                .collect(Collectors.toList());

        Map<Long, PaymentDetail> paymentDetailMap = paymentDetailService
                .getPaymentDetailByUserList(userIds)
                .stream()
                .collect(Collectors.toMap(PaymentDetail::getUserId, Function.identity()));

        chargeList.forEach(charge -> {
            PaymentDetail paymentDetail = paymentDetailMap.get(charge.getUserId());
            processChargeSingle(paymentDetail, charge);
        });
    }

    @Override
    public  boolean processChargeSingle(
            PaymentDetail customerPaymentDetail,
            CustomerCharge charge){

        if(ChargeStatus.PAID.equals(charge.getStatus())) {
            printLogResult(charge, ChargeResult.ALREADY_PAID);
            return true;
        }

        try {
            if(charge.getTries() >= chargeTriesLimit) {
                throw new TooManyTriesException(charge.getId(), charge.getTries());
            }
            validatePaymentProvider(charge, customerPaymentDetail);
            ChargeResultWrapper chargeResult = charge.isTestMode() && environmentUtil.isLiveEnv() ?
                    ChargeResultWrapper.testInstance() :
                    paymentProvider.charge(customerPaymentDetail.getCustomerToken(), charge);

            charge.setStatus(ChargeStatus.PAID);
            charge.setChargeResult(chargeResult);
            charge.setTries(charge.getTries() + 1);
            printLogResult(charge, ChargeResult.SUCCESSFUL);
            return true;
        }catch(Exception ex) {
            logger.error(
                    "ERROR in payment for customer {} in order {}\nDetails:\n{}",
                    charge.getId(),
                    charge.getInvoice().getOrderId(),
                    ex);
            charge.setStatus(ChargeStatus.FAILED);
            charge.setChargeError(DataUtil.toJson(ex));
            charge.setTries(charge.getTries() + 1);
            printLogResult(charge, ChargeResult.FAILED);
            return false;
        }finally{

            charge.setUpdatedAt(Instant.now());
            customerChargeRepository.save(charge);
        }

    }


    /**
     * Validates charge, paymentDetail and the provider in charge of processing the charge
     * are compatible
     */
    private void validatePaymentProvider(CustomerCharge charge, PaymentDetail customerPaymentDetail) {
        if(!customerPaymentDetail.getPaymentProviderId().equals(charge.getPaymentProviderId()) ||
                !customerPaymentDetail.getPaymentProviderId().equals(paymentProvider.getId())) {
            throw new PaymentProviderConflictException(
                    charge.getUserId(),
                    charge.getPaymentProviderId(),
                    customerPaymentDetail.getPaymentProviderId(),
                    paymentProvider.getId());
        }
    }

    /**
     * It just prints the result in the log
     */
    private void printLogResult(final CustomerCharge charge, final ChargeResult chargeResult) {

        logger.info(new StringBuilder("PAYMENT RESULT ")
                        .append("\n\tOrder id:\t")
                        .append(charge.getInvoice().getOrderId())
                        .append("\n\tInvoice id:\t")
                        .append(charge.getInvoice().getId())
                        .append("\n\tCharge id:\t")
                        .append(charge.getId())
                        .append("\n\tUser id:\t")
                        .append(charge.getUserId())
                        .append("\n\tResult:\t\t")
                        .append(chargeResult)
                        .toString()
        );
    }



}
