/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.Cart;
import com.example.demo.site.entities.Order;
import com.example.demo.site.entities.OrderStatus;
import com.example.demo.site.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Setir
 */
public interface OrderService 
{
    Page<Order> findAll(Pageable pageable);
    
    Optional<Order> findById(Long id);
    
    List<Order> findByUser(User user);
    
    Order save(Order order);
    
    Page<Order> findFiltred(Long orderStatusId, Long userId, Pageable pageable);
    
    void delete(Order order);
    
    Order createOrder(User user);

    public void sentOrder(Order order, Cart cart);

    Order confirmOrder(Order order);

    Order cancelOrder(Order order);

}
