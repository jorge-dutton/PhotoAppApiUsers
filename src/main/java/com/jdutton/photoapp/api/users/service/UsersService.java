package com.jdutton.photoapp.api.users.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jdutton.photoapp.api.users.shared.UserDto;

public interface UsersService extends UserDetailsService {
    UserDto getUserByUserId(final String userId);
    UserDto createUser(final UserDto userDetails);
}
