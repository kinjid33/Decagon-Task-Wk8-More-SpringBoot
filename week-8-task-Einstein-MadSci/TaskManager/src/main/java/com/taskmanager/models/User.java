package com.taskmanager.models;

import com.taskmanager.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    private String salt;

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            mappedBy = "user",
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<ToDo> toDo = new ArrayList<>();


//  User constructor that accepts DTO as argument to set
//  dto fields (full name and username) so that user
//  doesn't interact directly
//  with User entity
    public User(UserDTO userDTO){
        this.fullName = userDTO.getFullName();
        this.userName = userDTO.getUserName();
//        this.salt = BCrypt.gensalt();
//        this.password = BCrypt.hashpw(userDTO.getPassword(), salt);
//    }
//
//    public boolean checkPassword(String password, String checkPwSalt){
//        return BCrypt.checkpw(password, checkPwSalt);
    }
}
