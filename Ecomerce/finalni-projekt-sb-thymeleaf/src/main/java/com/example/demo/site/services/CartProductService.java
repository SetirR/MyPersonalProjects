/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.Cart;
import com.example.demo.site.entities.CartProduct;
import com.example.demo.site.entities.Product;
import java.util.Optional;

/**
 *
 * @author Setir
 */
public interface CartProductService 
{
    Optional<CartProduct> findByCart(Cart cart);
    
    Optional<CartProduct> findById(Long id);
    
    CartProduct save(CartProduct cartProduct);
    
    void delete(CartProduct cartProduct);

    public Optional<CartProduct> findByProductAndCart(Product product, Cart cart);

    public void editUp(CartProduct cartProduct);
    
    public void editDown(CartProduct cartProduct);
    
}
