package com.cts.transpogov.dtos.user;


import lombok.Data;

import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.enums.UserStatus;

@Data
public class UserResponse {
  private Long userId;
  private String name;
  private UserRole role;
  private String email;
  private String phone;
  private UserStatus status;
}