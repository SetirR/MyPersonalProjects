/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.forms;

import javax.validation.constraints.NotBlank;


/**
 *
 * @author Setir
 */
public class BrandForm 
{
    private Long id;

    
    
    @NotBlank(message = "Jmeno musi byt vyplneno")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
