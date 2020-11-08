/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.OrderStatus;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Setir
 */
public interface OrderStatusService
{
    List<OrderStatus> findAll();
    
    Optional<OrderStatus> findByName(String name);
    
    OrderStatus save(OrderStatus orderStatus);
    
    void delete(OrderStatus orderStatus);
    
}
