package com.example.first.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/project")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUser();
    }
    @PostMapping
    public void registerUser(@RequestBody User user){
        userService.addUser(user);
    }

    @PostMapping(path="login")
    public User logUser(@RequestBody User user){
        return userService.Login(user);
    }

    @PutMapping(path="{Id}")
    public User updateUser(
            @PathVariable("Id") Long studentId,
            @RequestParam(required=false) String password,
            @RequestParam(required = false) String email){
        return userService.updateUser(studentId, password, email);
    }
}
