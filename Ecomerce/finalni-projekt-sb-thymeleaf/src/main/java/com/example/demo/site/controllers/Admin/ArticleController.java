/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.controllers.Admin;

import com.example.demo.site.entities.Brand;
import com.example.demo.site.entities.Category;
import com.example.demo.site.entities.Product;
import com.example.demo.site.exceptions.ProductNotFoundException;
import com.example.demo.site.forms.ProductForm;
import com.example.demo.site.forms.ProductFormFiltred;
import com.example.demo.site.services.BrandService;
import com.example.demo.site.services.CategoryService;
import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.site.services.ProductService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.util.StringUtils;


@RequestMapping("/admin/zbozi")
@Controller
public class ArticleController
{
    @Inject
    private ProductService productService;
    
    @Inject 
    private BrandService brandService;
    
    @Inject
    private CategoryService categoryService;
    
    @GetMapping(value="zobrazit")
    public String showAll(Model model, Pageable pageable, @RequestParam(required = false) String name, Long brandId, Long categoryId)
    {
        boolean showResetButton =
                !StringUtils.isEmpty(name)
                || brandId != null
                || categoryId != null;
        model.addAttribute("showResetButton", showResetButton);

        List<Brand> brands = brandService.findAll();
        List<Category> categories = categoryService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);

        ProductFormFiltred productFormFiltred = new ProductFormFiltred();
        productFormFiltred.setName(name);
        productFormFiltred.setBrandId(brandId);
        productFormFiltred.setCategoryId(categoryId);

        model.addAttribute("productFormFiltred" , productFormFiltred);


        // jmeno, categoryId, brandId, musi byt v tomto poradi, protoze v metode je to taky takhle !
        Page<Product> page = productService.findFiltred(name, categoryId, brandId, pageable);

        model.addAttribute("page", page);

        model.addAttribute("sort", page
                .getSort()
                .get()
                .map(s -> s.getProperty() + "," + s.getDirection().name().toLowerCase())
                .findFirst()
                .orElse(null));
        
        return "admin/article/interface";
    }

    @PostMapping("/zobrazit")
    public RedirectView showAll(ProductFormFiltred productFormFiltred, RedirectAttributes redirectAttributes)
    {
        String name = productFormFiltred.getName();
        if (!StringUtils.isEmpty(name))
            redirectAttributes.addAttribute("name", name);

        Long brandId = productFormFiltred.getBrandId();
        if (brandId != null)
            redirectAttributes.addAttribute("brandId", brandId);

        Long categoryId = productFormFiltred.getCategoryId();
        if (categoryId != null)
            redirectAttributes.addAttribute("categoryId", categoryId);


        return new RedirectView("/admin/zbozi/zobrazit", true);
    }
    
    
    @GetMapping(value="pridat-zbozi")
    public String addArticle(Model model)
    {
        List<Brand> brands = brandService.findAll();
        List<Category> categories = categoryService.findAll();
        
        model.addAttribute("productForm", new ProductForm());
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        
        return "admin/article/add";
    }
    
    @PostMapping(value="pridat-zbozi")
    public String addArticle(@Valid ProductForm productForm, BindingResult bindingResult, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors()){
            return "admin/article/add";
        }
        
        Product product = new Product();
        
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setDescription(productForm.getDescription());
        
        Optional<Brand> optBrand = brandService.findById(productForm.getBrandId());
        Brand brand = optBrand.get();
        product.setBrand(brand);
        
        Optional<Category> optCategory = categoryService.findById(productForm.getCategoryId());
        Category category = optCategory.get();
        product.setCategory(category);
        
        product.setEnabled(true);
        
        try
        {
            Product newProduct = productService.save(product);
            redirectAttributes.addFlashAttribute("message", "Zboží "+ newProduct.getName() +" bylo úspěšně přidáno");
            return "redirect:/admin/zbozi/zobrazit";
        }
        catch(Exception e)
        {
            redirectAttributes.addFlashAttribute("error", "Zboži nelze přidat");
            return "redirect:/admin/zbozi/zobrazit";
        }
        
        
    }
    
    
    
    @RequestMapping(value="editace-zbozi/{id}", method=RequestMethod.GET)
    public String editArticle(Model model, @PathVariable Long id)
    {
        Optional<Product> optionalProduct = productService.findById(id);
        if(!optionalProduct.isPresent())
            throw new ProductNotFoundException();
        Product product = optionalProduct.get();
        
        List<Brand> brands = brandService.findAll();
        List<Category> categories = categoryService.findAll();
        
        model.addAttribute("categories", categories);
        model.addAttribute("brands", brands);
        model.addAttribute("productId", product.getId());
        
        ProductForm productForm = new ProductForm();
        productForm.setName(product.getName());
        productForm.setPrice(product.getPrice());
        productForm.setDescription(product.getDescription());
        productForm.setEnabled(product.isEnabled());
        productForm.setBrandId(product.getBrand().getId());
        productForm.setCategoryId(product.getCategory().getId());
        
        model.addAttribute("productForm", productForm);
        
        return "admin/article/edit";
    }
    
    
    @RequestMapping(value="editace-zbozi/{id}", method=RequestMethod.POST)
    public String editArticle(@Valid ProductForm productForm, BindingResult bindingResult, @PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors()){
            return "admin/article/edit";
        }
        
        Optional<Product> product = productService.findById(id);
        if(!product.isPresent()) 
            throw new ProductNotFoundException();
        
        Product productEdit = product.get();
        productEdit.setName(productForm.getName());
        productEdit.setPrice(productForm.getPrice());
        productEdit.setDescription(productForm.getDescription());
        productEdit.setEnabled(true);
        
        Optional<Brand> newBrand = brandService.findById(productForm.getBrandId());
        Brand editedBrand = newBrand.get();
        productEdit.setBrand(editedBrand);
        
        Optional<Category> newCategory = categoryService.findById(productForm.getCategoryId());
        Category editedCategory = newCategory.get();
        productEdit.setCategory(editedCategory);
        
        
        productService.save(productEdit);
           
        
        redirectAttributes.addFlashAttribute("message", "Produkt "+ product.get().getName() +" byl úspěšně upraven");
        
        return "redirect:/admin/zbozi/zobrazit";
    }
    
    @RequestMapping(value="smazani-zbozi/{id}")
    public String deleteArticle(@PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        Optional<Product> optProduct = productService.findById(id);
        
        if(!optProduct.isPresent())
        {
            throw new ProductNotFoundException();
        }
        
        Product product = optProduct.get();
        
        try
        {
          productService.delete(product);  
        }
        catch(Exception e)
        {
            redirectAttributes.addFlashAttribute("error", "Zbozi "+ product.getName() +" nemohlo byt smazano");
            return "redirect:/admin/zbozi/zobrazit";
        }        
        
        redirectAttributes.addFlashAttribute("message", "Zbozi "+ product.getName() +" bylo úspěšně smazano");
        
        return "redirect:/admin/zbozi/zobrazit";
    }
    
    
}
