package com.edigest.journalApp.services;

//contains business logic for api calls

import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.JournalEntryRepository;
import com.edigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired  //dependency injection for journalentryrepository(used to write sql queries)
    private UserRepository userRep;

    //an instance of bcrypt password encoder
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public void saveEntry(User user){ userRep.save(user); //repository interface called
    }

    //save user and password using bcrypt encoder
    public void saveNewUser(User user){
      user.setPassword(encoder.encode((user.getPassword())));
      user.setRoles(List.of("USER"));
      userRep.save(user);
    }

//    to get all the entries from the database
    public List<User> getAllEntries()
    {
        return userRep.findAll();
    }

    public Optional<User> getEntryById(ObjectId myid){
        return userRep.findById(myid);
    }

    public void deleteEntryById(ObjectId myid){
        userRep.deleteById(myid);
    }

    public User findByUserName(String userName){
        return userRep.findByUserName(userName);
    }

    // Method to encode a password
    public String encodePassword(String password) {
        return encoder.encode(password);
    }
}
