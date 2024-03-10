package com.canuniv.repository;

import com.canuniv.document.UniversityDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UniversityRepository extends MongoRepository<UniversityDocument, String> {

}
