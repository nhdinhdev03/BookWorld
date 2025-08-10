package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.model.Cart;
import com.poly.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface CartDAO extends JpaRepository<Cart,String> {
    List<Cart> findByUser(User user); 
    @Transactional
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user  = :userCurrent")
    void deleteAllByUser(@Param("userCurrent") User user);

    
}
