package com.poly.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	String id;
    String ten;
    int soLuong;    
    double gia;
    String anh;
 
}