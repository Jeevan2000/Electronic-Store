package com.example.electronic.store.services;

import com.example.electronic.store.dtos.UserDto;
import com.example.electronic.store.entities.User;

import java.util.List;

public interface UserService {

    // create user

    UserDto createUser(UserDto dto);

    // update user

    UserDto updateUser(UserDto userdto, String userId);

    // delete user

    void deleteUser(String userID);

    // getAllUser
    List<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortOrder);

    //getUser by ID

    UserDto getUserById(String userId);

    // get user by email
    UserDto getUserByEmail(String email);

    // search user
    List<UserDto> getUser(String keyword);
}
