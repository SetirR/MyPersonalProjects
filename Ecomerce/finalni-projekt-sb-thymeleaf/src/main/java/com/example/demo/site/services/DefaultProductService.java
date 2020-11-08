/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;

import com.example.demo.site.entities.Product;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.example.demo.site.repositories.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

/**
 *
 * @author Setir
 */

@Service
public class DefaultProductService implements ProductService 
{
    @Inject
    private ProductRepository articleRepository;
    
    @Override
    public Page<Product> findAll(Pageable pageable)
    {
        return articleRepository.findAll(pageable);
    }
    
    @Override
    public Optional<Product> findById(Long id)
    {
        return articleRepository.findById(id);
    }
    
    @Override
    public Product save(Product product) 
    {
        return articleRepository.save(product);
    }

    @Override
    public void delete(Product product) 
    {
        articleRepository.delete(product);
    }
    
    static Specification<Product> nameContains(String partOfName)
    {
        return (article, cq, cb) -> cb.like(article.get("name"), "%" + partOfName + "%");
    }
    
    static Specification<Product> hasCategoryId(Long categoryId)
    {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }
    
    static Specification<Product> hasBrandId(Long brandId)
    {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("brand").get("id"), brandId);
    }

    

    @Override
    public Page<Product> findFiltred(String name, Long categoryId, Long brandId, Pageable pageable) {
        Specification<Product> specification = null;
        
        if (!StringUtils.isEmpty(name))
        {
            specification = Specification.where(nameContains(name));
        }

        if (!StringUtils.isEmpty(categoryId)) {
            specification = (specification == null)
                    ? Specification.where(hasCategoryId(categoryId))
                    : specification.and(hasCategoryId(categoryId));
        }
        
        if (!StringUtils.isEmpty(brandId))
        {
            specification = (specification == null)
                    ? Specification.where(hasBrandId(brandId))
                    : specification.and(hasBrandId(brandId));
        }

        return articleRepository.findAll(specification, pageable);
    }

    
}
