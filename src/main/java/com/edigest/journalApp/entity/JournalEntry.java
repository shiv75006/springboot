package com.edigest.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

//a simple java class mapped as a mongodb document
@Document(collection = "journal_entries")
@Data
public class JournalEntry {

    @Id //maps this id with the id column in mongodb document
    private ObjectId id;
    private String title;
    private String Content;
    private LocalDateTime date;

}
