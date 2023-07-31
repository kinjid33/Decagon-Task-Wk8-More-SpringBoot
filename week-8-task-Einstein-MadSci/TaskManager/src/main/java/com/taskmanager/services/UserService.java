package com.taskmanager.services;

import com.taskmanager.dto.UserDTO;
import com.taskmanager.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
//    List<UserDTO> findAllUsers();


//    add user method
    User addUser(UserDTO userDTO);

//    find user method (for logging in)
    User findUser(UserDTO userDTO);
}
