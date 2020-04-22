package com.jdutton.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jdutton.photoapp.api.users.data.UserEntity;
import com.jdutton.photoapp.api.users.data.UsersRepository;
import com.jdutton.photoapp.api.users.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {

	private final UsersRepository usersRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UsersServiceImpl(final UsersRepository usersRepository,
			final BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDto getUserByUserId(final String userId) {
		UserEntity userEntity = usersRepository.findByUserId(userId);
		final ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(userEntity, UserDto.class);
	}

	@Override
	public UserDto createUser(UserDto userDetails) {

		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(
				bCryptPasswordEncoder.encode(userDetails.getPassword()));
		// Maps DTO to Entity. Attributes for both DTO and Entity objects must
		// have the same name, for this mapping to work
		ModelMapper modelMapper = new ModelMapper();

		// Model maper has some problems matching between similar attribute
		// names
		// we define MatchingStrategy to STRICT for this reason
		modelMapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

		usersRepository.save(userEntity);

		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		UserEntity userEntity = usersRepository.findByEmail(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException(username);
		}

		return new User(userEntity.getEmail(),
				userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
	}

}
