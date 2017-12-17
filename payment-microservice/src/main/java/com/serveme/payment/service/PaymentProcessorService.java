package com.serveme.payment.service;

import com.serveme.payment.domain.CustomerCharge;
import com.serveme.payment.domain.PaymentDetail;

import java.util.List;

public interface PaymentProcessorService {
    /**
     * Retrieves the less recently updated.
     * Size in properties
     * @return List of guest charges
     */
    List<CustomerCharge> defaultNoChargedList();

    /**
     * Retrieves the less recently updated
     * @param size Limitation
     * @return List of guest charges
     */
    List<CustomerCharge> noChargedList(int size);

    /**
     * Process the next charges based on the default configuration
     */
    void defaultProcessNextCharges();

    /**
     * Process the given charges
     * @param nextCharges charges to be process
     */
    void processChargeList(List<CustomerCharge> nextCharges);

    /**
     * Idempotent process to perform the charge using
     * the payment provider(Stripe by default. Fake if live and test mode).
     * If successes, the charge data is saved in the db as paid
     * if fails, the error is saved in db.
     * If it's already paid, just return a successful result.
     * Performs logs
     * @param charge charge to be processed
     * @return true if the payment went successful or was already done. False otherwise
     */
    boolean processChargeSingle(PaymentDetail customerPaymentDetail, CustomerCharge charge);

}
