package com.edigest.journalApp.controller;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.services.UserService;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers()
    {
      return userService.getAllEntries();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }

    //for updating both username and password
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userName){
        User userIndb=userService.findByUserName(userName);
        if(userIndb!=null){
            userIndb.setUserName(user.getUserName());
            // Encode the password before saving
            userIndb.setPassword(userService.encodePassword(user.getPassword()));
            // Preserve roles or set to USER if null
            if (userIndb.getRoles() == null || userIndb.getRoles().isEmpty()) {
                userIndb.setRoles(List.of("USER"));
            }
            userService.saveEntry(userIndb);
        }
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }





}
