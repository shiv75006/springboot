package com.edigest.journalApp.services;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceimpl implements UserDetailsService {

    @Autowired
    private UserRepository userRep;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRep.findByUserName(username);
        if(user !=null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUserName())  //sending username from our database to security user instance
                    .password(user.getPassword()) //sending password from our database to security user implementation
                    .roles(user.getRoles().toArray(new String[0])) //sending roles from our database to security user
                    .build();

        }
         throw new UsernameNotFoundException("User not found");
    }
}
