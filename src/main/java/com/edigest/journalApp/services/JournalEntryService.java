package com.edigest.journalApp.services;

//contains business logic for api calls

import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired  //dependency injection for journalentryrepository(used to write sql queries)
    private JournalEntryRepository journalEnRep;

    @Autowired
    @org.springframework.context.annotation.Lazy
    private UserService userService;


    //method to get user, set date and save journalentry data to mongodb database
   @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
      try {
          User user = userService.findByUserName(userName); //finding user in database
          journalEntry.setDate(LocalDateTime.now()); //setting local date and time
          JournalEntry save = journalEnRep.save(journalEntry);//repository interface called to save the entry ( save is a local variable containing data of the saved entry returned after the execution of this method)
          user.getJournalEntries().add(save);//save the id of journal in journalEntries field of use pojo
          userService.saveEntry(user);//saving the user into the database after putting journal id in it)
      }catch(Exception e){
          System.out.println(e.getMessage());
          throw new RuntimeException("Error saving entry");
      }
    }


    //method for put request
    public void saveEntry(JournalEntry journalEntry){
        journalEnRep.save(journalEntry);
    }


//    to get all the entries from the database
    public List<JournalEntry> getAllEntries()
    {
        return journalEnRep.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId myid){
        return journalEnRep.findById(myid);
    }

    public void deleteEntryById(ObjectId myid, String userName){
        User user = userService.findByUserName(userName); //get user by username
        user.getJournalEntries().removeIf(x->x.getId().equals(myid)); //databse ke user pe removeif chlaya if x.get id equal to id jo hum pass krre hai
       userService.saveEntry(user); //save the update user
        journalEnRep.deleteById(myid);
    }
}
