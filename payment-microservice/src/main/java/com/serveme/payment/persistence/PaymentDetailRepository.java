package com.serveme.payment.persistence;

import com.serveme.payment.domain.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {



    PaymentDetail findByUserId(Long userId);

    List<PaymentDetail> findByUserIdIn(List<Long> userIdList);
}