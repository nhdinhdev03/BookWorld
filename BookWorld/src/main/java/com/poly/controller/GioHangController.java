package com.poly.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.model.Cart;
import com.poly.model.Product;
import com.poly.model.User;
import com.poly.service.SessionService;
import com.poly.service.ShoppingCartService;

@Controller
public class GioHangController {
    @Autowired
    private IndexController indexController;
    @Autowired
    ShoppingCartService cart;

    @Autowired
    SessionService session;

    boolean isError = false;
    boolean isEmptyCarts = false;
    String typeSuccess = "";
    @RequestMapping("/giohang")
    public String view(Model model, RedirectAttributes params) {
         Collection<Cart> carts = cart.getCarts();
        model.addAttribute("list",carts); 
        session.set("cartTotal", cart.getTotal());
        session.set("cartSize", carts.size());
        if(isError) {
           
            model.addAttribute("error", "Vui lòng nhập thông tin");
        } 
        if(isEmptyCarts) {
            model.addAttribute("isEmptyCarts", "Chưa có sản phẩm trong giỏ hàng");
        }
        String messageSuccess = null;
        switch (this.typeSuccess) {
            case "add-cart":
                messageSuccess = "Đã thêm vào giỏ hàng!";
                break;
            case "remove-cart":
                messageSuccess = "Đã xóa khỏi giỏ hàng!";
                break;
            case "clear-all-cart":
                messageSuccess = "Giỏ hàng đã được xóa!";
                break;
            case "payment-cart":
                messageSuccess = "Thanh toán thành công!";
                params.addAttribute("message", messageSuccess);
                return "redirect:/user/orders";
            default:
                break;
        }
        if(messageSuccess != null) {
            model.addAttribute("message", messageSuccess);
            model.addAttribute("typeSuccess",this.typeSuccess);
        }
        messageSuccess = null;
        this.typeSuccess = "";
        isError = false;
        isEmptyCarts = false;
        return "gio-hang";

    }

    @RequestMapping("/cart/add/{id}")
    public String add(@PathVariable("id") String id) {
        cart.add(id);
        this.typeSuccess = "add-cart";
        return "redirect:/giohang";
    }

    @RequestMapping("/cart/remove/{id}")
    public String remove(@PathVariable("id") String id) {
        cart.remove(id );
        this.typeSuccess = "remove-cart";
        return "redirect:/giohang";
    }

    @RequestMapping("/cart/update/{id}")
    public String update(@PathVariable("id") String id,
            @RequestParam("qty") Integer qty) {
        cart.update(id, qty);

        return "redirect:/giohang";
    }

    @RequestMapping("/cart/clear")
    public String clear() {
        if(cart.getCarts().size() > 0) {
            cart.clear();
        }
        this.typeSuccess = "clear-all-cart";
        return "redirect:/giohang";
    }

    @RequestMapping("/cart/payment")
    public String payment(User user,@RequestParam("paymentMethod") Optional<String> param) {
            isError = false;
            if(session.get("user") == null) {
                return"redirect:/account/login";
            } else {
                if(user.getPhone().isBlank()) {
                    isError = true;
                } if(user.getAddress().isBlank()){
                    isError = true;
                }
                List<Cart> list = (List<Cart>) cart.getCarts();

                if(list == null){
                    isEmptyCarts = true;
                }
                if(isError == true || isEmptyCarts == true) {
                    return "redirect:/giohang";
                }
                User currentUser = session.get("user");
                currentUser.setFullName(user.getFullName());
                currentUser.setPhone(user.getPhone());
                currentUser.setAddress(user.getAddress());
                Boolean paymentMethod = true;
                if(param.equals("online")){
                        paymentMethod = false;
                }
                cart.payment(currentUser, paymentMethod );
            }
        this.typeSuccess = "payment-cart";
        return "redirect:/giohang";
    }

}
