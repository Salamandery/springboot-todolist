package br.com.atomiccodes.todolist.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;
    
    @PostMapping("/")
    public ResponseEntity<TaskModel> create(@RequestBody TaskModel taskModel) {
        TaskModel taskCreated = this.taskRepository.save(taskModel);
        return ResponseEntity.ok().body(taskCreated);
    }
}