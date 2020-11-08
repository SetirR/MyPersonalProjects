/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.Cart;
import com.example.demo.site.entities.CartProduct;
import com.example.demo.site.entities.Product;
import com.example.demo.site.entities.User;
import java.util.Optional;

/**
 *
 * @author Setir
 */

public interface CartService 
{
    Optional<Cart> findById(Long id);
    
    Cart findByUser(User user);
    
    Cart save(Cart cart);
    
    void delete(Cart cart);

    public CartProduct addingProduct(Product product, User user);
    
    
}
