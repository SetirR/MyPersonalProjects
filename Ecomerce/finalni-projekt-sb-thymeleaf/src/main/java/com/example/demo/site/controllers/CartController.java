/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.controllers;

import com.example.demo.site.entities.Cart;
import com.example.demo.site.entities.CartProduct;
import com.example.demo.site.entities.Product;
import com.example.demo.site.entities.User;
import com.example.demo.site.exceptions.CartProductNotFoundException;
import com.example.demo.site.exceptions.ProductNotFoundException;
import com.example.demo.site.exceptions.UserNotFoundException;
import com.example.demo.site.services.CartProductService;
import com.example.demo.site.services.CartService;
import com.example.demo.site.services.ProductService;

import com.example.demo.site.services.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Setir
 */

@Controller
public class CartController 
{
    @Inject
    private ProductService productService;
    
    @Inject
    private CartProductService cartProductService;
    
    @Inject
    private CartService cartService;
    
    @Inject
    private UserService userService;
    
    @RequestMapping("/kosik")
    private String cart(Model model, Principal principal) 
    {   
        Optional<User> optUser = userService.findByUsername(principal.getName());
        if(optUser.isEmpty())
        {
            throw new UserNotFoundException("Uzivatel nenalezen");
        }
        
        User user = optUser.get();
        
        Cart cart = cartService.findByUser(user);
        cartService.save(cart);
        
        List<CartProduct> cartProducts = cart.getCardProducts();
        
        model.addAttribute("cartProducts", cartProducts);
        model.addAttribute("cart", cart);
        
        return "eshop/business/cart";
        
    }
    
    @RequestMapping("/kosik/add/{id}")
    private String cartAdd(Model model, @PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes )
    {
        Optional<Product> optProduct = productService.findById(id);
        if(optProduct.isEmpty())
        {
            throw new ProductNotFoundException("Produkt nenalezen");
        }
        Product product = optProduct.get();
        
        
        Optional<User> optUser = userService.findByUsername(principal.getName());
        if(optUser.isEmpty())
        {
            throw new UserNotFoundException("Uzivatel nenalezen");
        }
        User user = optUser.get();
        
        
        CartProduct cartProduct = cartService.addingProduct(product, user);
        redirectAttributes.addFlashAttribute("message", "Product "+ cartProduct.getProduct().getName() +" byl přidán do košíku.");
        return "redirect:/kosik";
        
    }
    
    @RequestMapping("/kosik/editup/{id}")
    private String editUp(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes )
    {
       Optional<CartProduct> optCartProduct = cartProductService.findById(id);
       
       if(optCartProduct.isEmpty())
       {
           throw new CartProductNotFoundException("Produkt nenalezen");
       }
       
       CartProduct cartProduct = optCartProduct.get();
       cartProductService.editUp(cartProduct);
       redirectAttributes.addFlashAttribute("message", "Počet produktu "+ cartProduct.getProduct().getName() +" byl změněn.");
       return "redirect:/kosik";
       
    }
    
    @RequestMapping("/kosik/editdown/{id}")
    private String editDown(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes )
    {
       Optional<CartProduct> optCartProduct = cartProductService.findById(id);
       
       if(optCartProduct.isEmpty())
       {
           throw new CartProductNotFoundException("Produkt nenalezen");
       }
       
       CartProduct cartProduct = optCartProduct.get();
       cartProductService.editDown(cartProduct);
       redirectAttributes.addFlashAttribute("message", "Počet produktu "+ cartProduct.getProduct().getName() +" byl změněn.");
       return "redirect:/kosik";
       
    }
    
    @RequestMapping("/kosik/delete/{id}")
    private String delete(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes )
    {
       Optional<CartProduct> optCartProduct = cartProductService.findById(id);
       
       if(optCartProduct.isEmpty())
       {
           throw new CartProductNotFoundException("Produkt nenalezen");
       }
       
       CartProduct cartProduct = optCartProduct.get();
       cartProductService.delete(cartProduct);
       redirectAttributes.addFlashAttribute("message", "Product "+ cartProduct.getProduct().getName() +" byl odebrán z košíku.");
       return "redirect:/kosik";
       
    }
}
