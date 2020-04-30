package com.jdutton.photoapp.api.users.ui.model;

public class AlbumResponseModel {
	private String ambumId;
	private String userId;
	private String name;
	private String description;
	public String getAmbumId() {
		return ambumId;
	}
	public void setAmbumId(String ambumId) {
		this.ambumId = ambumId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
