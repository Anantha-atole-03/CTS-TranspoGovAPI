package com.cts.transpogov.dtos.citizen;

import lombok.Data;
import java.time.LocalDate;

import com.cts.transpogov.enums.CitizenStatus;


@Data
public class CitizenCreateRequest {
  private String name;
  private LocalDate dob;
  private String gender;
  private String address;
  private String phone;
  private String password;
  private CitizenStatus status;
}



