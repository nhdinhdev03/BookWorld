//Bài 4
package com.poly.service;

import java.util.Collection;
import com.poly.model.Cart;
import com.poly.model.Order;
import com.poly.model.OrderDetail;
import com.poly.model.User;

public interface ShoppingCartService {
   
    Cart add(String id);

    void remove(String id);

    Cart update(String id, Integer qty);

    void clear();

 
    Collection<Cart> getCarts();

    int getCount();


    double getTotal();

    Order payment(User user, Boolean paymentMethod);
}
