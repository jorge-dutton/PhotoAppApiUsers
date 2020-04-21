package com.jdutton.photoapp.api.users.ui.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateUserResponseModel {
    
    private String userId; 
    private String firstName;
    private String lastName;
    private String email;

    public UpdateUserResponseModel() {

    }

    public UpdateUserResponseModel(String userId, String firstName, String lastName, String email) {
	super();
	this.userId = userId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
    }
    

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

}
