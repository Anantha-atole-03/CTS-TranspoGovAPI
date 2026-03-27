package com.cts.transpogov.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cts.transpogov.enums.CitizenStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "citizens")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Citizen {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "citizen_id", updatable = false, nullable = false)
  private Long citizenId;

  private String name;
  private LocalDate dob;
  private String gender;
  private String address;
  @Column(name = "contact_info", columnDefinition = "text")
  private String phone;
  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  private CitizenStatus status;

  @CreationTimestamp private LocalDateTime createdAt;
  @UpdateTimestamp private LocalDateTime updatedAt;
}