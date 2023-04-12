package com.reactly.backend.services;

import com.reactly.backend.dtos.UserDto;
import com.reactly.backend.entities.User;
import com.reactly.backend.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

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

    //register
    public User register(UserDto dto) {
        User user = new User();
        user.setEmail(dto.email);
        user.setPassword(dto.password);
        user.setFirstName(dto.firstName);
        user.setLastName(dto.lastName);
        return addUser(user);
    }

    //get user by email
    public User getUserByUsernameAuth(String email) {
        return userRepository.findByEmail(email);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    public User getAuthUser(UserDetails authentication) {
        return userRepository.findByEmailAndPassword(authentication.getUsername(), authentication.getPassword());
    }
}
