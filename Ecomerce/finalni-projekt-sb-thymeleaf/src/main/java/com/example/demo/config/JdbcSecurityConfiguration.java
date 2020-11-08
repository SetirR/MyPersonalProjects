package com.example.demo.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class JdbcSecurityConfiguration extends WebSecurityConfigurerAdapter 
{    
    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); 
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {            
        auth
            .jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder())
            
            .usersByUsernameQuery(
                "SELECT username as username, password as password, enabled as enabled " 
                    + "FROM `user` "
                    + "WHERE username = ?")
            
            .authoritiesByUsernameQuery(
                "SELECT u.username AS username, a.role AS role "
                    + "FROM `user` AS u, user_authority AS a "
                    + "WHERE u.id = a.user_id "
                    + "AND u.username = ?");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception 
    {
        http.formLogin();

        http
            .authorizeRequests()
            .antMatchers("/admin/**").hasAuthority("ADMIN") // v databazi stac byt ADMIN // hasRole("ADMIN") === v databazi musi byt ROLE_ADMIN   
            .antMatchers("/kosik/**").authenticated()
            .antMatchers("/").permitAll();
        
        http
            .logout()
            .logoutSuccessUrl("/");    
    }



}