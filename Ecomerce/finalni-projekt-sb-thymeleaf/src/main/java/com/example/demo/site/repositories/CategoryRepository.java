/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.repositories;


import com.example.demo.site.entities.Category;
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


public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>, JpaSpecificationExecutor<Category>
{
    @Override
    List<Category> findAll();
    
    @Override
    Page<Category> findAll(Specification<Category> specification, Pageable pageable);
    
    Category save(Category category);
    
    @Override
    Optional<Category> findById(Long id);
    
    @Override
    void delete(Category category);
    
    
}
