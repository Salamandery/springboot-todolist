package br.com.atomiccodes.todolist.tasks;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.atomiccodes.todolist.utils.Util;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;
    
    @PostMapping("/")
    public ResponseEntity<TaskModel> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        taskModel.setUserId((UUID)request.getAttribute("userId"));

        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            System.out.println("Data de inicio e Termino não pode ser antes da data atual");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(taskModel);
        }

        if (taskModel.getEndAt().isBefore(taskModel.getStartAt())) {
            System.out.println("Data de Termino não pode ser antes da data de inicio");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(taskModel);
        }

        TaskModel taskCreated = this.taskRepository.save(taskModel);
        return ResponseEntity.ok().body(taskCreated);
    }

    @GetMapping("/")
    public ResponseEntity<List<TaskModel>> listAll(HttpServletRequest request) {
        List<TaskModel> tasks = this.taskRepository.findByUserId((UUID)request.getAttribute("userId"));

        return ResponseEntity.ok().body(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskModel> update(@PathVariable UUID id, @RequestBody TaskModel taskModel, HttpServletRequest request) {
        Optional<TaskModel> taskExists = this.taskRepository.findById(id);
        if (taskExists == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(taskModel);
        }

        Util.copyNonNullProperties(taskModel, taskExists);
        TaskModel taskUpdated = this.taskRepository.save(taskModel);
        
        return ResponseEntity.ok().body(taskUpdated);
    }
}
