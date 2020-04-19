package com.jdutton.photoapp.api.users.ui.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class CreateUserRequestModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ui;
    
    @NotNull(message="First name is mandatory")
    @Size(min=2, message="First name must be longer than one char")
    private String firstName;
    
    @NotNull(message="Last name is mandatory")
    @Size(min=2, message="Last name must be longer than one char")
    private String lastName;
    
    @NotNull(message="Password is mandatory")
    @Size(min=8, max=16, message="Password must be beteewen 9 and 16 chars")
    private String passsword;
    
    @NotNull(message="Email is mandatory")
    @Email
    private String email;

    public CreateUserRequestModel() {

    }

    public CreateUserRequestModel(String firstName, String lastName, String passsword, String email) {
	super();
	this.firstName = firstName;
	this.lastName = lastName;
	this.passsword = passsword;
	this.email = email;
    }

    public Long getUi() {
	return ui;
    }

    public void setUi(Long ui) {
	this.ui = ui;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getPasssword() {
	return passsword;
    }

    public void setPasssword(String passsword) {
	this.passsword = passsword;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

}
