package com.organizer.domain.model.user;

import org.springframework.stereotype.Repository;

public interface UserRepository {
    User findByUsername(String username);

    User findByEmailAddress(String emailAddress);

    void save(User user);
}
