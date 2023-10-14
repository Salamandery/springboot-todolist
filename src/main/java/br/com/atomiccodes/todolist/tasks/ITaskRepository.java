package br.com.atomiccodes.todolist.tasks;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    public List<TaskModel> findByUserId(UUID userId);
}
