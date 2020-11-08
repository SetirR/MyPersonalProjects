/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.controllers;

import com.example.demo.site.entities.Cart;
import com.example.demo.site.entities.CartProduct;
import com.example.demo.site.entities.Order;
import com.example.demo.site.entities.OrderProduct;
import com.example.demo.site.entities.User;
import com.example.demo.site.exceptions.CartNotFoundException;
import com.example.demo.site.exceptions.UserNotFoundException;
import com.example.demo.site.services.CartService;
import com.example.demo.site.services.OrderProductService;
import com.example.demo.site.services.OrderService;
import com.example.demo.site.services.OrderStatusService;
import com.example.demo.site.services.UserService;
import java.security.Principal;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Setir
 */

@Controller
public class EshopOrderController 
{
    @Inject
    private CartService cartService;
    
    @Inject
    private OrderService orderService;
    
    @Inject
    private UserService userService;
    
    
    
    @RequestMapping("/objednavka/odeslat/{id}")
    private String sentOrder(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes )
    {
        Optional<Cart> optCart = cartService.findById(id);
        if(optCart.isEmpty())
        {
            throw new CartNotFoundException("Kosik nenalezen");
        }
        Cart cart = optCart.get();
        
        Optional<User> optUser = userService.findByUsername(principal.getName());
        if(optUser.isEmpty())
        {
            throw new UserNotFoundException("Uzivatel nenalezen");
        }
        User user = optUser.get();
        
        orderService.sentOrder(orderService.createOrder(user), cart);
        redirectAttributes.addFlashAttribute("message", "Objednávka byla úspěšně odeslána");
        
        return "redirect:/zbozi";
        
    }
    
}
