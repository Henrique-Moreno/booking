package com.airline.booking.authentication.modules.user.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.airline.booking.authentication.exceptions.UserFoundException;
import com.airline.booking.authentication.modules.user.entities.UserEntity;
import com.airline.booking.authentication.modules.user.repositories.UserRepository;

@Service
public class CreateUserUseCase {

   @Autowired
   private UserRepository candidateRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

   public UserEntity execute(UserEntity userEntity) {

      this.candidateRepository
            .findByUserNameOrEmail(userEntity.getUserName(), userEntity.getEmail())
            .ifPresent((user) -> {

               throw new UserFoundException();
            });

      var password = passwordEncoder.encode(userEntity.getPassword());
      userEntity.setPassword(password);

      return this.candidateRepository.save(userEntity);
   }
}
