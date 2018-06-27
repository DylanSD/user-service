package com.dksd.service.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Document(collection="User")
public class User {

    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private Date created;
    private Date lastLogon;
    private String token;

    public static User EXAMPLE = new User("dylansd@gmail.com", "Dylan", "Scott-Dawkins", "password");

    public User(String email, String first, String last, String password) {
        this.email = email;
        this.firstName = first;
        this.lastName = last;
        this.created = new Date();
        this.lastLogon = new Date();
    }

}
