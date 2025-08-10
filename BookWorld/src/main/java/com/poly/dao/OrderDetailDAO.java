package com.poly.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.model.OrderDetail;

public interface OrderDetailDAO extends JpaRepository <OrderDetail,Long> {
    @Query("SELECT new ReportRevenue(" +
       "COUNT(DISTINCT o.order.id), " +
       "COUNT(DISTINCT o.book.id), " +
       "o.order.orderDate, " +
       "SUM(o.quantity * o.book.price)) " +
       "FROM OrderDetail o " +
       "WHERE o.order.orderDate BETWEEN :startDate AND :endDate " +
       "GROUP BY o.order.orderDate")
   Page findByRevenueDate(@Param("startDate") Date startDate,@Param("endDate") Date endDate,Pageable pageable);
}
