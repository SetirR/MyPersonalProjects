/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.site.services;


import com.example.demo.site.entities.User;
import com.example.demo.site.repositories.UserRepository;


import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Setir
 */

@Service
public class DefaultUserService implements UserService 
{
    @Inject
    private UserRepository userRepository;
    
    @Override
    public List<User> findAll() 
    {
        return userRepository.findAll();
    }
    
    @Override
    public User save(User user) 
    {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
