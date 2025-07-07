// Declares the package where this interface belongs
package com.edigest.journalApp.repository;

// Imports the JournalEntry entity class
import com.edigest.journalApp.entity.JournalEntry;
// Imports MongoDB's ObjectId type for document identifiers
import org.bson.types.ObjectId;
// Imports Spring Data MongoDB's MongoRepository interface
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * A Repository in Spring Data is an abstraction that provides a powerful way to interact with databases.
 * It eliminates boilerplate code by automatically implementing common database operations.
 * This interface extends MongoRepository to inherit CRUD operations (Create, Read, Update, Delete)
 * specifically for JournalEntry documents with ObjectId as their unique identifier.
 */

// Defines the JournalEntryRepository interface that extends MongoRepository for JournalEntry entities with ObjectId as the ID type
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
    // This interface inherits all standard CRUD operations from MongoRepository without defining any custom methods
}
