package com.dksd.service.user.controller;

import com.dksd.service.user.model.Password;
import com.dksd.service.user.model.User;
import com.dksd.service.user.service.DuplicateUserException;
import com.dksd.service.user.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController(value = "/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private Gson gson = new GsonBuilder().create();

    @Autowired
    private UserService<User, String> userService;

    @PostMapping(value = "/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        logger.info("Request for adding User {} received.", user);
        if (userService.findByEmailExists(user.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        user.setCreated(new Date());
        return new ResponseEntity<>(userService.add(user), HttpStatus.CREATED);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Request for getting all entries received.");
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        User userExisting = userService.findOne(id);
        logger.info("Finding User number {} and got existing User {} we want to update with {}", id, userExisting, user);
        if (!Password.checkPassword(user.getPassword(), userExisting.getPassword())) {
            logger.info("Updating user {} password!", user.getEmail());
            userExisting.setPassword(Password.hashPassword(user.getPassword()));
        }
        logger.info("Updated existing User to {}", userExisting);
        return new ResponseEntity<>(userService.update(userExisting), HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        logger.info("Request to delete User {} received.", id);
        userService.delete(id);
        User UserExisting = userService.findOne(id);
        if (UserExisting != null) {
            logger.error("Could not delete the User id: {} {}.", id, UserExisting);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        return Optional.ofNullable(userService.findOne(id))
                .map(User -> ResponseEntity.ok().body(User))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/info")
    public String getInfo() {
        StringBuilder sb = new StringBuilder("User Service: which can CRUD Jira like cards called entries. \n");
        sb.append("Example create User (Id will be assigned for you): POST http://localhost:8080/entries {\"parentId\":\"1\",\"title\":\"Treehouse\",\"description\":\"As a home owner I want a Treehouse in the garden that I can rent out on AirBnb\",\"estWorkRemaining\":\"3y\"} \n");
        sb.append("Get User: GET http://localhost:8080/entries/1 \n");
        sb.append("Get all entries: GET http://localhost:8080/entries/1 \n");
        sb.append("Update User: PUT http://localhost:8080/entries/1, body: {\n" +
                "    \"id\": \"1\",\n" +
                "    \"parentId\": null,\n" +
                "    \"title\": \"1023 Erica\",\n" +
                "    \"description\": \"Our house main Epic, This includes inside outside, everything related to the house\",\n" +
                "    \"estWorkRemaining\": \"50y\"\n" +
                "}\n");
        sb.append("Delete User: DELETE http://localhost:8080/entries/1 \n");
        return sb.toString();
    }

}
