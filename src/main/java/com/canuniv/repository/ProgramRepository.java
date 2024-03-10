package com.canuniv.repository;

import com.canuniv.document.ProgramDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProgramRepository extends MongoRepository<ProgramDocument, String> {


    Optional<ProgramDocument> findByName(String name);

    @Query("{ 'name' : { $in: ?0 } }")
    List<ProgramDocument> findByNames(Set<String> programNames);
}
