package com.airline.booking.authentication.modules.user.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.airline.booking.authentication.modules.user.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

   Optional <UserEntity> findByUserNameOrEmail(String username, String email);

   Optional <UserEntity> findByUserName(String userName);
}
