package com.ndbarbearia.barberservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

import com.ndbarbearia.barberservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByProfile(String profile);

    Optional<User> findByEmail(String email);

    Page<User> findAll(Pageable pageable);
}
