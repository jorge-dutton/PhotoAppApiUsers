package com.jdutton.photoapp.api.users.ui.controllers;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import javax.xml.bind.annotation.XmlRootElement;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdutton.photoapp.api.users.service.UsersService;
import com.jdutton.photoapp.api.users.shared.UserDto;
import com.jdutton.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.jdutton.photoapp.api.users.ui.model.CreateUserResponseModel;
import com.jdutton.photoapp.api.users.ui.model.UpdateUserResponseModel;
import com.jdutton.photoapp.api.users.ui.model.UserResponseModel;

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

    @GetMapping(value = "/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UserResponseModel> getUser(@PathVariable final String userId) {
	final UserDto user = usersService.getUserByUserId(userId);
	final ModelMapper modelMapper = new ModelMapper();
	return ResponseEntity.status(HttpStatus.FOUND).body(modelMapper.map(user, UserResponseModel.class));
    }

    @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
	    MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<CreateUserResponseModel> createUser(
	    @Valid @RequestBody final CreateUserRequestModel userDetails) {
	ModelMapper modelMapper = new ModelMapper();
	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

	UserDto userResponseDto = usersService.createUser(modelMapper.map(userDetails, UserDto.class));

	CreateUserResponseModel createUserResponseModel = modelMapper.map(userResponseDto,
		CreateUserResponseModel.class);

	return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseModel);
    }

    @PutMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
	    MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<UpdateUserResponseModel> updateUser(
	    @Valid @RequestBody final CreateUserRequestModel userDetails) {
	ModelMapper modelMapper = new ModelMapper();
	final UpdateUserResponseModel returnValue = modelMapper.map(userDetails, UpdateUserResponseModel.class);
	return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
