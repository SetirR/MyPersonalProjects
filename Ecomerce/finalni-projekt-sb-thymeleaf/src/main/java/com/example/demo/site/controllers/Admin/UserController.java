/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.controllers.Admin;

import com.example.demo.site.entities.User;
import com.example.demo.site.services.UserService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/uzivatele")
@Controller
public class UserController
{
    @Inject
        private UserService userService;  
    
    @RequestMapping("/zobrazit")
    public String showAll(Model model)
    {
              
        
        List<User> users = userService.findAll();
        
        model.addAttribute("uzivatele", users);
        
        return "admin/user/interface";
    }
    
    
}
