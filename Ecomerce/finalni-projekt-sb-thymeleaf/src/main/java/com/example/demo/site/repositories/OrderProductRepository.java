/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.repositories;

import com.example.demo.site.entities.Order;
import com.example.demo.site.entities.OrderProduct;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Setir
 */

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long>
{
    Optional<OrderProduct> findByOrder(Order order);
    
    Optional<OrderProduct> findById(Long id);
    
    OrderProduct save(OrderProduct orderProduct);
    
    void delete(OrderProduct orderProduct);
    
}
