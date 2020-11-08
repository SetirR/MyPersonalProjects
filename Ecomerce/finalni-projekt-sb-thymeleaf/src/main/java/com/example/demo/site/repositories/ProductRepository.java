/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.repositories;

import com.example.demo.site.entities.Product;
import com.example.demo.site.entities.Brand;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Setir
 */


public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product>
{

    Page<Product> findAll(Specification<Product> specification, Pageable pageable);
    
    @Override
    Optional<Product> findById(Long id);
    
    @Override
    Product save(Product product);
    
    @Override
    void delete(Product product);
}
