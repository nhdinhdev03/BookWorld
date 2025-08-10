package com.poly.model;

import java.util.HashMap;
import java.util.Map;



public class DB {
    public static Map<String, Product> products = new HashMap<>();
    static {
    products.put("1", new Product("1", "Sach 1", 10, 100, "Sach1.jpg" ));
    products.put("2", new Product("2", "Sach 2", 10, 234, "Sach2.jpg" ));
    products.put("3", new Product("3", "Sach 3", 10, 523, "Sach3.jpg" ));
    products.put("4", new Product("4", "Sach 4", 10, 623, "Sach4.jpg" ));
    products.put("5", new Product("5", "Sach 5", 10, 236, "Sach5.jpg" ));
    products.put("6", new Product("6", "Sach 6", 10, 873, "Sach6.jpg" ));
    products.put("7", new Product("7", "Sach 7", 10, 58, "Sach7.jpg" ));
    products.put("8", new Product("8", "Sach 8", 10, 364, "Sach8.jpg" ));
   
   
    }
}
