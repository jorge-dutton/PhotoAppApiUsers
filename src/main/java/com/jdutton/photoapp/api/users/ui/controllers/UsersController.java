package com.jdutton.photoapp.api.users.ui.controllers;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
