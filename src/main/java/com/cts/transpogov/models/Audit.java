package com.cts.transpogov.models;

import java.time.LocalDateTime;

import com.cts.transpogov.enums.AuditStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ToString(exclude = "findings")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "audit")
public class Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Auditid")
	@EqualsAndHashCode.Include
	private Long id;

	@JoinColumn(name = "officer_id ", nullable = false)
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	private User officer;

	@Column(name = "Scope", nullable = false, length = 300)
	private String scope;

	@Enumerated(EnumType.STRING)
	@Column(name = "Status", nullable = false, length = 20)
	@Builder.Default
	private AuditStatus status = AuditStatus.OPEN;

	@Column(name = "Date", nullable = false)
	@Builder.Default
	private LocalDateTime startedAt = LocalDateTime.now();

	@Column(name = "ClosedAt")
	private LocalDateTime closedAt;

//	@Column(name = "ReportUrl", length = 500)
//	private String reportUrl;

	private String findings;

}