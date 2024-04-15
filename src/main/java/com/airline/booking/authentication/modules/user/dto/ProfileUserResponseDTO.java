package com.airline.booking.authentication.modules.user.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUserResponseDTO {

   private UUID id;
   private String name;
   private String userName;
   private String email;
   private String description;
}
