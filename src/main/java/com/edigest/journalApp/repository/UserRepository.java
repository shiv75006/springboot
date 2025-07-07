// Declares the package where this interface belongs
package com.edigest.journalApp.repository;

// Imports the User entity class
import com.edigest.journalApp.entity.User;
// Imports MongoDB's ObjectId type for document identifiers
import org.bson.types.ObjectId;
// Imports Spring Data MongoDB's MongoRepository interface
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * A Repository in Spring Data MongoDB provides an interface to perform database operations.
 * It uses method naming conventions to automatically generate queries without requiring SQL or MongoDB query language.
 * This repository handles User document operations and includes a custom finder method to retrieve users by username,
 * which Spring Data implements automatically based on the method name pattern.
 */

// Defines the UserRepository interface that extends MongoRepository for User entities with ObjectId as the ID type
public interface UserRepository extends MongoRepository<User, ObjectId> {
    // Declares a method to find a User by userName
    // Spring Data MongoDB will automatically implement this method based on the method name
    User findByUserName(String userName);
}
