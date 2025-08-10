package com.poly.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.model.Book;
import com.poly.model.Favorite;
import com.poly.model.User;

public interface FavoriteDAO extends JpaRepository <Favorite,Long> {

    Page  findByUser (User user,Pageable pageable);
    
    Favorite findByUserAndBook(User user,Book book);
}   
