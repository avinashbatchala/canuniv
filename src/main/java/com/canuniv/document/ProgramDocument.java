package com.canuniv.document;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "program")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgramDocument {

    @MongoId
    private String id;

    private String name;
    private String duration;
    private String url;

}
