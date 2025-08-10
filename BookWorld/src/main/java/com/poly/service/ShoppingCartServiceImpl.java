// Bài 4
package com.poly.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.poly.dao.BookDAO;
import com.poly.dao.CartDAO;
import com.poly.dao.OrderDAO;
import com.poly.dao.OrderDetailDAO;
import com.poly.dao.UserDAO;
import com.poly.model.Book;
import com.poly.model.Cart;
import com.poly.model.Order;
import com.poly.model.OrderDetail;
import com.poly.model.StatusOrder;
import com.poly.model.User;

import ch.qos.logback.core.status.Status;
@SessionScope
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService  {
    Map<String, Cart> map = new HashMap<>();
    @Autowired 
    SessionService session ;

    User user ;
    @Autowired
    BookDAO daoBook;

    @Autowired
    UserDAO daoUser;

    @Autowired
    OrderDAO daoOrder;

    @Autowired
    OrderDetailDAO daoOrderDet;

    @Autowired
    CartDAO daoCart;

    StatusOrder statusOrder;
    @Override
    public Cart add(String id) {
        Book book = null;
        Cart cart ;
        this.user = session.get("user");
        book = daoBook.findById(id).get() ;
        if(this.user == null) {
            cart = new Cart(id,this.user, book, 1, book.getPrice() * 1,book.getImage());
            map.put(id, cart);
        } else {
            cart = new Cart( id,this.user, book, 1, book.getPrice() * 1,book.getImage());
            daoCart.save(cart);
        }
        return cart;
    }

    @Override
    public void remove(String id) {
        if(this.user != null) {
           Cart crm =  daoCart.findById(id).get();
           daoCart.delete(crm);
        } else {
             map.remove( id) ;
        }
    }

    @Override
    public Cart update(String id, Integer qty) {
        if(this.user !=null) {
            Cart cart = daoCart.findById(id).get();
            cart.setQuantity(qty);
            cart.setTotalPrice(qty * cart.getBook().getPrice());
            daoCart.save(cart);
            return cart;
        }
        map.get(id).setQuantity(qty);
        map.get(id).setTotalPrice(qty * map.get(id).getBook().getPrice());
        return  map.get(id);
    }

    @Override
    public void clear() {
        this.user = session.get("user");
        if(this.user  != null) {
            daoCart.deleteAllByUser(this.user);
        } else {
            map.clear();
        }
       
    }

    @Override
    public Collection<Cart> getCarts() {
        this.user = session.get("user");
        if(user != null) {
            List<Cart> list = daoCart.findByUser(this.user);
            return list;
        } 
        return map.values();
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public double getTotal() {
        double amount = 0;
         this.user = session.get("user");
         
        if(this.user != null) {
            List<Cart> carts = daoCart.findByUser(this.user);
            for (Cart cart : carts) {
                amount += cart.getTotalPrice();
            }
        } else {
            for (Cart cart : map.values()) {
                amount += cart.getTotalPrice();
            }
        }
        return amount;
    }

    public Order payment(User user, Boolean paymentMethod) {
        daoUser.save(user);
        Order order = new Order();
        order.setId(daoOrder.generateOrderId(daoOrder.count() + 1));
        order.setOrderDate(new Date());
        order.setPaymentMethod(paymentMethod);
        order.setStatus(1);
        order.setUser(user);
        daoOrder.save(order);
        List<Cart> carts = daoCart.findByUser(user);
        
        for (Cart cart : carts) {
            Book book = cart.getBook();
            OrderDetail orderDet = new OrderDetail(order, book,cart.getQuantity() , cart.getTotalPrice());
            book.setQuantity(book.getQuantity() - cart.getQuantity());
            daoBook.save(book);
            daoOrderDet.save(orderDet);
            daoCart.delete(cart);
        }
        return order;
    }
    
}
