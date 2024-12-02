package com.tecup.backend.payload.response;

import java.util.List;

public class UserInfoResponse {
	private Long id;
	private String username;
	private String email;
	private String career;
	private String departmentCareer;
	List<String> inscriptions;
	List<String> roles;


	public UserInfoResponse(Long id, String username, String email,String career, String departmentCareer,List<String> inscriptions,List<String> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.career = career;
		this.departmentCareer = departmentCareer;
		this.inscriptions = inscriptions;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getCareer() {return career;}

	public void setCareer(String career) {this.career = career;}

	public String getDepartmentCareer() {return departmentCareer;}

	public void setDepartmentCareer(String departmentCareer) {this.departmentCareer = departmentCareer;}
}
