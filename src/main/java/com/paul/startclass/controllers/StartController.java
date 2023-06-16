package com.paul.startclass.controllers;

import com.paul.startclass.models.User;
import com.paul.startclass.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class StartController {

    @Autowired
    ApplicationContext context;

    @GetMapping("/")
    public String start(){
        return "test";
    }

    @Autowired
    UserRepository userRepository;



    @RequestMapping(value = "/user/{Id}", method = RequestMethod.GET)
    public ResponseEntity<User> getFile(@PathVariable Long id)  {
        return ResponseEntity.ok(userRepository.findById(id).orElse(null));
    }


}
