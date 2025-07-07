package com.edigest.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//a simple java class mapped as a mongodb document
@Document(collection = "users")
@Data
public class User {

    @Id //maps this id with the id column in mongodb document
    private ObjectId id;
    @Indexed(unique = true) //indexing makes searching faster and sets all users to be unique
    @NonNull  //annotation for checking null values
    private String userName;
    @NonNull
    private String password;

    private List<String> roles;

    @DBRef //creating a reference of journal entries from journal_entries in the mongodb database
    private List<JournalEntry> journalEntries= new ArrayList<>();

}
