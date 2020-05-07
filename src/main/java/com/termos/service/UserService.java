package com.termos.service;

import com.termos.dto.UserDTO;
import com.termos.model.User;
import com.termos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class UserService implements AbstractService<UserDTO> {
    private UserRepository repository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.repository = userRepository;
    }


    @Override
    public UserDTO persistNew(UserDTO user){
        User userDb = repository.dtoToModel(user);
        return repository.modelToDto(repository.persist(userDb));
    }

    @Override
    public String remove(String id) {
        return repository.remove(id);
    }

    @Override
    public UserDTO getById(String id) {
        return repository.modelToDto(repository.getById(id));
    }

    public UserDTO getByEmail(String email) {
        return repository.modelToDto(repository.getByEmail(email));
    }

    @Override
    public Stream<UserDTO> findAll() {
        return repository.findAll().stream().map(user -> repository.modelToDto(user));
    }

    @Override
    public int getRecordCount() {
        return repository.count();
    }
}