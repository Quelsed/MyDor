package com.example.dormitory.Models;

public class User {

    private Role role;
    private String email;
    private int hashPassword;

    public User(){

    }

    public User(Role role, String email, int hashPassword) {
        this.role = role;
        this.email = email;
        this.hashPassword = hashPassword;
    }

    public Role getRole() {
        return role;
    }

    public int getHashPassword() {
        return hashPassword;
    }

    public String getEmail() {
        return email;
    }

    public enum Role {
        ADMIN,
        USER
    }
}
