package com.airline.booking.authentication.modules.user.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.booking.authentication.modules.user.entities.UserEntity;
import com.airline.booking.authentication.modules.user.useCases.CreateUserUseCase;
import com.airline.booking.authentication.modules.user.useCases.ProfileUserUseCase;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

   @Autowired
   private CreateUserUseCase createCandidateUseCase;

   @Autowired
   private ProfileUserUseCase profileUserUseCase;

   @PostMapping("/")
   public ResponseEntity<Object> create(@Valid @RequestBody UserEntity userEntity) {

      try {
         var result = this.createCandidateUseCase.execute(userEntity);

         return ResponseEntity.ok().body(result);

      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }
   }

   @GetMapping("/")
   @PreAuthorize("hasRole('USER')")
   public ResponseEntity<Object> get(HttpServletRequest request) {

      var idUser = request.getAttribute("user_id");

      try {
         var profile = this.profileUserUseCase.execute(UUID.fromString(idUser.toString()));
         return ResponseEntity.ok().body(profile);

      } catch (Exception e) {
         return ResponseEntity.badRequest().body(e.getMessage());
      }
   }
}
