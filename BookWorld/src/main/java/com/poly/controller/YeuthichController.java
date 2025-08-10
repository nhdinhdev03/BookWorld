package com.poly.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.dao.BookDAO;
import com.poly.dao.FavoriteDAO;
import com.poly.dao.UserDAO;
import com.poly.model.Book;
import com.poly.model.Favorite;
import com.poly.model.User;
import com.poly.service.SessionService;

@Controller
public class YeuthichController {

    @Autowired
    FavoriteDAO favoriteDao;
    @Autowired
    private IndexController indexController;
    @Autowired
    BookDAO bookDao;
    @Autowired
    SessionService session;

    String isSuccess = "";

    @GetMapping("/yeuthich")
    public String yeuThich(Model model, @RequestParam("p") Optional<Integer> p) {
        User user = session.get("user");

        Pageable pageable = PageRequest.of(p.orElse(0), 8);
        Page page = favoriteDao.findByUser(user, pageable);
        model.addAttribute("page", page);

        if (isSuccess.equals("ExistBook")) {
            model.addAttribute("message", "Sách đã tồn tại rồi");
        } else if (isSuccess.equals("addSuccess")) {
            model.addAttribute("message", "Đã thêm sách yêu thích");
        } else if (isSuccess.equals("deleteSuccess")) {
            model.addAttribute("message", "Đã xóa sách yêu thích");
        }

        indexController.checkUsers(model);
        isSuccess = "";

        return "favorite";
    }

    @RequestMapping("/themYeuThich/{bookId}")
    public String themYeuThich(Favorite favorite, Model model, @PathVariable("bookId") String bookId) {
        User user = session.get("user");
        if (user == null) {
            return "redirect:/account/login";
        }
        Book book = bookDao.findById(bookId).get();
        favorite.setUser(user);
        favorite.setBook(book);
        favorite.setLikedDate(new Date());
        Favorite checkExistBook = favoriteDao.findByUserAndBook(user, book);

        if (checkExistBook != null) {
            isSuccess = "ExistBook";
            return "redirect:/yeuthich";
        }
        favoriteDao.save(favorite);
        isSuccess = "addSuccess";

        return "redirect:/yeuthich";
    }

    @RequestMapping("/yeuthich/delete/{favoriteId}")
    public String xoaYeuThich(@PathVariable("favoriteId") Long favoriteId) {
        favoriteDao.deleteById(favoriteId);
        isSuccess = "deleteSuccess";
        return "redirect:/yeuthich";
    }

}
