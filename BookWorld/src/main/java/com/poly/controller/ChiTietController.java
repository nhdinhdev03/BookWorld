package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.dao.AuthorBookDAO;
import com.poly.dao.BookDAO;
import com.poly.model.Author;
import com.poly.model.AuthorBook;
import com.poly.model.Book;

@Controller
public class ChiTietController {
    
    @GetMapping("/chitiet")
    public String lienHe(){
        return "chi-tiet";
    }


    @Autowired
    BookDAO bookDao;
    @Autowired
    AuthorBookDAO authorBookDao;

    @RequestMapping("/chitiet/{bookId}")
    public String edit(@PathVariable("bookId") String bookId,Model model ) {

        Book book = bookDao.findById(bookId).get();
        model.addAttribute("book",book);

        System.out.println("day ne: "+bookId);
        return "chi-tiet";
    }
}
