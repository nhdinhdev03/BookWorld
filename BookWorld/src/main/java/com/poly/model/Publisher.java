package com.poly.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "Publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    

    @NotEmpty(message = "(*) Vui lòng nhập tên nhà xuất bản")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "(*) Không được chứa số hoặc ký tự đặc biệt ")
    String publisherName;
    
    @NotBlank(message = "(*) Vui lòng nhập địa chỉ")    
    @Pattern(regexp = "^[\\p{L}\\d\\s,.]+$", message = "(*) Không chưa ký tự đặc biệt")
    String address;

    @NotBlank(message = "(*) Vui lòng nhập công ty")
    @Pattern(regexp = "^[\\p{L}\\d\\s,.]+$", message = "(*) Không chưa ký tự đặc biệt")
    String city;

    @NotEmpty(message = "(*) Vui lòng nhập quốc gia")
    @Pattern(regexp = "^[\\p{L}\\s,.]+$", message = "(*) Không được chứa số hoặc ký tự đặc biệt")
    String country;

    @NotBlank(message = "(*) Vui lòng nhập email")
    @Email(message = "(*) Email không hợp lệ")
    String email;
    
    
    @Pattern(regexp = "^(09|03)\\d{8}$", message = "(*) Vui lòng nhập số điện thoại phải có 10 chữ số và bắt đầu bằng 09 hoặc 03")
    String phone;

    @JsonIgnore
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REFRESH)
    List<Book> book;

}
