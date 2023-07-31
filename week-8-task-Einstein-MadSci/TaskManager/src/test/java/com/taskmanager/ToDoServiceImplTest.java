package com.taskmanager;

import com.taskmanager.dto.ToDoDTO;
import com.taskmanager.models.ToDo;
import com.taskmanager.models.User;
import com.taskmanager.repositories.ToDoRepo;
import com.taskmanager.repositories.UserRepo;
import com.taskmanager.services.ToDoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ToDoServiceImplTest {
    @Mock
    private ToDoRepo toDoRepo;
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private ToDoService toDoService;

    @Autowired
    public ToDoServiceImplTest(ToDoRepo toDoRepo, UserRepo userRepo, ToDoService toDoService) {
        this.toDoRepo = toDoRepo;
        this.userRepo = userRepo;
        this.toDoService = toDoService;
    }

    @Test
    public void testAddTask(){
        User user = new User();
        user.setUserName("olodo");
        user.setPassword("12345");
        user.setUserId(1L);
        ToDo toDo = new ToDo();
        toDo.setTitle("Beans");
        toDo.setDescription("Eat and fart");
        toDo.setTaskId(1L);
        toDo.setUser(user);
        ToDoDTO toDoDTO = new ToDoDTO();
        toDoDTO.setTitle(toDo.getTitle());
        toDoDTO.setDescription(toDo.getDescription());
        toDoDTO.setTaskId(toDo.getTaskId());

        Mockito.when(userRepo.findById(user.getUserId())).thenReturn(Optional.of(user));
        Mockito.when(toDoRepo.save(toDo)).thenReturn(toDo);
        Mockito.when(toDoService.newTask(toDoDTO, user.getUserId())).thenReturn(toDo);

        Assertions.assertEquals(toDo, toDoService.newTask(toDoDTO, user.getUserId()));
    }
}
