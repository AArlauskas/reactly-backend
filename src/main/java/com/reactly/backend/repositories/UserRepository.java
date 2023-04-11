package com.reactly.backend.repositories;

import com.reactly.backend.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByEmailAndPassword(String email, String password);
}
