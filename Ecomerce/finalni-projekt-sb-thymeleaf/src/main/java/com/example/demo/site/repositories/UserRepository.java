/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.repositories;


import com.example.demo.site.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Setir
 */


public interface UserRepository extends CrudRepository<User, Long> 
{
    @Override
    List<User> findAll();
    
    Optional<User> findByUsername(String username);
    
    User save(User user);
}
