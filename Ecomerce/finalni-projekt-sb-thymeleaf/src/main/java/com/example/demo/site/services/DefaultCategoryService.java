/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;



import com.example.demo.site.entities.Category;
import com.example.demo.site.repositories.CategoryRepository;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
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
public class DefaultCategoryService implements CategoryService 
{
    @Inject
    private CategoryRepository categoryRepository;
    
    @Override
    public Page<Category> findAll(Pageable pageable) 
    {
        return categoryRepository.findAll(pageable);
    }
    
    @Override
    public List<Category> findAll() 
    {
        return categoryRepository.findAll();
    }
    
    
    @Override
    public Optional<Category> findById(Long id)
    {
        return categoryRepository.findById(id);
    }
    
    @Override
    public Category save(Category category) 
    {
        return categoryRepository.save(category);
    }
    
    @Override
    public void delete(Category category)
    {
        categoryRepository.delete(category);
    }
    
    static Specification<Category> nameContains(String partOfName)
    {
        return (article, cq, cb) -> cb.like(article.get("name"), "%" + partOfName + "%");
    }

    @Override
    public Page<Category> findFiltered(String name, Pageable pageable)
    {
        Specification<Category> specification = null;

        if (!StringUtils.isEmpty(name))
        {
            specification = Specification.where(nameContains(name));
        }

        

        return categoryRepository.findAll(specification, pageable);
   }

    
}
