package br.com.atomiccodes.todolist.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private IUserRepository userRepository;
    /**
     * @param userModel
     */
    @PostMapping("/")
    public ResponseEntity<UserModel> create(@RequestBody UserModel userModel) {
        UserModel userExists = this.userRepository.findByUsername(userModel.getUsername());

        if (userExists != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userExists);
        }

        String hash = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(hash);
        UserModel userCreated = this.userRepository.save(userModel);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userCreated);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserModel>> listAll() {
        List<UserModel> users = this.userRepository.findAll();
        return ResponseEntity.ok().body(users);
    }
}
