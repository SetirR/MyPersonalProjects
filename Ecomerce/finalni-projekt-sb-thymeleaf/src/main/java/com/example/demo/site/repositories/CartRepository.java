/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.repositories;

import com.example.demo.site.entities.Cart;
import com.example.demo.site.entities.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Setir
 */


public interface CartRepository extends CrudRepository<Cart, Long>
{
    Optional<Cart> findById(Long id);
    
    Optional<Cart> findByUser(User user);
    
    Cart save(Cart cart);
    
    void delete(Cart cart);
    
}
