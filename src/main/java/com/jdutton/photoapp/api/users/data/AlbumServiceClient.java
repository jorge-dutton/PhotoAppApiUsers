package com.jdutton.photoapp.api.users.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jdutton.photoapp.api.users.ui.model.AlbumResponseModel;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

@FeignClient(name = "albums-ws", fallbackFactory = AlbumsFallbackFactory.class)
public interface AlbumServiceClient {

	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable final String id);

}

@Component
class AlbumsFallbackFactory implements FallbackFactory<AlbumServiceClient> {

	@Override
	public AlbumServiceClient create(Throwable cause) {
		// TODO Auto-generated method stub
		return new AlbumsServiceClientFallback(cause);
	}

}

class AlbumsServiceClientFallback implements AlbumServiceClient {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Throwable cause;

	public AlbumsServiceClientFallback(Throwable cause) {
		super();
		this.cause = cause;
	}

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		if (cause instanceof FeignException
				&& ((FeignException) cause).status() == 404) {
			logger.error(
					"404 error when trying to get albums for userId " + id);
		} else {
			logger.error("Unknown error when trying to get albums!");
		}
		return new ArrayList<>();
	}

}