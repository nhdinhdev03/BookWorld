package com.poly.controller.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.OrderDAO;
import com.poly.model.StatusOrder;
import com.poly.model.User;
import com.poly.service.SessionService;
import com.poly.model.Order;

@Controller
@RequestMapping("/admin")
public class OrdersPending {
    @Autowired
    SessionService session;
    @Autowired
    OrderDAO dao;

    StatusOrder status;
    boolean isSuccess = false;

    @RequestMapping("/orders-pending")
    public String ordersPending(Model model, @RequestParam("p") Optional<Integer> p) {
        model.addAttribute("pageName", "orders-pending orders");
        User user = session.get("user");
        model.addAttribute("user", user);
        if (isSuccess) {
            model.addAttribute("message", "Đã xác nhận đơn hàng!");
        }
        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page page = dao.findByStatus(StatusOrder.PENDING, pageable);

        model.addAttribute("page", page);
        isSuccess = false;
        return "admin/index-admin";
    }

    @RequestMapping("/orders-pending/{id}")
    public String handleShipping(@PathVariable("id") String id, Model model) {
        Order order = dao.findById(id).get();
        if (order != null) {
            order.setStatus(StatusOrder.COMPLETED);
            dao.save(order);
            isSuccess = true;
        }
        return "redirect:/admin/orders-pending";
    }
}
