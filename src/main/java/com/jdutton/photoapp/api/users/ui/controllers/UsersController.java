package com.jdutton.photoapp.api.users.ui.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdutton.photoapp.api.users.service.UsersService;
import com.jdutton.photoapp.api.users.shared.UserDto;
import com.jdutton.photoapp.api.users.ui.model.CreateUserRequestModel;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final Environment env;
    private final UsersService usersService;

    public UsersController(Environment env, UsersService usersService) {
	super();

	this.env = env;
	this.usersService = usersService;
    }

    @GetMapping("/status/check")
    public String status() {
	return "Working! port: " + env.getProperty("local.server.port");
    }

    @PostMapping
    public String createUser(@Valid @RequestBody final CreateUserRequestModel userDetails) {
	ModelMapper modelMapper = new ModelMapper();
	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	
	UserDto userDto = modelMapper.map(userDetails, UserDto.class);
	usersService.createUser(userDto);
	
	return "Create user called!";
    }
}
