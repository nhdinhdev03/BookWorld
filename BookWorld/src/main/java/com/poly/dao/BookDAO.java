package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.model.Author;
import com.poly.model.Book;
import com.poly.model.Category;
import com.poly.model.ReportSoldBooks;

public interface BookDAO extends JpaRepository<Book, String> {

    @Query("SELECT new com.poly.model.ReportFavoriteBook(b.title, b.category.categoryName, COUNT(f), MIN(f.likedDate), MAX(f.likedDate)) "
        + " FROM Book b JOIN b.favorites f "
        + " GROUP BY b.category.categoryName, b"
        + " ORDER BY COUNT(f) DESC")
    Page getFavoriteBook(Pageable pageable);

    @Query("SELECT new com.poly.model.ReportSoldBooks(od.book.title, c.categoryName, COUNT(od.book), MIN(od.order.orderDate), MAX(od.order.orderDate)) "
    + " FROM OrderDetail od JOIN od.book.category c"
    + " GROUP BY od.book.title, c.categoryName"
    + " ORDER BY COUNT(od.book) DESC")
    Page getSoldBooks(Pageable pageable);
    Page findByStatusNot(Integer status,Pageable pageable);

     @Query("SELECT b FROM Book b WHERE b.id LIKE  CONCAT('%',:keywords, '%') OR b.title LIKE CONCAT('%',:keywords, '%')")
    Page findByIdOrTitle(@Param("keywords") String keywords, Pageable pageable);
    // Page findByCategory(Category category ,Pageable pageable);

    

    
}
