/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.Product;
import com.example.demo.site.entities.Brand;
import com.example.demo.site.entities.Category;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



/**
 *
 * @author Setir
 */
public interface ProductService 
{
    Page<Product> findAll(Pageable pageable);
    
    Optional<Product> findById(Long id);
    
    void delete(Product product);
    
    Product save(Product product);
    
    Page<Product> findFiltred(String name, Long categoryId, Long brandId, Pageable pageable);

}