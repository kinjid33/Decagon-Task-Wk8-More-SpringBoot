package com.taskmanager.services.Implementation;

import com.taskmanager.dto.ToDoDTO;
import com.taskmanager.models.ToDo;
import com.taskmanager.models.User;
import com.taskmanager.repositories.ToDoRepo;
import com.taskmanager.repositories.UserRepo;
import com.taskmanager.services.ToDoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToDoServiceImpl implements ToDoService {
    private ToDoRepo toDoRepo;
    private UserRepo userRepo;

    public ToDo newTask(ToDoDTO toDoDTO, Long userId){
        ToDo toDo = new ToDo(toDoDTO);
        toDo.setStatus("Pending");
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm");
        String formattedDateTime = localDateTime.format(formatter);
        toDo.setCreatedDate(formattedDateTime);

        User user = userRepo.findById(userId).get();
        toDo.setUser(user);

        return toDoRepo.save(toDo);
    }

    public List<ToDo> findAllByUserId(Long id){
        return toDoRepo.findByUserId(id);
    }

    @Override
    public ToDo findToDo(Long id) {
        return toDoRepo.findById(id).orElseThrow(
                () -> new NullPointerException
                        ("Task with id " + id + " does not exist"));
    }

    @Override
    public void deleteById(Long id) {
        toDoRepo.deleteToDoByTaskId(id);
    }

    @Override
    public void completeTask(Long id) {
        ToDo toDo = findToDo(id);
        toDo.setStatus("Completed");

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm");
        String formattedDateTime = localDateTime.format(formatter);
        toDo.setCompletedDate(formattedDateTime);
        toDoRepo.save(toDo);
    }

    @Override
    public List<ToDo> findAll() {
        return toDoRepo.findAll();
    }

    @Autowired
    public ToDoServiceImpl(ToDoRepo toDoRepo, UserRepo userRepo) {
        this.toDoRepo = toDoRepo;
        this.userRepo = userRepo;
    }
}