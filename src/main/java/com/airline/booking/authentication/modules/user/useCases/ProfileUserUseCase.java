package com.airline.booking.authentication.modules.user.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.airline.booking.authentication.modules.user.dto.ProfileUserResponseDTO;
import com.airline.booking.authentication.modules.user.repositories.UserRepository;

@Service
public class ProfileUserUseCase {

   @Autowired
   private UserRepository userRepository;
 
   public ProfileUserResponseDTO execute(UUID idUser) {
     var user = this.userRepository.findById(idUser)
         .orElseThrow(() -> {
           throw new UsernameNotFoundException("User not found");
         });
 
     var userDTO = ProfileUserResponseDTO.builder()
         .id(user.getId())
         .userName(user.getUserName())
         .name(user.getName())
         .email(user.getEmail())
         .build();
 
     return userDTO;
   }
}
