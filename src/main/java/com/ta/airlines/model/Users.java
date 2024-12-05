package com.ta.airlines.model;

import java.sql.Timestamp;

public class Users {

    private int id; 
    private String email; 
    private String password; 
    private String role;
    private Timestamp createAt; 

    public Users() {
    }

    public Users(int id, String email, String password, String role, Timestamp createAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", createAt=" + createAt +
                '}';
    }

	
}
