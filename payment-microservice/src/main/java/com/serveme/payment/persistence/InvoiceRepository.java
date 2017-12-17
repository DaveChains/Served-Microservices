package com.serveme.payment.persistence;

import com.serveme.payment.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface InvoiceRepository
        extends JpaRepository<Invoice, Long> {

    Invoice findByOrderId(String orderId);

}