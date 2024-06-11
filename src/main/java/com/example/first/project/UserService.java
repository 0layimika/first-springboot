package com.example.first.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @GetMapping
    public List<User> getUser(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        Optional<User> user_email = userRepository.findUserByEmail(user.getEmail());
        Optional<User> user_name = userRepository.findUserByUsername(user.getUsername());
        if(user_email.isPresent() || user_name.isPresent()){
            throw new IllegalStateException("Email/username already exists");
        }
        userRepository.save(user);
    }

    public User Login(User user){
        Optional<User> user_name = userRepository.findUserByUsername(user.getUsername());
        if(!user_name.isPresent()){
            throw new IllegalStateException("Invalid username");
        }
        if(!user.getPassword().equals(user_name.get().getPassword())){
            throw new IllegalStateException("Incorrect Password");
        }
        System.out.println("Logged in");
        return user_name.get();
    }

    public User updateUser(Long Id, String password, String email){
        User curr_user = userRepository.findById(Id).orElseThrow(()-> new IllegalStateException("User with such ID doesn't exist"));
        if(password != null && password.length()>0 && !Objects.equals(curr_user.getPassword(), password)){
            curr_user.setPassword(password);
        }
        if(email !=null && email.length() > 0 && !Objects.equals(curr_user.getEmail(), email)){
            Optional<User> optionalUser = userRepository.findUserByEmail(email);
            if(optionalUser.isPresent()){
                throw new IllegalStateException("email already exists");
            }
            curr_user.setEmail(email);
        }
        userRepository.save(curr_user);
        return curr_user;
    }
}
