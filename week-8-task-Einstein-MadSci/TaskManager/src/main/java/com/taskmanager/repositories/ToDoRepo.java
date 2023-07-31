package com.taskmanager.repositories;

import com.taskmanager.models.ToDo;
import jakarta.transaction.Transactional;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoRepo extends JpaRepository<ToDo, Long> {
    List<ToDo> findByUserId(Long userId);
    @Transactional
    @Modifying
    @Query(
            value = "delete from ToDo t where t.taskId = :taskId"
    )
    void deleteToDoByTaskId(Long taskId);
}
