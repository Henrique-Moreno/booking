package com.airline.booking.authentication.modules.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.booking.authentication.modules.user.dto.AuthUserRequestDTO;
import com.airline.booking.authentication.modules.user.useCases.AuthUserUseCase;

@RestController
@RequestMapping("/user")
public class AuthUserController {

   @Autowired
   private AuthUserUseCase authUserUseCase;

   @PostMapping("/auth")
   public ResponseEntity<Object> auth(@RequestBody AuthUserRequestDTO authUserRequestDTO) {

      try {
         var token = this.authUserUseCase.execute(authUserRequestDTO);
         return ResponseEntity.ok().body(token);

      } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
      }
   }
}
