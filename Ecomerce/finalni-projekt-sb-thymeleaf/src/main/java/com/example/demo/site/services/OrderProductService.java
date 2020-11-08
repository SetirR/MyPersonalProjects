/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.Order;
import com.example.demo.site.entities.OrderProduct;
import java.util.Optional;

/**
 *
 * @author Setir
 */
public interface OrderProductService 
{
    Optional<OrderProduct> findByOrder(Order order);
    
    Optional<OrderProduct> findById(Long id);
    
    OrderProduct save(OrderProduct orderProduct);
    
    void delete(OrderProduct orderProduct);
    
}
