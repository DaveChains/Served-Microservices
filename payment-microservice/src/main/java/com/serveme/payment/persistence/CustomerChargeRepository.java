package com.serveme.payment.persistence;

import com.mysema.query.types.expr.BooleanExpression;
import com.serveme.payment.domain.CustomerCharge;
import com.serveme.payment.domain.QCustomerCharge;
import com.serveme.payment.domain.QRestaurantTab;
import com.serveme.payment.enums.ChargeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerChargeRepository extends
        JpaRepository<CustomerCharge, Long>,
        QueryDslPredicateExecutor<CustomerCharge> {


    default List<CustomerCharge> nextToProcess(int tries,int size) {

        BooleanExpression notPaid = QCustomerCharge
                .customerCharge.status.ne(ChargeStatus.PAID);
        BooleanExpression triesLimitation = QCustomerCharge
                .customerCharge.tries.lt(tries);

        PageRequest pageRequest = new PageRequest(
                0,
                size,
                new Sort(new Sort.Order(Sort.Direction.ASC, "updatedAt"))
        );
        return this
                .findAll(
                        BooleanExpression.allOf(notPaid, triesLimitation),
                        pageRequest)
                .getContent();
    }



}