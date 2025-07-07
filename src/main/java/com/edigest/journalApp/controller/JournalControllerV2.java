package com.edigest.journalApp.controller;

//REST API for mongodb database

import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.services.JournalEntryService;
import com.edigest.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

//a controller is a special type of component that handles our http requests
@RestController
@RequestMapping("/journal") //maps the complete class to the /journal endpoint
public class JournalControllerV2 {

    //dependency injection for journal entry service(business logic);
     @Autowired
     private JournalEntryService journalEntryService;


     @Autowired
     private UserService userService;

    //GET Request Mapping for /journal
    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
       //finding user by username
        User user = userService.findByUserName(userName);
        //a list of type JournalEntry storing journals fetched by getjournalentries method
       List<JournalEntry> all= user.getJournalEntries();
       //checking if the data exists and sending http status code
        if(all!=null && !all.isEmpty()){
            //returning data and custom http response
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //POST Request Mapping for /journal
    @PostMapping("{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
     try{
         journalEntryService.saveEntry(myEntry, userName);
         return new ResponseEntity<>(HttpStatus.CREATED);
     } catch(Exception e){
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }

    }

    //GET Request mapping for variable input (search for particular ID)
    @GetMapping("/id/{myId}")
    public JournalEntry getEntryById(@PathVariable ObjectId myId){
        return journalEntryService.getEntryById(myId).orElse(null);
    }

    //DELETE Request for variable input (Delete for particular id)
    @DeleteMapping("/id/{userName}/{myId}")
    public String deleteById(@PathVariable ObjectId myId,@PathVariable String userName){
         journalEntryService.deleteEntryById(myId,userName);
        return "record deleted";
    }

    //PUT Request (update existing record)
    @PutMapping("/id/{userName}/{id}")
    public ResponseEntity<?> updateById(
            @PathVariable ObjectId id,
            @PathVariable String userName,
            @RequestBody JournalEntry newEntry)
    {
       JournalEntry old = journalEntryService.getEntryById(id).orElse(null);
       if(old!=null){
           old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle()); //old ka title change kia
           old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent()); //old ka content change kia
           journalEntryService.saveEntry(old); //old with changed values ko db mai save kia
           return new ResponseEntity<>(old, HttpStatus.OK);
       }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}