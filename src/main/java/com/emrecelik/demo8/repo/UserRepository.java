package com.emrecelik.demo8.repo;

import com.emrecelik.demo8.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmail(String email);

    Boolean existsByEmail(String email);

}
