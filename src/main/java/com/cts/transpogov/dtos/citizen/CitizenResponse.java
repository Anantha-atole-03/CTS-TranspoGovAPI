package com.cts.transpogov.dtos.citizen;

import java.time.LocalDate;

import com.cts.transpogov.enums.CitizenStatus;

import lombok.Data;

@Data
public class CitizenResponse {
  private Long citizenId;
  private String name;
  private LocalDate dob;
  private String gender;
  private String address;
  private String contactInfo;
  private CitizenStatus status;
}