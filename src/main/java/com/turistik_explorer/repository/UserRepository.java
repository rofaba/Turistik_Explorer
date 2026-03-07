package com.turistik_explorer.repository;

import com.turistik_explorer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}