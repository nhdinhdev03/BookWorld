package com.poly.model;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Order_Details")
public class OrderDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;
    
    Double totalPrice;

    Integer quantity;

    public OrderDetail (Order order, Book book, Integer quantity, Double totalPrice) {
        this.order = order;
        this.book = book;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

}
