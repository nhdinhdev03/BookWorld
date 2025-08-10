package com.poly.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.controller.IndexController;
import com.poly.dao.BookDAO;
import com.poly.model.ReportSoldBooks;
import com.poly.model.User;
import com.poly.service.SessionService;


@Controller
@RequestMapping("/admin")
public class SoldBooksController {
        @Autowired
    SessionService session;
      @Autowired
    private IndexController indexController;
    @Autowired 
    BookDAO dao;
    @RequestMapping("/sold-books")
    public String soldeBooks(Model model,@RequestParam("p") Optional<Integer> p){
        model.addAttribute("pageName", "sold-books statistical");
        Pageable pageable = PageRequest.of(p.orElse(0),  5);
        Page page = dao.getSoldBooks(pageable);
        model.addAttribute("page", page);
        return "admin/index-admin";
    }



}
