package com.jdutton.photoapp.api.users.service;

import com.jdutton.photoapp.api.users.shared.UserDto;

public interface UsersService {
    UserDto createUser(UserDto userDetails);
}
