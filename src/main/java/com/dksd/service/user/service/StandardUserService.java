package com.dksd.service.user.service;

import com.dksd.service.user.model.User;
import com.dksd.service.user.repository.SequenceRepository;
import com.dksd.service.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StandardUserService implements UserService<User, String> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    @Override
    public User add(User entry) {
        return userRepository.save(entry);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(String id) {
        userRepository.delete(id);
    }

    @Override
    public User findOne(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> findAll() {
        List<User> entries = new ArrayList<>();
        Page<User> pageResult = userRepository.findAll(new PageRequest(0, 100));
        entries.addAll(pageResult.getContent());
        for (int i = 1;i < pageResult.getTotalPages(); i++) {
            entries.addAll(userRepository.findAll(new PageRequest(i, 100)).getContent());
        }
        return entries;
    }

    @Override
    public boolean exists(String id) {
        return userRepository.exists(id);
    }

    @Override
    public boolean findByEmailExists(String email) {
        return userRepository.findByEmailExists(email);
    }
}
