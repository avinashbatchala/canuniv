package com.canuniv.controller;

import com.canuniv.document.UniversityDocument;
import com.canuniv.service.UniversityService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/university")
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @PostMapping("/addUniversity")
    public ResponseEntity<UniversityDocument> addUniversity(@RequestBody UniversityDocument universityDocument) {
        return ResponseEntity.ok(universityService.saveUniversity(universityDocument));
    }

    @PostMapping("/refreshUnivesities")
    public ResponseEntity<String> refreshUniversities() {
        try {
            universityService.getUniversitiesFromWikipedia();
            return ResponseEntity.ok("Universities refreshed successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error occurred while refreshing universities");
        }
    }

    @GetMapping("/getAllUniversities")
    public ResponseEntity<List<UniversityDocument>> getAllUniversities() {
      return ResponseEntity.ok(universityService.getAllUniversities());
    }
}
