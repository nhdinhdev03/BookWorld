package com.poly.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.dao.BookDAO;
import com.poly.dao.CategoryDAO;
import com.poly.model.Book;
import com.poly.model.Category;
import com.poly.model.MailInfo;
import com.poly.model.StatusBook;
import com.poly.model.User;
import com.poly.service.MailerServiceImpl;
import com.poly.service.SessionService;

import jakarta.mail.MessagingException;

@Controller
public class IndexController {

    @Autowired
    BookDAO dao;

    @Autowired
    SessionService session;

    @Autowired
    MailerServiceImpl mailer;
    @Autowired
    CategoryDAO categoryDao;

    Book book = new Book();

    @RequestMapping("/index")
    public String index(Model model, @RequestParam("p") Optional<Integer> p) {
        Pageable pageable = PageRequest.of(p.orElse(0), 16);
        Page page = dao.findByStatusNot(StatusBook.NOTAVAILABLE,pageable);
        model.addAttribute("page", page);

        checkUsers(model);

        return "index";

    }

    public User checkUsers(Model model) {
        User user = new User();
        if (session.get("user") == null) {
              User user1 = new User();
            model.addAttribute("user", user1);
            model.addAttribute("checkLG", false);
        } else {
            user = session.get("user");
            model.addAttribute("user", user);
            model.addAttribute("checkLG", true);
        }
        return user;
    }

    @RequestMapping("/share/{bookId}")
    public String edit(@PathVariable("bookId") String bookId, Model model, @RequestParam("emailShare") String to) {

        System.out.println(bookId);
        book = dao.findById(bookId).get();
        try {
            MailInfo mail = new MailInfo();
            mail.setAnh(book.getImage());
            mail.setBody("<html><body>"
                    + "<div"
                    + " style='font-family: Arial, sans-serif; line-height: 1.6; max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd;'>"
                    + "<h2 style='margin-top: 0;'>Chia sẻ sản phẩm sách</h2>"
                    + " <p>Xin chào,  <strong>" + to + "</strong></p>"
                    + "<p>Tôi muốn chia sẻ với bạn một sản phẩm sách thú vị. Dưới đây là thông tin về sách:</p>"
                    + "<div style='display: flex; align-items: center; margin-bottom: 20px; '>"
                    + "<img style='width: 165px; height: auto; margin-right: 20px;'"
                    + "   src='cid:image' alt='Lỗi hình ảnh'>"
                    + " <div style=' width: auto;  margin-bottom: 30px;'>"
                    + "    <h3 style='font-size: 18px; font-weight: bold; margin: 0; '>" + book.getTitle() + "</h3>"
                    + "   <p style='font-size: 14px; color: #c71b1b; margin: 0; margin-top: 10px;'>Giá: "
                    + book.getPrice() + " VNĐ</p>"
                    + "     <div"
                    + "          style='margin-top: 30px; height: 51.2px;  display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden;'>"
                    + "          " + book.getDescription() + ""
                    + "       </div>"
                    + "    </div>"
                    + " </div>"
                    + " <p>Bạn có thể tìm hiểu thêm về sách này và đặt mua tại <a href='http://localhost:8081/chitiet/"
                    + book.getId() + "'>đường dẫn"
                    + "          sách</a>.</p>"
                    + " <p>Hy vọng bạn sẽ thích quyển sách này! Nếu bạn có bất kỳ câu hỏi hoặc cần thêm thông tin, hãy liên hệ với tôi."
                    + " </p>"
                    + "<p>Trân trọng,<br><strong>Success 202 - BOOKWORLD</strong> </p>"
                    + " <p>&copy; 2023 Tất cả các quyền được bảo lưu.</p>"
                    + "</div>"
                    + "</body></html>");

            mailer.send(to, "Gửi Email", mail.getBody(), mail.getAnh());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "redirect:/index";
    }
}
