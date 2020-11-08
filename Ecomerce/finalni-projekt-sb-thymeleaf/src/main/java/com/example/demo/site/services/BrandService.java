/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.Brand;
import com.example.demo.site.exceptions.BrandCantBeDeletedException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 *
 * @author Setir
 */

public interface BrandService 
{
    List<Brand> findAll();
    
    Page<Brand> findAll(Pageable pageable);
    
    Optional<Brand> findById(Long id);
    
    void delete(Brand brand) throws BrandCantBeDeletedException;
    
    Brand save(Brand brand);
    
    Page<Brand> findFiltered(String name, Pageable pageable);
    
    
}
