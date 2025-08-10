package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.model.Author;
import com.poly.model.AuthorBook;
import com.poly.model.Book;
import com.poly.model.Cart;

public interface AuthorBookDAO extends JpaRepository<AuthorBook,Long> {


   List<AuthorBook> findByBook(Book book);

    AuthorBook findByBookAndAuthor(Book book, Author author);
    
}
