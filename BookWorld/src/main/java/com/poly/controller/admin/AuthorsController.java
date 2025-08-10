package com.poly.controller.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.dao.AuthorDAO;
import com.poly.model.Author;
import com.poly.model.Category;
import com.poly.model.User;
import com.poly.service.SessionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AuthorsController {

    @Autowired
    AuthorDAO dao;
    @Autowired
    SessionService session;

    Boolean form = false;
    Boolean isEdit = false;
    Author author = new Author();
    String isSuccess = "";
    @RequestMapping("/authors")
    public String author(Model model, @RequestParam("p") Optional<Integer> p, @RequestParam("field") Optional<String> field
    ,@RequestParam("keywords") Optional<String> keywords) {
        model.addAttribute("pageName", "authors products");
        User user = session.get("user");
        model.addAttribute("user", user);
        if (p.isPresent()) {
            // check khi bấm phân trang khi đang sửa
            if (isEdit == true) {
                form = false;
            }
            // check khi bấm phân trang sau khi bấm reset
            if (form == true) {
                form = false;
                isEdit = false;
            }
        }
        // check mặc định tránh bị ghi đè
        if ((form == false && isEdit == false)) {
            form = false;
            isEdit = false;
        }
        if (author.getId() == null) {
            author = new Author();
        }
        if (isSuccess.equals("Create")) {
            model.addAttribute("message", "Thêm tác giả thành công");
        } else if (isSuccess.equals("Update")) {
            model.addAttribute("message", "Cập nhật tác giả thành công");
        } else if (isSuccess.equals("Delete")) {
            model.addAttribute("message", "Xóa tác giả thành công");
        }else if(isSuccess.equals("errorDelete")){
             model.addAttribute("message", "Tác giả tồn tại trong sách");
        }
        isSuccess = "";
        if(keywords.isPresent()){
            form = false;
        }
        model.addAttribute("form", form);
        model.addAttribute("isEdit", isEdit);
        model.addAttribute("author", author);

        Pageable pageable;

        if(field.isPresent()){

              Sort.Direction direction = (Sort.Direction) session.get("currentDirection") ;
              if(direction == null){
                direction = Direction.ASC;
              }
              Sort sort = Sort.by( (direction == Direction.ASC ?  Direction.DESC : Direction.ASC) , field.orElse("id") ); 
              pageable = PageRequest.of(p.orElse(0), 5 ,direction,field.orElse("id"));
              session.set("currentDirection", sort.getOrderFor(field.orElse("id")).getDirection());

        }else{
             pageable = PageRequest.of(p.orElse(0), 5 );
        }
       

       
        String value = keywords.orElse("");
        
        // Page<Author> page = dao.findAll(pageable);   
         Page<Author> page = dao.findByIdOrAuthorName(value,pageable);   
        model.addAttribute("page", page);

        return "admin/index-admin";
    }

    @RequestMapping("/authors/edit/{id}")
    public String edit(@PathVariable("id") Long id) {
        form = true;
        isEdit = true;
        author = dao.findById(id).get();
        return "redirect:/admin/authors";
    }

    @RequestMapping("/authors/create")
    public String create(@Valid Author author, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("pageName", "authors products");
            model.addAttribute("form", true);
            model.addAttribute("isEdit", false);
            Optional<Integer> p = Optional.of(0);
            Pageable pageable = PageRequest.of(p.orElse(0), 5);
            Page<Author> page = dao.findAll(pageable);
            model.addAttribute("page", page);
            return "admin/index-admin";
        }
        dao.save(author);
        this.author = new Author();
        isSuccess = "Create";
        return "redirect:/admin/authors";
    }

    @RequestMapping("/authors/update")
    public String update(@Valid Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageName", "authors products");
            model.addAttribute("form", true);
            model.addAttribute("isEdit", true);
            Optional<Integer> p = Optional.of(0);
            Pageable pageable = PageRequest.of(p.orElse(0), 5);
            Page<Author> page = dao.findAll(pageable);
            model.addAttribute("page", page);
            return "admin/index-admin";
        }

        dao.save(author);
        isSuccess = "Update";
        this.author = author;
        return "redirect:/admin/authors";
    }

    @RequestMapping("/authors/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        this.author = new Author();
         form = false;
         isEdit = false;
       try {
            dao.deleteById(id);
            isSuccess = "Delete";
        } catch (Exception e) {
             isSuccess = "errorDelete";
        }
        return "redirect:/admin/authors";
    }

    @RequestMapping("/authors/reset")
    public String reset(Model model) {
        this.author = new Author();
        form = true;
        isEdit = false;
        return "redirect:/admin/authors";
    }

}
