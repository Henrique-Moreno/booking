package com.airline.booking.authentication.modules.user.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.airline.booking.authentication.modules.user.dto.AuthUserRequestDTO;
import com.airline.booking.authentication.modules.user.dto.AuthUserResponseDTO;
import com.airline.booking.authentication.modules.user.repositories.UserRepository;

@Service
public class AuthUserUseCase {
   @Value("${security.token.secret.user}")
   private String secretKey;

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

   public AuthUserResponseDTO execute(AuthUserRequestDTO authUserRequestDTO)
         throws AuthenticationException {
      var cantidate = this.userRepository.findByUserName(authUserRequestDTO.userName())
            .orElseThrow(() -> {
               throw new UsernameNotFoundException("Username/password incorrect");
            });

      var passwordMatches = this.passwordEncoder
            .matches(authUserRequestDTO.password(), cantidate.getPassword());

      if (!passwordMatches) {
         throw new AuthenticationException();
      }

      Algorithm algorithm = Algorithm.HMAC256(secretKey);
      var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
      var token = JWT.create()
            .withIssuer("javagas")
            .withSubject(cantidate.getId().toString())
            .withClaim("roles", Arrays.asList("CANDIDATE"))
            .withExpiresAt(expiresIn)
            .sign(algorithm);

      var authUserResponse = AuthUserResponseDTO.builder()
            .access_token(token)
            .expires_in(expiresIn.toEpochMilli())
            .build();

      return authUserResponse;
   }
}