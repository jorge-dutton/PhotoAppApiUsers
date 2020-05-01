package com.jdutton.photoapp.api.users.data;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.jdutton.photoapp.api.users.ui.model.AlbumResponseModel;

@FeignClient(name = "albums-ws")
public interface AlbumServiceClient {

	@GetMapping("/users/{id}/albumss")
	public List<AlbumResponseModel> getAlbums(@PathVariable final String id);

}