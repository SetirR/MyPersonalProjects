/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.Order;
import com.example.demo.site.entities.OrderProduct;
import com.example.demo.site.repositories.OrderProductRepository;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Setir
 */

@Service
public class DefaultOrderProductService implements OrderProductService
{
    @Inject
    private OrderProductRepository orderProductRepository;
    

    @Override
    public Optional<OrderProduct> findByOrder(Order order)
    {
        return orderProductRepository.findByOrder(order);
    }

    @Override
    public Optional<OrderProduct> findById(Long id) 
    {
        return orderProductRepository.findById(id);
    }

    @Override
    public OrderProduct save(OrderProduct orderProduct)
    {
        return orderProductRepository.save(orderProduct);
    }

    @Override
    public void delete(OrderProduct orderProduct) {
        orderProductRepository.delete(orderProduct);
    }
    
    
    
}
