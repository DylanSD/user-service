package com.dksd.service.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Document(collection="Sequence")
public class Sequence {

    @Id
    private String name;
    private String project;
    private int nextSequence; //next available sequence

}
