package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.model.Share;

public interface ShareDAO extends JpaRepository <Share,Long> {
    
}
