/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.controllers.Admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController
{
    
    //hlavní interface přihlášeného administrátora
    @RequestMapping("/admin")
    public String administrace()
    {
        return "admin/admin-interface";
    }
    
}
