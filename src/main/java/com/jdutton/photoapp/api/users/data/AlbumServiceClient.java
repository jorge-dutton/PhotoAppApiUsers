package com.jdutton.photoapp.api.users.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jdutton.photoapp.api.users.ui.model.AlbumResponseModel;

@FeignClient(name = "albums-ws", fallback = AlbumsFallback.class)
public interface AlbumServiceClient {

	@GetMapping("/users/{id}/albums")
	public List<AlbumResponseModel> getAlbums(@PathVariable final String id);

}

@Component
class AlbumsFallback implements AlbumServiceClient {

	@Override
	public List<AlbumResponseModel> getAlbums(String id) {
		return new ArrayList<>();
	}

}