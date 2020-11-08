/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.controllers;

import com.example.demo.json.CaptchaResponse;
import com.example.demo.settings.CaptchaSettings;
import com.example.demo.settings.MailSettings;
import com.example.demo.site.entities.Brand;
import com.example.demo.site.entities.Category;
import com.example.demo.site.entities.Product;
import com.example.demo.site.forms.MailForm;
import com.example.demo.site.forms.ProductFormFiltred;
import com.example.demo.site.services.BrandService;
import com.example.demo.site.services.CategoryService;
import com.example.demo.site.services.MailService;
import com.example.demo.site.services.ProductService;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.thymeleaf.util.StringUtils;

@Controller
public class MainPageController
{
    @Inject
    private MailService mailService;

    @Inject
    private MailSettings mailSettings;

    @Inject
    private RestTemplate restTemplate;

    @Inject
    private CaptchaSettings captchaSettings;
    
    @Inject
    private ProductService productService;
    
    @Inject
    private BrandService brandService;
    
    @Inject
    private CategoryService categoryService;
    
    @RequestMapping("/")
    public String hello()
    {
        return "redirect:/zbozi";
    }
    
    @GetMapping(value = {"/kontakty"})
    public String mail(Model model)
    {
        model.addAttribute("mailForm", new MailForm());
        return "eshop/business/contacts";
    }

    @PostMapping(value = {"/kontakty"})
    public String mail(Model model, @Valid MailForm mailForm, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, @RequestParam(name = "g-recaptcha-response") String reCaptchaResponse)
    {
        if (bindingResult.hasErrors())
        {
            model.addAttribute("message", "Formular obsahuje chyby");
            return "eshop/business/contacts";
        }

        String url = captchaSettings.getUrl() + "?secret=" + captchaSettings.getSecret() + "&response=" + reCaptchaResponse;
        CaptchaResponse captchaResponse = restTemplate.exchange(url, HttpMethod.POST, null, CaptchaResponse.class).getBody();
        if(!captchaResponse.isSuccess())
        {
            model.addAttribute("message", "Chyba při zpracování reCaptcha");
            return "eshop/business/contacts";
        }

        try
        {
            mailService.sendMessage(
                mailForm.getEmail(),
                mailSettings.getUsername(),
                "Zpráva od " + mailForm.getEmail(),
                "Zpráva: " + mailForm.getMessage());
        }
        catch (Exception e)
        {
            redirectAttributes.addFlashAttribute("message", "Zprava nebyla odeslana.");
            System.err.println(e.getMessage());
            return "redirect:/kontakty";
            
        }

        redirectAttributes.addFlashAttribute("message", "Zprava byla odeslana.");
        return "redirect:/kontakty";
    }
    
    @GetMapping(value="zbozi")
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
        
        return "eshop/articles/articles-all";
    }

    @PostMapping("/zbozi")
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


        return new RedirectView("/zbozi", true);
    }
    
    @RequestMapping("/zbozi/detail/{id}")
    public String detail(@PathVariable Long id, Model model)
    {
        Optional<Product> optProduct = productService.findById(id);
        
        if(optProduct.isEmpty())
            return "redirect:/zbozi";
        
        Product product = optProduct.get();
        model.addAttribute("product", product);
        
        return "eshop/articles/detail";
    }
    
}
