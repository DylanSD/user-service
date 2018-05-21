package com.dksd.service.user.repository;

import com.dksd.service.user.model.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends MongoRepository<Sequence, String> {

}
