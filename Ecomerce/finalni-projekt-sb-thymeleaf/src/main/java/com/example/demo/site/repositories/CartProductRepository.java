/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.repositories;

import com.example.demo.site.entities.Cart;
import com.example.demo.site.entities.CartProduct;
import com.example.demo.site.entities.Product;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Setir
 */
public interface CartProductRepository extends CrudRepository<CartProduct, Long>
{
    Optional<CartProduct> findByCart(Cart cart);
    
    Optional<CartProduct> findById(Long id);
    
    CartProduct save(CartProduct cartProduct);
    
    void delete(CartProduct cartProduct);

    Optional<CartProduct> findByProductAndCart(Product product, Cart cart);
    
}
