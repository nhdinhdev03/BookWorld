
package com.poly.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Books")

public class Book {
    
    @Id
    @NotBlank(message = "(*) Vui lòng nhập mã sách")
    @Pattern(regexp = "^[\\p{Alnum}]+$", message = "(*) Mã sách không được chứa kí tự đặc biệt")
    String id;
  
    @NotBlank(message = "(*) Vui lòng nhập tên sách")
    String title;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    Publisher publisher;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd") 
    Date createdDate = new Date();

    @NotNull(message = "(*) Vui lòng nhập giá")
    @DecimalMin(value = "0.01", message = "(*) Giá phải lớn hơn 0.01")
    Double price;

    @NotNull(message = "(*) Vui lòng nhập số lượng")
    @Min(value = 0, message = "(*) số lượng phải lớn hơn hoặc bằng 0")
    Integer quantity;

    String image;

    @NotNull(message = "(*) Vui lòng chọn trạng thái")
    Integer status;

    String description;



    @OneToMany(mappedBy = "book", cascade=CascadeType.REFRESH)
    List<OrderDetail> orderDetails;



    @OneToMany(mappedBy = "book", cascade=CascadeType.REFRESH)
    List<AuthorBook> authorBooks;


    @OneToMany(mappedBy = "book", cascade=CascadeType.REFRESH)
    List<Share> shares;


    @OneToMany(mappedBy = "book", cascade=CascadeType.REFRESH)
    List<Favorite> favorites;


    @OneToMany(mappedBy = "book", cascade=CascadeType.REFRESH)
    List<Cart> carts;

}
