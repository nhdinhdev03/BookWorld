package com.poly.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReportRevenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long numOrders;
    Long numBooksSold;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd") 
    Date createdDate = new Date();
    Double totalPrice;

      public ReportRevenue(Long numOrders, Long numBooksSold, Date createdDate, Double totalPrice) {
        this.numOrders = numOrders;
        this.numBooksSold = numBooksSold;
        this.createdDate = createdDate;
        this.totalPrice = totalPrice;
    }

    
}
