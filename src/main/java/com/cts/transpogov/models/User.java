package com.cts.transpogov.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.enums.UserStatus;

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
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", updatable = false, nullable = false)
	private Long userId;
	private String name;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	private String email;
	private String phone;
	private String password;
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public User(String name, UserRole role, String email, String phone, String password, UserStatus status,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.name = name;
		this.role = role;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}