package com.jdutton.photoapp.api.users.data;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUserId(final String userId);
    UserEntity findByEmail(final String email);
}
