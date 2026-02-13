package com.AuthService.AuthService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.AuthService.AuthService.Entities.User;


import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String name);
}
