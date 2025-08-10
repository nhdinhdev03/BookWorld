package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.model.User;

public interface UserDAO extends JpaRepository<User,String> {
    User findByEmail( String email);

    Page findByAdmin(Boolean role ,Pageable pageable);
}