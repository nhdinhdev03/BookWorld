package com.poly.controller.admin;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.poly.dao.UserDAO;

import com.poly.model.User;
import com.poly.service.SessionService;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class UsersController {

  Boolean isSuccess = false;

  String oldImg;
  @Autowired
  UserDAO dao;
  @Autowired
  SessionService session;
  @Autowired
  ServletContext app;
  Boolean form = false;
  Boolean isEdit = false;

  User user = new User();

  @RequestMapping("/users")
  public String Users(Model model, @RequestParam("p") Optional<Integer> p,
      @RequestParam("field") Optional<String> field) {
    model.addAttribute("pageName", "users user");
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
    if (user.getUsername() == null) {
      user = new User();
    }
    if (isSuccess) {
      model.addAttribute("message", "Update Thành công!");
    }
    isSuccess = false;
    model.addAttribute("form", form);
    model.addAttribute("isEdit", isEdit);
    model.addAttribute("user", user);

    Pageable pageable;

    if (field.isPresent()) {
      Sort.Direction direction = (Sort.Direction) session.get("currentDirection");
      Sort sort = Sort.by((direction == Direction.ASC ? Direction.DESC : Direction.ASC), field.orElse("username"));
      pageable = PageRequest.of(p.orElse(0), 5, sort);
      session.set("currentDirection", sort.getOrderFor(field.orElse("username")).getDirection());
    } else {
      pageable = PageRequest.of(p.orElse(0), 5);
    }
    Page<User> page = dao.findByAdmin(false, pageable);
    model.addAttribute("page", page);

    return "admin/index-admin";
  }

  @RequestMapping("/users/edit/{username}")
  public String edit(@PathVariable("username") String username) {
    this.oldImg = this.user.getImage();
    form = true;
    isEdit = true;
    user = dao.findById(username).get();
    return "redirect:/admin/users";
  }

  @RequestMapping("/users/update")
  public String update(@Valid User user, BindingResult result, Model model,
      @RequestParam("fileImage") MultipartFile fileImage) {
    if (result.hasErrors()) {

      isSuccess = false;
      Optional<Integer> p = Optional.of(0);
      model.addAttribute("pageName", "users user");
      model.addAttribute("form", false);
      model.addAttribute("isEdit", true);

      Pageable pageable = PageRequest.of(p.orElse(0), 5);
      Page<User> page = dao.findByAdmin(false , pageable);
      model.addAttribute("page", page);
      return "admin/index-admin";
    }

    if (fileImage.isEmpty()) {
      user.setImage(oldImg);
      dao.save(user);
    } else {
      String fileName = fileImage.getOriginalFilename();
      String uploadDirectory = "static/assets/img/";
      try {
        Path path = Paths.get(new ClassPathResource(uploadDirectory).getURI());
        fileImage.transferTo(path.resolve(fileName).toFile());
        System.out.println(path.resolve(fileName).toFile().getAbsolutePath());
        user.setImage(fileName);
        isSuccess = true;
        dao.save(user);
      } catch (IllegalStateException | IOException e) {
        e.printStackTrace();
      }
    }
    this.user = user;
    return "redirect:/admin/users";

  }

  @RequestMapping("/users/reset")
  public String reset(Model model) {
    this.user = new User();
    form = true;
    isEdit = false;
    return "redirect:/admin/users";
  }

}
