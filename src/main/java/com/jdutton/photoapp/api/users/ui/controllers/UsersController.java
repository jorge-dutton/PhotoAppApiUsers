package com.jdutton.photoapp.api.users.ui.controllers;

import javax.validation.Valid;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdutton.photoapp.api.users.ui.model.CreateUserRequestModel;

@RestController
@RequestMapping("/users")
public class UsersController {
    
    
    private final Environment env;
        
    
    public UsersController(Environment env) {
	super();
	this.env = env;
    }


    @GetMapping("/status/check")
    public String status() {
	return "Working! port: " + env.getProperty("local.server.port");
    }
    
    @PostMapping
    public String createUser(@Valid @RequestBody final CreateUserRequestModel userDetails) {
	return "Create user method called!";
    }
}
