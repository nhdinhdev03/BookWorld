package com.poly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Carts")
public class Cart {
    @Id
    String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
  
    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;

    Integer quantity; 

    Double totalPrice;

    String image;

    public Cart(User user, Book book, Integer quantity, Double totalPrice,String image) {
        this.user = user;
        this.book = book;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.image = image;
    }
}
