package com.dksd.service.user.service;

import java.util.List;

public interface UserService<T, ID> {

    T add(T entry);

    T update(T entry);

    void delete(ID id);

    T findOne(ID id);

    List<T> findAll();

    boolean exists(ID id);

    boolean findByEmailExists(String email);

}
