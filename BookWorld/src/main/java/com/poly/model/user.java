package com.poly.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

 @Getter
 @Setter
// @Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
	
  @Id
  @NotBlank(message = "(*)Tên người dùng là bắt buộc" )
  @Size(min = 5, message = "(*)Tên Người Dùng phải ít nhất 5 kí tự")
  String username;
  
  @NotBlank(message = "(*) Mật khẩu là bắt buộc" )
    @Size(min = 8, message = "(*)mật khẩu phải ít nhất 8 kí tự")
  String password; 
  
   @NotBlank(message = "(*)Tên Là Bắt buộc")
  String fullName;

   @NotBlank(message = "(*)Số điện thoại là bắt buộc")
  //  @Pattern(regexp = "^(09|03)\\d{8}$", message = "(*)Số điện thoại phải có 10 chữ số và bắt đầu bằng 09 hoặc 03")
  String phone;

  @NotBlank(message = "(*)email là bắt buộc")
  @Email(message = "Email phải đúng định dạng")
  String email;

  @NotBlank(message = "(*)Địa chỉ là bắt buộc")
  String address;

  String image;

  Boolean admin;

  Boolean activated;


  @OneToMany(mappedBy = "user", cascade=CascadeType.REFRESH)
  List<Share> share ;

  @OneToMany(mappedBy = "user", cascade=CascadeType.REFRESH)
  List<Favorite> favorite ;

  @OneToMany(mappedBy = "user", cascade=CascadeType.REFRESH)
  List<Order> order ;
  
  @OneToMany(mappedBy = "user", cascade=CascadeType.REFRESH)
  List<Cart> carts ;
}
