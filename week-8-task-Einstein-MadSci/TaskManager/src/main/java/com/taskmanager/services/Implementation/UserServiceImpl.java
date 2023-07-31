package com.taskmanager.services.Implementation;

import com.taskmanager.dto.UserDTO;
import com.taskmanager.models.User;
import com.taskmanager.repositories.UserRepo;
import com.taskmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
//    Repository object to allow communication with DB
    private UserRepo userRepo;

//    Constructor to inject UserRepo
    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

//    Implementation of addUser method
    @Override
    public User addUser(UserDTO userDTO) {
        User user = new User(userDTO);
//    UserRepo extends jpa repository which provides us with a save method
//    for adding to DB without stress and returns an instance of the object
//    passed to it
        return userRepo.save(user);
    }

//    Implementation of the findUser method
    @Override
    public User findUser(UserDTO userDTO) {
//    Boolean to check if user exists in the DB by calling the findByUserName method
//    which takes a String (in this case, the userName contained in the DTO) and the
//    isPresent() method which returns true or false
        boolean userBool = userRepo.findByUserName(userDTO.getUserName()).isPresent();
//        User user = userRepo.findByUserName(userDTO.getUserName()).get();

//     if userBool above is true, we return the User found by calling the findByUserName method
//      dot get
        if(userBool){
            return userRepo.findByUserName(userDTO.getUserName()).get();
        }
        return null;
    }



}