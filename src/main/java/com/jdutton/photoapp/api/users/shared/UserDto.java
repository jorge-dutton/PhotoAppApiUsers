package com.jdutton.photoapp.api.users.shared;

import java.io.Serializable;

public class UserDto implements Serializable {
    
    private static final long serialVersionUID = 9029428175163892298L;
    
    private String userId; //Is insecure to use autoincremente ID so we use alphanumeric
    private String firstName;
    private String lastName;
    private String passsword;
    private String email;
    private String encryptedPassword;
    
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
    public String getEncryptedPassword() {
        return encryptedPassword;
    }
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }
    
    
}
