package com.canuniv.service;

import com.canuniv.document.UniversityDocument;
import com.canuniv.repository.ProgramRepository;
import com.canuniv.repository.UniversityRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import com.canuniv.document.ProgramDocument;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;
    private final ProgramRepository programRepository;

    public UniversityServiceImpl(UniversityRepository universityRepository, ProgramRepository programRepository) {
        this.universityRepository = universityRepository;
        this.programRepository = programRepository;
    }

    @Override
    public UniversityDocument saveUniversity(UniversityDocument universityDocument) {

        // Collect all program names from the university document
        Set<String> programNames = universityDocument.getPrograms().stream()
                .map(ProgramDocument::getName)
                .collect(Collectors.toSet());

        // Find all existing programs by names
        List<ProgramDocument> existingPrograms = programRepository.findByNames(programNames);
        Map<String, ProgramDocument> nameToExistingProgramMap = existingPrograms.stream()
                .collect(Collectors.toMap(ProgramDocument::getName, Function.identity()));

        // Prepare the final set of programs for the university document
        Set<ProgramDocument> finalPrograms = new HashSet<>();
        List<ProgramDocument> newProgramsToSave = new ArrayList<>();

        for (ProgramDocument program : universityDocument.getPrograms()) {
            if (nameToExistingProgramMap.containsKey(program.getName())) {
                finalPrograms.add(nameToExistingProgramMap.get(program.getName()));
            } else {
                // Collect new programs to save them in batch later
                newProgramsToSave.add(program);
            }
        }

        // Save all new programs in a batch and add them to the final programs set
        List<ProgramDocument> savedNewPrograms = programRepository.saveAll(newProgramsToSave);
        finalPrograms.addAll(savedNewPrograms);

        // Associate the final set of programs with the university and save
        universityDocument.setPrograms(finalPrograms);
        return universityRepository.save(universityDocument);
    }

    @Override
    public void getUniversitiesFromWikipedia() throws IOException {

        Set<UniversityDocument> universityDocuments = new HashSet<>();

        Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_universities_in_Canada").get();
        Elements tables = doc.select("table.wikitable");

        for (Element table : tables) {
            Elements rows = table.select("tr");

            for (int i = 1; i < rows.size(); i++) {
                Element row = rows.get(i);
                Elements cols = row.select("td");

                if (!cols.isEmpty()) {
                    String universityName = cols.get(0).text().trim();
                    String location = cols.size() > 1 ? cols.get(1).text().trim() : "Location not found";

                    universityDocuments.add(UniversityDocument.builder()
                            .name(universityName)
                            .location(location)
                            .url("http://test.edu")
                            .build());
                }
            }
        }
        universityRepository.saveAll(universityDocuments);
    }

    @Override
    public List<UniversityDocument> getAllUniversities() {
        return universityRepository.findAll();
    }


}

