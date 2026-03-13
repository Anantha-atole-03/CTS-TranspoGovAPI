package com.cts.transpogov.dtos.user;



import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.enums.UserStatus;

import lombok.Data;

@Data
public class UserCreateRequest {
  private String name;
  private UserRole role;
  private String email;
  private String phone;
  private UserStatus status;
  
}
