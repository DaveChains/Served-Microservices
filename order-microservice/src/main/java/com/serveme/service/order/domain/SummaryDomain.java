package com.serveme.service.order.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Edgar on 3/13/2016.
 */
public class SummaryDomain implements Serializable {

    private Timestamp initDate = new Timestamp(0);

    private Timestamp endDate = new Timestamp(0);

    private float lastTransfer;

    private float nextTransfer;

    private float totalVolume;

    private int customers;

    private int totalOrders;

    private float totalTips;

    private List<DayInfo> dayInfos = new ArrayList<DayInfo>();

    public SummaryDomain() {
    }

    public SummaryDomain(List<Order> orders, Timestamp initDate, Timestamp endDate) {
        this.initDate = initDate;
        this.endDate = endDate;
        this.totalOrders = orders.size();
        calcSummary(orders);
    }

    private void calcSummary(List<Order> orders) {
        HashMap<String, String> users = new HashMap<String, String>();
        HashMap<String, DayInfo> days = new HashMap<String, DayInfo>();
        totalVolume = 0.0f;
        totalTips = 0.0f;

        if (initDate.before(endDate)) {
            Timestamp tmpDate = initDate;
            while (tmpDate.before(endDate)) {
                DayInfo dayInfo = new DayInfo();
                String label = formatDate(tmpDate.getTime());
                dayInfo.setLabel(label);
                days.put(label, dayInfo);
                dayInfos.add(dayInfo);
                tmpDate = new Timestamp(tmpDate.getTime() + (24 * 60 * 60 * 1000));
            }
        }

        for (Order order : orders) {
            OrderPeople user = order.getPeople().get(0);
            users.put("" + user.getUserId(), user.getUserName());

            totalVolume += order.getTotal().floatValue(); //todo check if it's subtotal or total
            if (order.getTip() != null) {
                totalTips += order.getTip().floatValue();
            }

            DayInfo dayInfo = days.get(formatDate(order.getArrivalAt()));
            if (dayInfo != null) {
                dayInfo.setOrders(dayInfo.getOrders() + 1);
                dayInfo.setSales(dayInfo.getSales() + order.getTotal().floatValue());
            }
        }

        customers = users.size();
    }

    private String formatDate(String time) {
        return formatDate(Long.valueOf(time));
    }

    private String formatDate(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date tmpDate = new Date(time);
        return formatter.format(tmpDate);
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Timestamp getInitDate() {
        return initDate;
    }

    public void setInitDate(Timestamp initDate) {
        this.initDate = initDate;
    }

    public List<DayInfo> getDayInfos() {
        return dayInfos;
    }

    public void setDayInfos(List<DayInfo> dayInfos) {
        this.dayInfos = dayInfos;
    }

    public int getCustomers() {
        return customers;
    }

    public void setCustomers(int customers) {
        this.customers = customers;
    }

    public float getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(float totalVolume) {
        this.totalVolume = totalVolume;
    }

    public float getNextTransfer() {
        return nextTransfer;
    }

    public void setNextTransfer(float nextTransfer) {
        this.nextTransfer = nextTransfer;
    }

    public float getLastTransfer() {
        return lastTransfer;
    }

    public void setLastTransfer(float lastTransfer) {
        this.lastTransfer = lastTransfer;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public float getTotalTips() {
        return totalTips;
    }

    public void setTotalTips(float totalTips) {
        this.totalTips = totalTips;
    }
}
