package com.poly.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import org.springframework.web.multipart.MultipartFile;

import com.poly.dao.AuthorBookDAO;
import com.poly.dao.AuthorDAO;
import com.poly.dao.BookDAO;
import com.poly.dao.CartDAO;
import com.poly.dao.CategoryDAO;
import com.poly.dao.FavoriteDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.dao.PublisherDAO;
import com.poly.dao.ShareDAO;
import com.poly.model.Author;
import com.poly.model.AuthorBook;
import com.poly.model.Book;
import com.poly.model.Cart;
import com.poly.model.Category;
import com.poly.model.Favorite;
import com.poly.model.OrderDetail;
import com.poly.model.Publisher;
import com.poly.model.Share;
import com.poly.model.StatusBook;
import com.poly.model.User;
import com.poly.service.SessionService;

import jakarta.persistence.PrePersist;
import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ProductsController {
    @Autowired
    BookDAO dao;
    @Autowired
    CategoryDAO daoCat;

    @Autowired
    PublisherDAO daoPub;

    @Autowired
    OrderDetailDAO daoOdrDet;

    @Autowired
    AuthorDAO daoAut;

    @Autowired
    AuthorBookDAO daoAutBook;

    @Autowired
    CartDAO daoCart;
    
    @Autowired
    ShareDAO daoShare;

    @Autowired
    FavoriteDAO daoFav;

    @Autowired
    ServletContext app;

    @Autowired
    SessionService session;
    String typeSuccess = "";
    boolean isError = false;
    boolean isErrorDelete = false;
    Book book =  new Book();
    boolean form = false;
    boolean isEdit = false;
    List<Author> listAut = new ArrayList<>();
    List<Author> listAutOfBook = new ArrayList<>();

    String uploadDirectory = "static/assets/img/";
    String oldImg ;
    @RequestMapping("/products")
    public String products(Model model,@RequestParam("p") Optional<Integer> p,
     @RequestParam("field") Optional<String> field,
     @RequestParam("keywords") Optional<String> keywords) {

        model.addAttribute("pageName", "products products");
              User user = session.get("user");
        model.addAttribute("user", user);
        if(p.isPresent()){  
            // check phân trang khi đang edit
            if(isEdit) {
                form = false;
            }
            //check phân trang sau khi reset form
            if(form){
                form = false;
                isEdit = false;
            }
        }
        // check mặc định tránh bị ghi đè
        if( (form == false && isEdit == false)){
            form = false;
            isEdit = false;
        } 
        if( this.book.getId() == null){
            this.book = new Book();
            this.book.setStatus(StatusBook.INSTOCK);
            this.book.setQuantity(1);
        }

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

        Page page = dao.findByIdOrTitle(keywords.orElse(""), pageable);
        if(isEdit) {
            List<Long> listAuthorId = new ArrayList<>();
            for (Author aut : listAutOfBook) {
                listAuthorId.add( aut.getId());
            }
            if(!listAuthorId.isEmpty()) {
                this.listAut = daoAut.findByIdNotIn(listAuthorId);
            } else {
                 this.listAut = daoAut.findAll();
            }
        } else {
             this.listAut = daoAut.findAll();
        }
        String messageSuccess = null;
        switch (typeSuccess) {
            case "create":
                  messageSuccess = "Thêm thành công!";
                break;
            case "update":
                  messageSuccess = "Cập nhật thành công!";
                break;
            case "delete-book":
                  messageSuccess = "Xóa thành công!";
                break;
            case "delete-book-existed":
                  messageSuccess = "Đã chuyển trạng thái ngừng kinh doanh!";
                break;
            case "delete-author":
                  messageSuccess = "Xóa tác giả thành công!";
                break;
            default:
                messageSuccess = null;
                break;
        }

        List<Category> listCat = daoCat.findAll();
        List<Publisher> listPub = daoPub.findAll();
        model.addAttribute("message", messageSuccess);
        model.addAttribute("form", this.form);
        model.addAttribute("isEdit", this.isEdit);
        model.addAttribute("listAutOfBook", this.listAutOfBook);
        model.addAttribute("listAut", this.listAut);
        model.addAttribute("listPub", listPub);
        model.addAttribute("listCat", listCat);
        model.addAttribute("book",this.book);
        model.addAttribute("page", page);
        messageSuccess = null;
        this.typeSuccess = "";
        return "admin/index-admin"; 
    }
 
    @RequestMapping("/products/form-errors")
    public String formErrors(@Valid Book book,BindingResult result,Model model) {
        model.addAttribute("pageName", "products products");
        model.addAttribute("form", true); 
        model.addAttribute("isEdit", false);
        Pageable pageable = PageRequest.of( 0, 5);
        Page page = dao.findAll(pageable);
        List<Category> listCat = daoCat.findAll();
        List<Publisher> listPub = daoPub.findAll();
        model.addAttribute("listPub", listPub);
        model.addAttribute("listCat", listCat);
        model.addAttribute("listAutOfBook", this.listAutOfBook);
        model.addAttribute("listAut", this.listAut);
        model.addAttribute("book", book);
        model.addAttribute("page", page);
        return "admin/index-admin";  
    }
    @RequestMapping("/products/create")
    public String create(@Valid Book book,BindingResult result,Model model,
    @RequestParam("fileImage") MultipartFile fileImage,
    @RequestParam("authors") Optional< List<Long>> items) {
        isError = false;
        if(dao.existsById(book.getId())){
             isError = true;    
            model.addAttribute("isExist", "(*) Mã sách đã tồn tại");
        }
        if(fileImage.isEmpty() ){
             isError = true;
            model.addAttribute("errorImg", "(*) Vui lòng chọn ảnh");
        }
        if(!items.isPresent()) {
             isError = true;
             model.addAttribute("nullAuthor", "(*) Vui lòng chọn tác giả");
        }
        
        if(book.getQuantity() <= 0) {
             isError = true;
             model.addAttribute("errorQuantity", "(*) Vui lòng nhập số lượng lớn hơn 0");
        }
        if(isError || result.hasErrors()) {
             return "forward:/admin/products/form-errors";
        }
        List<Author> listAuthor = new ArrayList<>();
        for (Long authorId : items.get()) {
            Author authorsSelected  = daoAut.findById(authorId).get();
            listAuthor.add( authorsSelected);
        }
         if(!fileImage.isEmpty()) {        
            String fileName = fileImage.getOriginalFilename();
            
            try {
                Path path = Paths.get(new ClassPathResource(uploadDirectory).getURI());
                fileImage.transferTo(path.resolve(fileName).toFile());
                book.setImage(fileName);
                dao.save(book);
                if (listAuthor != null) {
                    for (Author author : listAuthor) {
                    AuthorBook authorBook = new AuthorBook(author,book);
                     daoAutBook.save(authorBook);
                    }
                }
                this.typeSuccess = "create";
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        } 
        this.form = false;
        return "redirect:/admin/products";  
    }

    @RequestMapping("/products/edit/{id}")
    public String edit(@PathVariable("id") String id) {
        this.listAutOfBook = new ArrayList<>();
        this.book = dao.findById(id).get();
        List<AuthorBook> listAutBook = this.book.getAuthorBooks();
        for (AuthorBook authorBook : listAutBook) {
            this.listAutOfBook.add( authorBook.getAuthor());
        }
        this.oldImg = this.book.getImage();
        this.form = true;
        this.isEdit = true;
        return "redirect:/admin/products"; 
    }

    @RequestMapping("/products/reset")
    public String reset() {
        this.book = new Book();
        this.form = true;
        this.isEdit = false;
        this.typeSuccess = "";
        return "redirect:/admin/products"; 
    }
    @RequestMapping("/products/update")
    public String update(@Valid Book book,BindingResult result,Model model,
    @RequestParam("fileImage") MultipartFile fileImage,
    @RequestParam("authors") Optional< List<Long>> items ) {

        if(result.hasErrors()) {
             return "forward:/admin/products/form-errors";
        }
       

        if(book.getQuantity() <= 0) {
            if(book.getStatus() != StatusBook.NOTAVAILABLE) {
                book.setStatus(StatusBook.SOLDOUT);
            }
          
        } else  {

            if(book.getStatus() == StatusBook.SOLDOUT) {
                book.setStatus(StatusBook.INSTOCK);
            }
        } 
        if(fileImage.isEmpty()) {
                book.setImage(oldImg);
                dao.save(book);
                this.typeSuccess = "update";
                
        } else {
            String fileName = fileImage.getOriginalFilename();
            try {
                Path path = Paths.get(new ClassPathResource(uploadDirectory).getURI());
                fileImage.transferTo(path.resolve(fileName).toFile());
                System.out.println(path.resolve(fileName).toFile().getAbsolutePath());
                book.setImage(fileName);
                dao.save(book);
                this.typeSuccess = "update";
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        } 
        List<Author> listAuthor = new ArrayList<>();
        if(items.isPresent()) {
            for (Long authorId : items.get()) {
                    Author authorsSelected  = daoAut.findById(authorId).get();
                    listAuthor.add( authorsSelected);
            }   
            for (Author author : listAuthor) {
                 AuthorBook authorBook = new AuthorBook(author,book);
                 daoAutBook.save(authorBook);
            }
        }  
    
        this.listAutOfBook = new ArrayList<>();
        List<AuthorBook> listAutBook = daoAutBook.findByBook(book);
        for (AuthorBook authorBook : listAutBook) {
            this.listAutOfBook.add(authorBook.getAuthor());
        }
        this.book = book;
        return "redirect:/admin/products"; 
    }

    @RequestMapping("/products/delete/{id}")
    public String delete(Model model , Book book,@PathVariable("id") String id) {
        book = dao.findById(id).get();

       
        try {
             if(book.getFavorites().isEmpty() &&
              book.getOrderDetails().isEmpty() &&
              book.getShares().isEmpty() &&
              book.getCarts().isEmpty()) {
                List<AuthorBook> listABCurrent = book.getAuthorBooks();
                for (AuthorBook ab : listABCurrent) {
                    daoAutBook.delete(ab);
                }
            }
            dao.delete(book);
             this.typeSuccess = "delete-book";
        } catch (Exception e) {
            this.typeSuccess = "delete-book-existed";
            book.setStatus(StatusBook.NOTAVAILABLE);
            dao.save(book);
            e.printStackTrace();
            return "redirect:/admin/products"; 
        }
        
        book = new Book();
        this.form = false;
        this.isEdit = false;
        this.typeSuccess = "delete-book";
        return "redirect:/admin/products"; 
    }

    @RequestMapping("/products/delete-author-of-book")
    public String deleteAuthorOfBook(Model model ,
    @RequestParam("bookId") String bookId,
    @RequestParam("authorId") String authorId) {
        Book book = dao.findById(bookId).get();
        Author authorChecked = daoAut.findById(Long.parseLong(authorId)).get();
        AuthorBook autBook = daoAutBook.findByBookAndAuthor(book,authorChecked);
       
        try {
            daoAutBook.delete(autBook);
        } catch (Exception e) {
            isErrorDelete = true;
            return "redirect:/admin/products"; 
        }
         this.listAutOfBook = new ArrayList<>();
        List<AuthorBook> listAutBook = daoAutBook.findByBook(book);
        for (AuthorBook authorBook : listAutBook) {
            this.listAutOfBook.add(authorBook.getAuthor());
        }
        this.form = true;
        this.isEdit = true;
        this.typeSuccess = "delete-author";
        return "redirect:/admin/products"; 
    }
}
