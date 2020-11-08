/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.OrderStatus;
import com.example.demo.site.repositories.OrderStatusRepository;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Setir
 */

@Service
public class DefaultOrderStatusService implements OrderStatusService 
{
    @Inject
    private OrderStatusRepository orderStatusRepository;

    @Override
    public List<OrderStatus> findAll() {
        return orderStatusRepository.findAll();
    }

    @Override
    public Optional<OrderStatus> findByName(String name) {
        return orderStatusRepository.findByName(name);
    }

    @Override
    public OrderStatus save(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }

    @Override
    public void delete(OrderStatus orderStatus) {
        orderStatusRepository.delete(orderStatus);
    }

    
}
