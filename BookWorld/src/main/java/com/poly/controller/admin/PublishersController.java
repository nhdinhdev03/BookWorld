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

import com.poly.dao.PublisherDAO;
import com.poly.model.Publisher;
import com.poly.model.User;
import com.poly.service.SessionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class PublishersController {

    @Autowired
    PublisherDAO dao;
    @Autowired
    SessionService session;
   
    Boolean form = false;
    Boolean isEdit = false;

    Publisher publisher = new Publisher();
    String isSuccess = ""; 
    @RequestMapping("/publishers")
    public String publisher(Model model, @RequestParam("p") Optional<Integer> p, @RequestParam("field") Optional<String> field
    ,@RequestParam("keywords") Optional<String> keywords) {
        model.addAttribute("pageName", "publishers products");
        if(p.isPresent()){
            //check khi bấm phân trang khi đang sửa
            if(isEdit == true){          
                 form = false;
            }
            // check khi bấm phân trang sau khi bấm reset  
             if(form == true ){
                 form = false;
                 isEdit = false;
             }
        }
        // check mặc định tránh bị ghi đè 
        if( (form == false && isEdit == false)  ){
             form = false;
             isEdit = false;
        } 
        if(publisher.getId() == null) {
            publisher = new Publisher();
        }
        if(isSuccess.equals("Create")){
            model.addAttribute("message", "Thêm nhà xuất bản thành công");
        }else if(isSuccess.equals("Update")){
            model.addAttribute("message", "Cập nhật nhà xuất bản thành công");
        }else if(isSuccess.equals("Delete")){
            model.addAttribute("message", "Xóa nhà xuất bản thành công");
        }else if(isSuccess.equals("errorDelete")){
             model.addAttribute("message", "NXB tồn tại trong sách");
        }
        isSuccess = "";
        if(keywords.isPresent()){
            form = false;
        }
        model.addAttribute("form", form);
        model.addAttribute("isEdit", isEdit);
        model.addAttribute("publisher", publisher);

        Pageable pageable;

        if(field.isPresent()){
              Sort.Direction direction = (Sort.Direction) session.get("currentDirection") ;
              if(direction == null){
                direction = Direction.ASC;
              }
              Sort sort = Sort.by( (direction == Direction.ASC ?  Direction.DESC : Direction.ASC) , field.orElse("id") ); 
              pageable = PageRequest.of(p.orElse(0), 5 ,sort);
              session.set("currentDirection", sort.getOrderFor(field.orElse("id")).getDirection());
        }else{
             pageable = PageRequest.of(p.orElse(0), 5 );
        }

        String value = keywords.orElse("");
        //Page<Publisher> page = dao.findAll(pageable);
        Page<Publisher> page = dao.findByIdOrPublisherName(value,pageable);
        model.addAttribute("page", page);
        
        return "admin/index-admin";
    }

    @RequestMapping("/publishers/edit/{id}")
    public String edit(@PathVariable("id") Long id) {    
        form = true;
        isEdit = true;     
        publisher = dao.findById(id).get();
        return "redirect:/admin/publishers";
    }

    @RequestMapping("/publishers/create")
    public String create(@Valid Publisher publisher,BindingResult result, Model model) {

        if(result.hasErrors()){
            model.addAttribute("pageName", "publishers products");
            model.addAttribute("form", true);
            model.addAttribute("isEdit", false);
            Optional<Integer> p = Optional.of(0);
            Pageable pageable = PageRequest.of(p.orElse(0), 5);
            Page<Publisher> page = dao.findAll(pageable);     
            model.addAttribute("page", page);
            return "admin/index-admin";
        }


        dao.save(publisher);
        this.publisher = new Publisher();
        isSuccess = "Create";
        return "redirect:/admin/publishers";
    }

    @RequestMapping("/publishers/update")
    public String update(@Valid Publisher publisher, BindingResult result, Model model) {
        if(result.hasErrors()){
            model.addAttribute("pageName", "publishers products");
            model.addAttribute("form", true);
            model.addAttribute("isEdit", true);
            Optional<Integer> p = Optional.of(0);
            Pageable pageable = PageRequest.of(p.orElse(0), 5);
            Page<Publisher> page = dao.findAll(pageable);     
            model.addAttribute("page", page);
            return "admin/index-admin";
        }

        dao.save(publisher);
        isSuccess = "Update";
        this.publisher = publisher;
        return "redirect:/admin/publishers";
    }

    @RequestMapping("/publishers/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        this.publisher = new Publisher();
        form = false;
        isEdit = false;
        try {
            dao.deleteById(id);
            isSuccess = "Delete";
        } catch (Exception e) {
             isSuccess = "errorDelete";
        }
       
        return "redirect:/admin/publishers";
    }

    @RequestMapping("/publishers/reset")
    public String reset(Model model) {
        this.publisher = new Publisher();
        form = true;
        isEdit = false;
        return "redirect:/admin/publishers";
    }

}
