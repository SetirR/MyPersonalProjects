/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.repositories;

import com.example.demo.site.entities.Order;
import com.example.demo.site.entities.Product;
import com.example.demo.site.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Setir
 */


public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, JpaSpecificationExecutor<Order>
{
    
    Page<Order> findAll(Specification<Order> specification, Pageable pageable);
    
    List<Order> findAll();
    
    Optional<Order> findById(Long id);
    
    List<Order> findByUser(User user);
    
    Order save(Order order);
    
    void delete(Order order);
    
}
