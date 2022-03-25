package com.nnk.springboot.services;

import com.nnk.springboot.domain.DTO.UserDTO;
import com.nnk.springboot.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User createUser(UserDTO userDTO);

    Optional<User> findByIdAndUpdate(Integer id, UserDTO userDTO);

    Optional<User> findById(Integer id);

    void delete(User user);

}
