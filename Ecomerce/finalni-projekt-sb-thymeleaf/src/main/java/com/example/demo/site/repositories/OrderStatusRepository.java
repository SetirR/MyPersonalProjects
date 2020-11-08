/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.repositories;

import com.example.demo.site.entities.OrderStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Setir
 */


public interface OrderStatusRepository extends CrudRepository<OrderStatus, Long>
{
    List<OrderStatus> findAll();
   
    Optional<OrderStatus> findByName(String name);
    
    @Override
    OrderStatus save(OrderStatus orderStatus);
    
    @Override
    void delete(OrderStatus orderStatus);
}
