/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.Cart;
import com.example.demo.site.entities.CartProduct;
import com.example.demo.site.entities.Product;
import com.example.demo.site.repositories.CartProductRepository;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Setir
 */

@Service
public class DefaultCartProductService implements CartProductService
{
    @Inject
    private CartProductRepository cartProductRepository;
    

    @Override
    public Optional<CartProduct> findByCart(Cart cart) {
        return cartProductRepository.findByCart(cart);
    }
    
    @Override
    public Optional<CartProduct> findById(Long id) {
        return cartProductRepository.findById(id);
    }

    @Override
    public CartProduct save(CartProduct cartProduct) {
        return cartProductRepository.save(cartProduct);
    }

    @Override
    public void delete(CartProduct cartProduct) {
        cartProductRepository.delete(cartProduct);
    }

    @Override
    public Optional<CartProduct> findByProductAndCart(Product product, Cart cart) {
        return cartProductRepository.findByProductAndCart(product, cart);
        
    }

    @Override
    public void editUp(CartProduct cartProduct) 
    {
       cartProduct.setAmount(cartProduct.getAmount()+1);
       cartProductRepository.save(cartProduct);
    }
   
    @Override
    public void editDown(CartProduct cartProduct) 
    {
       if(cartProduct.getAmount() == 1 )
       {
           cartProductRepository.delete(cartProduct);
           return;
       }
       
       cartProduct.setAmount(cartProduct.getAmount()-1);
       cartProductRepository.save(cartProduct);
    }
    
    
}
