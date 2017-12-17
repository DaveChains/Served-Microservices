package com.serveme.payment.persistence;

import com.mysema.query.types.expr.BooleanExpression;
import com.serveme.payment.domain.QRestaurantTab;
import com.serveme.payment.domain.RestaurantTab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface RestaurantTabRepository
        extends JpaRepository<RestaurantTab, Long>,
        QueryDslPredicateExecutor<RestaurantTab>{

        default RestaurantTab retrieve(long restaurantId, LocalDate date ){
                BooleanExpression restaurantIdEq = QRestaurantTab
                        .restaurantTab
                        .restaurantId.eq(restaurantId);
                BooleanExpression endExclusive= QRestaurantTab
                        .restaurantTab
                        .startPeriodInclusive
                        .loe(date);
                BooleanExpression startInclusive= QRestaurantTab
                        .restaurantTab
                        .endPeriodInclusive
                        .gt(date);
                return this
                        .findOne(
                                restaurantIdEq
                                        .and(startInclusive)
                                        .and(endExclusive));
        }


}