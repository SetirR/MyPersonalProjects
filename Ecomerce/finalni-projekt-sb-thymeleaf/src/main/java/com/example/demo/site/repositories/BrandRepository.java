/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.repositories;

import com.example.demo.site.entities.Brand;
import com.example.demo.site.exceptions.BrandCantBeDeletedException;
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


public interface BrandRepository extends PagingAndSortingRepository<Brand, Long>, JpaSpecificationExecutor<Brand> 
{
    List<Brand> findAll();
    
    Page<Brand> findAll(Specification<Brand> specification, Pageable pageable);
    
    @Override
    Optional<Brand> findById(Long id);
    
    @Override
    Brand save(Brand brand);
    
    @Override
    void delete(Brand brand);
}
