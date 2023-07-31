package com.taskmanager.dto;

import com.taskmanager.models.ToDo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToDoDTO {
    private Long taskId;
    private String title;
    private String description;
    private String status;
//    private LocalDateTime createdDate;
    private String createdDate;
//    private LocalDateTime updatedDate;
    private String updatedDate;
//    private LocalDateTime completedDate;
    private String completedDate;
//    private ToDo todo;

    public ToDoDTO(ToDo toDo) {
       ToDoDTO.builder()
                .description(toDo.getDescription())
                .taskId(toDo.getTaskId())
                .title(toDo.getTitle())
                .completedDate(toDo.getCompletedDate())
                .createdDate(toDo.getCreatedDate())
                .updatedDate(toDo.getUpdatedDate())
                .build();

    }
}
