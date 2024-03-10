package com.canuniv.service;

import java.io.IOException;
import java.util.Map;

public interface ExtractGraduatePrograms {

    String getProgramListUrl(String universityName) ;
    Map<String, String> extractProgramNameAndUrl(String programListUrl) throws IOException;
}
