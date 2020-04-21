package com.jdutton.photoapp.api.users.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginRequestModel {

    @NotNull(message="Email is mandatory")
    @Email
    private String email;
    
    @NotNull(message="Password is mandatory")
    @Size(min=8, max=16, message="Password must be beteewen 9 and 16 chars")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
