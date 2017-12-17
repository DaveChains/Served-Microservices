package com.serveme.service.domain;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Edgar on 7/20/2016.
 */
public class DiscountDomainTest {

    @org.junit.Test
    public void testCalcDiscountPercentage() throws Exception {
        List<Long> finishedOrders = new ArrayList<Long>();
        List<DiscountDomain> discounts = new ArrayList<DiscountDomain>();
        double discount;

        // Case nulls
        discount = DiscountDomain.calcDiscountPercentage(null, null).doubleValue();
        System.out.println(discount);
        assertEquals(0.0, discount, 0.01);

        // Case no orders, no discounts
        discount = DiscountDomain.calcDiscountPercentage(finishedOrders, discounts).doubleValue();
        System.out.println(discount);
        assertEquals(0.0, discount, 0.01);

        // Case niche
        DiscountDomain discountA = new DiscountDomain();
        discountA.setInitOrder(1);
        discountA.setEndOrder(1);
        discountA.setPercentage(4000);
        DiscountDomain discountB = new DiscountDomain();
        discountB.setInitOrder(2);
        discountB.setEndOrder(99999);
        discountB.setPercentage(1000);
        DiscountDomain discountC = new DiscountDomain();
        discountC.setInitOrder(3);
        discountC.setEndOrder(3);
        discountC.setDaysCount(7);
        discountC.setPercentage(2000);

        discounts.add(discountA);
        discounts.add(discountB);
        discounts.add(discountC);

        discount = DiscountDomain.calcDiscountPercentage(finishedOrders, discounts).doubleValue();
        System.out.println(discount);
        assertEquals(40, discount, 0.01);

        finishedOrders.add(2L); //simulate one order 2 days ago
        discount = DiscountDomain.calcDiscountPercentage(finishedOrders, discounts).doubleValue();
        System.out.println(discount);
        assertEquals(10, discount, 0.01);

        finishedOrders.add(4L); //simulate another order 4 days ago
        discount = DiscountDomain.calcDiscountPercentage(finishedOrders, discounts).doubleValue();
        System.out.println(discount);
        assertEquals(20, discount, 0.01);

        finishedOrders.add(8L); //simulate another order 8 days ago
        discount = DiscountDomain.calcDiscountPercentage(finishedOrders, discounts).doubleValue();
        System.out.println(discount);
        assertEquals(20, discount, 0.01);

        finishedOrders.add(5L); //simulate another order 5 days ago
        discount = DiscountDomain.calcDiscountPercentage(finishedOrders, discounts).doubleValue();
        System.out.println(discount);
        assertEquals(10, discount, 0.01);

        // Case mizen bouche
        discounts.clear();
        DiscountDomain discountAA = new DiscountDomain();
        discountAA.setInitOrder(1);
        discountAA.setEndOrder(1);
        discountAA.setPercentage(4000);
        DiscountDomain discountBB = new DiscountDomain();
        discountBB.setInitOrder(2);
        discountBB.setEndOrder(3);
        discountBB.setDaysCount(7);
        discountBB.setPercentage(2500);
        DiscountDomain discountCC = new DiscountDomain();
        discountCC.setInitOrder(4);
        discountCC.setEndOrder(99999);
        discountCC.setPercentage(1000);
        discounts.add(discountAA);
        discounts.add(discountBB);
        discounts.add(discountCC);

        finishedOrders.clear();
        discount = DiscountDomain.calcDiscountPercentage(finishedOrders, discounts).doubleValue();
        System.out.println(discount);
        assertEquals(40, discount, 0.01);

        finishedOrders.add(5L); //simulate another order 5 days ago
        discount = DiscountDomain.calcDiscountPercentage(finishedOrders, discounts).doubleValue();
        System.out.println(discount);
        assertEquals(25, discount, 0.01);

        finishedOrders.add(8L); //simulate another order 8 days ago
        discount = DiscountDomain.calcDiscountPercentage(finishedOrders, discounts).doubleValue();
        System.out.println(discount);
        assertEquals(25, discount, 0.01);

        finishedOrders.add(2L); //simulate another order 2 days ago
        discount = DiscountDomain.calcDiscountPercentage(finishedOrders, discounts).doubleValue();
        System.out.println(discount);
        assertEquals(25, discount, 0.01);

        finishedOrders.add(2L); //simulate another order 2 days ago
        discount = DiscountDomain.calcDiscountPercentage(finishedOrders, discounts).doubleValue();
        System.out.println(discount);
        assertEquals(10, discount, 0.01);
    }
}