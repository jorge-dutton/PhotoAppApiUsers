package com.jdutton.photoapp.api.users.shared;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

	private final Environment env;

	public FeignErrorDecoder(final Environment env) {
		super();
		this.env = env;
	}

	@Override
	public Exception decode(String methodKey, Response response) {
		switch (response.status()) {
			case 400 :
				// new return BadRequestException();
				break;
			case 404 :
				if (methodKey.contains("getAlbums")) {
					return new ResponseStatusException(
							HttpStatus.valueOf(response.status()),
							env.getProperty(
									"albums.exceptions.albums-not-found"));
				}
				break;
			default :
				return new Exception(response.reason());
		}
		return null;
	}

}
