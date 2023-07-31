package com.taskmanager.models;

import com.taskmanager.dto.ToDoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "todo_list")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "task_title")
    private String title;

    @Column(name = "task_description")
    private String description;

    @Column(name = "task_status")
    private String status;

    @Column(name = "date_created")
//    @CreationTimestamp
//    private LocalDateTime createdDate;
    private String createdDate;

//    @UpdateTimestamp
    @Column(name = "date_updated")
//    private LocalDateTime updatedDate;
    private String updatedDate;

    @Column(name = "date_completed")
//    private LocalDateTime completedDate;
    private String completedDate;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(
                    name = "todo_user_id_fk"
            )
    )
    private User user;

    @Column(name = "user_id", updatable = false, insertable = false)
    private Long userId;

    public ToDo (ToDoDTO toDoDTO){
        this.title = toDoDTO.getTitle();
        this.description = toDoDTO.getDescription();
        this.status = toDoDTO.getStatus();
        this.createdDate = toDoDTO.getCreatedDate();
        this.updatedDate = toDoDTO.getUpdatedDate();
        this.completedDate = toDoDTO.getCompletedDate();
    }
}