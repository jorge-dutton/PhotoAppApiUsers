package com.jdutton.photoapp.api.users.service;

import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import com.jdutton.photoapp.api.users.data.UserEntity;
import com.jdutton.photoapp.api.users.data.UsersRepository;
import com.jdutton.photoapp.api.users.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    
    public UsersServiceImpl(UsersRepository usersRepository) {
	super();
	this.usersRepository = usersRepository;
    }

    @Override
    public UserDto createUser(UserDto userDetails) {
	
	userDetails.setUserId(UUID.randomUUID().toString());
	//Maps DTO to Entity. Attributes for both DTO and Entity objects must
	//have the same name, for this mapping to work
	ModelMapper modelMapper = new ModelMapper();
	
	//Model maper has some problems matching between similar attribute names
	//we define MatchingStrategy to STRICT for this reason
	modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	
	UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
	userEntity.setEncryptedPassword("to_be_done");
	
	usersRepository.save(userEntity);
	
	UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
	
	return returnValue;
    }

}
