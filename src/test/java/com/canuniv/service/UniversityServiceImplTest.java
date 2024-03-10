package com.canuniv.service;

import com.canuniv.document.ProgramDocument;
import com.canuniv.document.UniversityDocument;
import com.canuniv.repository.ProgramRepository;
import com.canuniv.repository.UniversityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversityServiceImplTest {

    @Mock
    private UniversityRepository universityRepository;

    @Mock
    private ProgramRepository programRepository;

    @InjectMocks
    private UniversityServiceImpl universityService;

    private UniversityDocument mockUniversity;
    private ProgramDocument existingProgram;
    private ProgramDocument newProgram;

    @BeforeEach
    void setUp() {

        mockUniversity = new UniversityDocument("1", "Test University", "Test Location", "http://test.edu", new HashSet<>());

        existingProgram = ProgramDocument.builder()
                .id("1")
                .name("Existing Program")
                .duration("1 Year")
                .url("http://existingprogram.com")
                .build();


        newProgram = ProgramDocument.builder()
                .url("http://newprogram.com")
                .name("New Program")
                .duration("2 Years")
                .build();

        mockUniversity.getPrograms().add(existingProgram);
        mockUniversity.getPrograms().add(newProgram);
    }

    @Test
    void saveUniversityWithExistingAndNewPrograms() {
        // Setup mock behavior
        when(programRepository.findByName(existingProgram.getName())).thenReturn(Optional.of(existingProgram));
        when(programRepository.findByName(newProgram.getName())).thenReturn(Optional.empty());
        when(programRepository.save(any(ProgramDocument.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(universityRepository.save(any(UniversityDocument.class))).thenReturn(mockUniversity);

        // Execute the service method
        UniversityDocument savedUniversity = universityService.saveUniversity(mockUniversity);

        // Assertions
        assertNotNull(savedUniversity);
        assertEquals(2, savedUniversity.getPrograms().size());
        verify(programRepository, times(1)).findByName(existingProgram.getName());
        verify(programRepository, times(1)).findByName(newProgram.getName());
        verify(programRepository, times(1)).save(newProgram); // Only new program should be saved
        verify(universityRepository, times(1)).save(mockUniversity); // Verify university is saved with correct programs
    }
}
