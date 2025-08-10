package com.poly.controller.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

import com.poly.dao.OrderDetailDAO;
import com.poly.model.ReportRevenue;
import com.poly.model.User;
import com.poly.service.SessionService;

@Controller
@RequestMapping("/admin")
public class RevenueController {
        @Autowired
    SessionService session;
    @Autowired
    OrderDetailDAO dao;

    @RequestMapping("/revenue")
    public String revenue(Model model,
            @RequestParam("startDate") Optional<String> startDate,
            @RequestParam("endDate") Optional<String> endDate,
            @RequestParam("p") Optional<Integer> p) {
        model.addAttribute("pageName", "revenue statistical");
              User user = session.get("user");
        model.addAttribute("user", user);
        Date sdate = null;
        Date edate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdate = dateFormat.parse(startDate.orElse("1975-01-01"));
            edate = dateFormat.parse(endDate.orElse(dateFormat.format(new Date())));
        } catch (ParseException e) {
            model.addAttribute("message", "Vui lòng chọn ngày để tìm!");
            try {
                sdate = dateFormat.parse("1975-01-01");
                edate = dateFormat.parse(dateFormat.format(new Date()));
            } catch (ParseException e1) {
            }
            e.printStackTrace();
        }
        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page page = dao.findByRevenueDate(sdate, edate, pageable);
        model.addAttribute("page", page);
        return "admin/index-admin";
    }

}
