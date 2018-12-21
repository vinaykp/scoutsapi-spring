package com.example.scoutsapi.repositories;

import com.example.scoutsapi.model.Members;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;

import java.util.Optional;


public interface MembersRepository extends MongoRepository<Members, String> {
    Optional<Members> findBy_id(ObjectId _id);
}
