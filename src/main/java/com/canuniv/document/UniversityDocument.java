package com.canuniv.document;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "university")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UniversityDocument {

    @MongoId
    private String id;

    private String name;
    private String location;
    private String url;

    @Builder.Default
    private Set<ProgramDocument> programs = new HashSet<>();

}
