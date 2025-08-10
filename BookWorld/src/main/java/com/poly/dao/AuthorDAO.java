package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.model.Author;
import com.poly.model.Category;

public interface AuthorDAO extends JpaRepository <Author,Long> {
    List<Author> findByIdNotIn(List<Long> AuthorsId);
    

    // @Query(value = "SELECT * FROM Authors WHERE id LIKE  CONCAT('%',:idName, '%') OR author_name LIKE CONCAT('%',:idName, '%')", nativeQuery = true)
    // Page<Author> findByIdOrAuthorName(@Param("idName") String idName, Pageable pageable);

    @Query("SELECT a FROM Author a WHERE a.id LIKE  CONCAT('%',:idName, '%') OR a.authorName LIKE CONCAT('%',:idName, '%')")
    Page<Author> findByIdOrAuthorName(@Param("idName") String idName, Pageable pageable);
}
