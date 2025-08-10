package com.poly.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.model.User;
import com.poly.service.SessionService;

@Controller
public class LienHeController {
    @Autowired
    SessionService session;
    
    @Autowired
    private IndexController indexController;

    @GetMapping("/lienhe")
    public String lienHe(Model model) {
        return "lien-he";
    }
}
