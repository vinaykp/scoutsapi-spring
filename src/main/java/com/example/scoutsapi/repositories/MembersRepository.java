package com.example.scoutsapi.repositories;

import com.example.scoutsapi.model.Members;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface MembersRepository extends MongoRepository<Members, String> {
    Optional<Members> findByMemberId(String memberId);
}
