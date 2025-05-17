package com.alkhatib.dto;

public class RegisterRequest {
	private String name; // Display name
    private String username;
    private String password;
    private String role;
    // Getters and Setters
    
    
    
	public String getUsername() {
		return username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
    
}
