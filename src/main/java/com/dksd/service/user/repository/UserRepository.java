package com.dksd.service.user.repository;

import com.dksd.service.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    boolean findByEmailExists(String email);

}
