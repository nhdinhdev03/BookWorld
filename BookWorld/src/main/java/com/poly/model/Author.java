package com.poly.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "(*) Vui lòng nhập tên tác giả")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "(*) Không được chứa số hoặc ký tự đặc biệt ")
    String authorName;
    
    @Column(name = "date_of_birth")
    @NotNull(message = "(*) Vui lòng nhập ngày sinh")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateOfBirth = new Date();

    @NotBlank(message = "(*) Vui lòng nhập quốc tịch")
    @Pattern(regexp = "^[\\p{L}\\d\\s,.]+$", message = "(*) Không chưa ký tự đặc biệt")
    String nationality;


    @NotBlank(message = "(*) Vui lòng nhập mô tả")
    @Size(max = 255, message = "(*) Vui lòng nhập mô tả phải nhỏ hơn hoặc bằng 255 ký tự")
    String description;

    @OneToMany(mappedBy = "author", cascade=CascadeType.REFRESH)
    List<AuthorBook> authorBooks;

}
