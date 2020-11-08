/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.controllers.Admin;

import com.example.demo.site.entities.Category;
import com.example.demo.site.exceptions.CategoryNotFoundException;
import com.example.demo.site.forms.CategoryForm;
import com.example.demo.site.forms.CategoryFormFiltred;
import com.example.demo.site.services.CategoryService;
import java.util.Optional;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author Setir
 */

@RequestMapping("/admin/kategorie")
@Controller
public class CategoryController
{
    @Inject
    private CategoryService categoryService;
 
     //administrace značek
    @RequestMapping("/zobrazit")
    public String showAll(Model model, Pageable pageable,@RequestParam(required = false) String name)
    {
        
        boolean showResetButton =
                !StringUtils.isEmpty(name);
        model.addAttribute("showResetButton", showResetButton);
        
        
        
        CategoryFormFiltred categoryFormFiltred = new CategoryFormFiltred();
        categoryFormFiltred.setName(name);
        
        model.addAttribute("categoryFormFiltred", categoryFormFiltred);
        
        
        Page<Category> page = categoryService.findFiltered(name, pageable);
        model.addAttribute("page", page);
        
        
        
        model.addAttribute("sort", page
                .getSort()
                .get()
                .map(s -> s.getProperty() + "," + s.getDirection().name().toLowerCase())
                .findFirst()
                .orElse(null));
        
        return "admin/category/interface";
    }
    
    @PostMapping("/zobrazit")
    public RedirectView showAll(CategoryFormFiltred categoryFormFiltred, RedirectAttributes redirectAttributes)
    {
        String name = categoryFormFiltred.getName();
        if (!StringUtils.isEmpty(name))
            redirectAttributes.addAttribute("name", name);
       
        return new RedirectView("/admin/kategorie/zobrazit", true);
    }
    
    
    @RequestMapping(value="pridat-kategorii", method=RequestMethod.GET)
    public String addCategory(Model model)
    {
        model.addAttribute("categoryForm", new CategoryForm());
                
        return "admin/category/add";
    }
    
    @RequestMapping(value="pridat-kategorii", method=RequestMethod.POST)
    public String addCategory(@Valid CategoryForm categoryForm, BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors()){
            return "admin/category/add";
        }
        
            Category cat = new Category();
            cat.setName(categoryForm.getName());
            
        try
        {
            categoryService.save(cat);
            redirectAttributes.addFlashAttribute("message", "Kategorie "+ cat.getName() +" byla úspěšně přidána");
            return "redirect:/admin/kategorie/zobrazit";
        }
        catch(Exception e)
        {
            redirectAttributes.addFlashAttribute("error", "Kategorii nelze přidat");
            return "redirect:/admin/kategorie/zobrazit";
        }
    }    
    @RequestMapping(value="editace-kategorie/{id}", method=RequestMethod.GET)
    public String editCategory(Model model, @PathVariable Long id)
    {
        Optional<Category> category = categoryService.findById(id);
        if(!category.isPresent()) 
            throw new CategoryNotFoundException();
        
        model.addAttribute("categoryId", category.get().getId());
        
        CategoryForm categoryForm = new CategoryForm();
        categoryForm.setName(category.get().getName());
        model.addAttribute("categoryForm", categoryForm);
        
        return "admin/category/edit";
    }
    
    
    @RequestMapping(value="editace-kategorie/{id}", method=RequestMethod.POST)
    public String editBrand(@Valid CategoryForm categoryForm, BindingResult bindingResult, @PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors()){
            return "admin/category/edit";
        }
        
        Optional<Category> category = categoryService.findById(id);
        if(!category.isPresent()) 
            throw new CategoryNotFoundException();
        
        Category categoryEdit = category.get();
        categoryEdit.setName(categoryForm.getName());
        categoryService.save(categoryEdit);   
        
        redirectAttributes.addFlashAttribute("message", "Značka "+ category.get().getName() +" byla úspěšně upravena");
        
        return "redirect:/admin/kategorie/zobrazit";
    }
    
    @RequestMapping(value="smazani-kategorie/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        Optional<Category> category = categoryService.findById(id);
        if(!category.isPresent())
        {
            throw new CategoryNotFoundException();
        }

        try
        {
          categoryService.delete(category.get());  
        }
        catch(DataIntegrityViolationException e)
        {
            redirectAttributes.addFlashAttribute("error", "Kategorie "+ category.get().getName() +" nemohla byt smazana");
            return "redirect:/admin/kategorie/zobrazit";
        }        
        
        redirectAttributes.addFlashAttribute("message", "Kategorie "+ category.get().getName() +" byla úspěšně smazana");
        
        return "redirect:/admin/kategorie/zobrazit";
    }
}

