package com.poly.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.model.Order;
import com.poly.model.User;

public interface OrderDAO extends JpaRepository <Order,String> {
    
    Page findByStatus(Integer status, Pageable pageable);
    Page findByUser(User user, Pageable pageable);
    public default String generateOrderId(long l) {
        String formattedCount = String.format("%04d", l); 
        String currentDate = getCurrentDate();
        
        return "D" + formattedCount + currentDate;
    }
    
    private String getCurrentDate() {
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        int month = today.getMonthValue();
        int year = today.getYear() % 100; 
        
        return String.format("%02d%02d%02d", day, month, year);
    }
}
