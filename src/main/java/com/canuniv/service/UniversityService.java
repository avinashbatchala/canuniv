package com.canuniv.service;

import com.canuniv.document.UniversityDocument;

import java.io.IOException;
import java.util.List;

public interface UniversityService {
    UniversityDocument saveUniversity(UniversityDocument universityDocument);

    void getUniversitiesFromWikipedia() throws IOException;

    List<UniversityDocument> getAllUniversities();


}
