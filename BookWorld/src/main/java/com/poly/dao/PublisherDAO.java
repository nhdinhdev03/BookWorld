package com.poly.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.model.Author;
import com.poly.model.Publisher;

public interface PublisherDAO extends JpaRepository<Publisher,Long> {
    

     @Query("SELECT p FROM Publisher p WHERE p.id LIKE  CONCAT('%',:idName, '%') OR p.publisherName LIKE CONCAT('%',:idName, '%')")
    Page<Publisher> findByIdOrPublisherName(@Param("idName") String idName, Pageable pageable);
}
