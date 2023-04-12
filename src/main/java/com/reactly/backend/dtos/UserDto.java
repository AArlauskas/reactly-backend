package com.reactly.backend.dtos;

public class UserDto {
    public String id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public UserDto() {
    }

    public UserDto(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
