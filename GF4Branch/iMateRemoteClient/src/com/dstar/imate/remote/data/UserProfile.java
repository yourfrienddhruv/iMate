package com.dstar.imate.remote.data;

import java.util.Date;

import com.dstar.imate.data.IUserProfile;
import com.dstar.imate.web.json.base.gson.Json;

public class UserProfile extends AbstractViewObject implements IUserProfile<UserProfile>{
	private static final long serialVersionUID = 1L;

	private String username;

	private String firstName;

	private String lastName;

	private String password;

	private String status= "A";
	
	private Date birthDate;

	public UserProfile() {
		super();
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public Date getBirthDate() {
		return birthDate;
	}

	@Override
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String toJson(){
		return Json.to(this);
	}
	@Override
	public String toString() {
		return Json.to(this);
	}
}
