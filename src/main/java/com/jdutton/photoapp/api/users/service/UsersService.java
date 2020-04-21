package com.jdutton.photoapp.api.users.service;

import com.jdutton.photoapp.api.users.shared.UserDto;

public interface UsersService {
    UserDto getUserByUserId(final String userId);
    UserDto createUser(final UserDto userDetails);
}
