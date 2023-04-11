package com.reactly.backend.services;

import com.reactly.backend.dtos.UserDto;
import com.reactly.backend.entities.User;
import com.reactly.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserExist(String userId) {
        return userRepository.existsById(userId);
    }

    //get all users
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    //get user by id
    public User getUserById(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    //add user
    public User addUser(User user) {
        return userRepository.save(user);
    }

    //update user
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    //delete user
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    //authenticate user
    public User authenticateUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    //map to dto
    public UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.id = user.getId();
        userDto.email = user.getEmail();
        userDto.firstName = user.getFirstName();
        userDto.lastName = user.getLastName();
        return userDto;
    }

    //map to dtos
    public List<UserDto> mapToUserDtos(Iterable<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(mapToUserDto(user));
        }
        return userDtos;
    }

}
