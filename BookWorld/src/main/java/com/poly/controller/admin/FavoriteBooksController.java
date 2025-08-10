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

import com.poly.dao.BookDAO;
import com.poly.model.ReportFavoriteBook;
import com.poly.model.User;
import com.poly.service.SessionService;

@Controller
@RequestMapping("/admin")
public class FavoriteBooksController {

    @Autowired
    SessionService session;
    @Autowired
    BookDAO dao;

    @RequestMapping("/favorite-books")
    public String favoriteBooks(Model model, @RequestParam("p") Optional<Integer> p) {
        model.addAttribute("pageName", "favorite-books statistical");
        User user = session.get("user");
        model.addAttribute("user", user);
        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page page = dao.getFavoriteBook(pageable);
        model.addAttribute("page", page);

        return "admin/index-admin";
    }
}
