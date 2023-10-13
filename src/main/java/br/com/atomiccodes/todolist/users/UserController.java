package br.com.atomiccodes.todolist.users;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    
    /**
     * @param userModel
     */
    @PostMapping("/")
    public String create(@RequestBody UserModel userModel) {
        return "ok";
    }
}
