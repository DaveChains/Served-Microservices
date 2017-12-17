package com.serveme.payment.service.impl;


import com.serveme.payment.domain.PaymentDetail;
import com.serveme.payment.exceptions.CardNotFoundException;
import com.serveme.payment.exceptions.CardPersistenceException;
import com.serveme.payment.exceptions.CardProviderException;
import com.serveme.payment.persistence.PaymentDetailRepository;
import com.serveme.payment.payment.PaymentProvider;
import com.serveme.payment.payment.PaymentProviderFactory;
import com.serveme.payment.service.PaymentDetailService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentDetailServiceImpl implements PaymentDetailService{

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PaymentDetailServiceImpl.class);
    private final PaymentDetailRepository paymentDetailRepository;

    private final PaymentProvider paymentProvider;

    @Inject
    public PaymentDetailServiceImpl(
            PaymentDetailRepository paymentDetailRepository,
            PaymentProviderFactory paymentProviderFactory) {
        this.paymentProvider = paymentProviderFactory.defaultInstance();
        this.paymentDetailRepository = paymentDetailRepository;
    }

    @Override
    public PaymentDetail getPaymentDetailByUser(long userId){
        PaymentDetail scp = paymentDetailRepository.findByUserId(userId);
        if(scp != null){
            return scp;
        }
        throw new CardNotFoundException(userId);
    }

    @Override
    public List<PaymentDetail> getPaymentDetailByUserList(List<Long> userIds) {
        return paymentDetailRepository
                .findByUserIdIn(userIds);
    }


    @Override
    public PaymentDetail setCustomerPaymentDetail(
            long userId,
            String cardToken,
            String cardType,
            String cardLastDigits) {

        try {
            PaymentDetail pd;
            if((pd = paymentDetailRepository.findByUserId(userId)) != null) {
                logger.info("User {} already has a card. Proceeding to update it.", userId);
                pd.setUpdatedAt(Instant.now());
                pd.setCardLastDigits(cardLastDigits);
                pd.setCardType(cardType);
                paymentProvider.updateCustomer(
                        pd.getCustomerToken(),
                        cardToken);
                logger.info("Card updated in the Payment Provider for User {}.", userId);
            } else {

                logger.info("User {} DOESN'T have any card. Proceeding to add it.", userId);
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("description", "user-id: "+userId);
                String providerCustomerId = paymentProvider
                        .createCustomer(
                        cardToken,
                        metadata);
                pd = new PaymentDetail(
                        paymentProvider.getId(),
                        userId,
                        providerCustomerId,
                        cardType,
                        cardLastDigits);
                logger.info("Card added in the Payment Provider for User {}.", userId);
            }

            if((pd = paymentDetailRepository.save(pd)) != null) {
                logger.info("Card successfully saved in the local database for User {}.", userId);
                return pd;
            }else {
                logger.error("Card couldn't be saved in the local database for User {}.", userId);
                throw new CardPersistenceException(
                        userId,
                        cardToken
                );
            }
        }catch(Exception ex) {
            logger.error("Error processing card", ex);
            throw new CardProviderException(userId, ex.getMessage());
        }
    }

}
