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
import com.example.demo.site.repositories.CartRepository;
import java.util.Optional;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 *
 * @author Setir
 */

@Service
public class DefaultCartService implements CartService
{
    @Inject
    private CartRepository cartRepository;
    
    @Inject
    private CartProductService cartProductService;
    
    @Override
    public Cart findByUser(User user) 
    {
        
        if(cartRepository.findByUser(user).isEmpty())
        {
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
            return cart;
        }
        
        return cartRepository.findByUser(user).get();
    }


    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }
    
    @Override
    public void delete(Cart cart)
    {
        cartRepository.delete(cart);
    }

    @Override
    @Transactional
    public CartProduct addingProduct(Product product, User user) {
        
        Cart cart = findByUser(user);
        Optional<CartProduct> optCartProduct = cartProductService.findByProductAndCart(product,cart);
        if(optCartProduct.isPresent())
        {
            CartProduct cartProduct = optCartProduct.get();
            cartProduct.setAmount(cartProduct.getAmount()+1);
            return cartProductService.save(cartProduct);
                   
        }
        
        CartProduct cartProduct = new CartProduct();
        cartProduct.setProduct(product);
        cartProduct.setPrice(product.getPrice());
        cartProduct.setAmount(1);
        cartProduct.setCart(cart);
        return cartProductService.save(cartProduct);
    }
}
