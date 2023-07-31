package com.taskmanager.services;

import com.taskmanager.dto.ToDoDTO;
import com.taskmanager.models.ToDo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ToDoService {
    ToDo newTask(ToDoDTO toDoDTO, Long id);
    List<ToDo> findAll();
    List<ToDo> findAllByUserId(Long id);
    ToDo findToDo(Long id);

    void deleteById(Long id);

    void completeTask(Long id);
}
