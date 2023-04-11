package com.reactly.backend.repositories;

import com.reactly.backend.entities.Website;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteRepository extends CrudRepository<Website, String> {
    Iterable<Website> findAllByUserId(String userId);
}
