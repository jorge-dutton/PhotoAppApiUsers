package com.jdutton.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jdutton.photoapp.api.users.data.AlbumServiceClient;
import com.jdutton.photoapp.api.users.data.UserEntity;
import com.jdutton.photoapp.api.users.data.UsersRepository;
import com.jdutton.photoapp.api.users.shared.UserDto;
import com.jdutton.photoapp.api.users.ui.model.AlbumResponseModel;

@Service
public class UsersServiceImpl implements UsersService {

	private final UsersRepository usersRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	// private final RestTemplate restTemplate; // For using with RestTemplate
	private final AlbumServiceClient albumServiceClient; // To be used with
															// Feign HTTP
															// service client
	private final Environment env;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public UsersServiceImpl(final UsersRepository usersRepository,
			final BCryptPasswordEncoder bCryptPasswordEncoder,
			// final RestTemplate restTemplate,
			final AlbumServiceClient albumServiceClient,
			final Environment env) {
		super();
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		// this.restTemplate = restTemplate;
		this.albumServiceClient = albumServiceClient;
		this.env = env;
	}

	@Override
	public UserDto getUserDetailsByEmail(final String email) {
		UserEntity userEntity = usersRepository.findByEmail(email);
		if (userEntity == null) {
			throw new UsernameNotFoundException(email);
		}
		return new ModelMapper().map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUserDetailsByUserId(final String userId) {
		UserEntity userEntity = usersRepository.findByUserId(userId);
		if (userEntity == null) {
			throw new UsernameNotFoundException(userId);
		}

		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

		final String albumsUrl = String.format(env.getProperty("albums.url"),
				userId);

		// To be used with Feign HTTP Service Client
		// ResponseEntity<List<AlbumResponseModel>> albumsListResponse =
		// restTemplate
		// .exchange(albumsUrl, HttpMethod.GET, null,
		// new ParameterizedTypeReference<List<AlbumResponseModel>>() {
		// });
		// List<AlbumResponseModel> albumsList = albumsListResponse.getBody();

		logger.info("Going to invoke Albums microservice");

		List<AlbumResponseModel> albumsList = albumServiceClient
				.getAlbums(userId);
		logger.info("Albums microservice response received");
		userDto.setAlbums(albumsList);

		return userDto;
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
