/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.Brand;
import com.example.demo.site.exceptions.BrandCantBeDeletedException;
import com.example.demo.site.repositories.BrandRepository;

import java.util.List;
import java.util.Optional;
import javax.inject.Inject;

import org.hibernate.QueryException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author Setir
 */

@Service
public class DefaultBrandService implements BrandService 
{
    @Inject
    private BrandRepository brandRepository;
    
    @Override
    public Page<Brand> findAll(Pageable pageable) 
    {
        return brandRepository.findAll(pageable);
    }
    
    @Override
    public Optional<Brand> findById(Long id)
    {
        return brandRepository.findById(id);
    }
    
    @Override
    public Brand save(Brand brand) 
    {
        return brandRepository.save(brand);
    }

    @Override
    public void delete(Brand brand) throws BrandCantBeDeletedException
    {
        try
        {
            brandRepository.delete(brand);
        }
        catch(DataIntegrityViolationException e)
        {
            throw new BrandCantBeDeletedException("Znacku nejde smazat kvuli referencni integrite dat v databazi", e);
        }
    }
    
    static Specification<Brand> nameContains(String partOfName)
    {
        return (article, cq, cb) -> cb.like(article.get("name"), "%" + partOfName + "%");
    }

    @Override
    public Page<Brand> findFiltered(String name, Pageable pageable)
    {
        Specification<Brand> specification = null;

        if (!StringUtils.isEmpty(name))
        {
            specification = Specification.where(nameContains(name));
        }

        

        return brandRepository.findAll(specification, pageable);
   }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    
    
}
