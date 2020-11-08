/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;


import com.example.demo.site.entities.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Setir
 */
public interface CategoryService 
{   
    List<Category> findAll();
    
    Page<Category> findAll(Pageable pageable);
    
    Category save(Category category);
    
    Page<Category> findFiltered(String name, Pageable pageable);
    
    Optional<Category> findById(Long id);
    
    void delete(Category category);
    
    
}
