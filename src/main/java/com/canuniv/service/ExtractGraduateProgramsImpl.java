package com.canuniv.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExtractGraduateProgramsImpl implements ExtractGraduatePrograms{

    @Override
    public String getProgramListUrl(String universityName) {
        return null;
    }

    @Override
    public Map<String, String> extractProgramNameAndUrl(String programListUrl) throws IOException {

        // Connect to the URL and get the document
        Document doc = Jsoup.connect(programListUrl).get();

        // Select the elements that contain program names and URLs
        Elements programElements = doc.select("a[href*='/graduate-studies/']");

        //Iterate and extract information
        Map<String, String> programNameAndUrlMap = new HashMap<>();

        for (Element program : programElements) {
            String programName = program.text();
            String programUrl = program.absUrl("href");
            programNameAndUrlMap.put(programName, programUrl);
        }

        return programNameAndUrlMap;
    }
}
