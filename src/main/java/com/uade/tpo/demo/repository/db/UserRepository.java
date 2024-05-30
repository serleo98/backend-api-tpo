package com.uade.tpo.demo.repository.db;

import com.uade.tpo.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String username);
    Optional<User> findByIdentityId(String identityId);
}
