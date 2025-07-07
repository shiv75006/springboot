package com.edigest.journalApp.config;


import com.edigest.journalApp.services.UserDetailsServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    @org.springframework.context.annotation.Lazy
    private UserDetailsServiceimpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeRequests()
                    .antMatchers("/public/**").permitAll()
                    .antMatchers("/journal/**").authenticated()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll()
                .and()
                .httpBasic(Customizer.withDefaults())
                .csrf().disable()
                .build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Use a direct instance of BCryptPasswordEncoder to avoid circular reference
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }  //fetches user by using UserServiceImpl and starts password matching. This method converts the password given by us into hashed password and then matches it with the password stored in database

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }  //password encoder
}
