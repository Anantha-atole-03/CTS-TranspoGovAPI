package com.cts.transpogov.models;


import com.cts.transpogov.enums.AuditStatus;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter 	
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "findings")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Auditid")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "officerid ", nullable = false)
    private Long officerId;

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

    @Column(name = "ReportUrl", length = 500)
    private String reportUrl;

    @Builder.Default
    @OneToMany(mappedBy = "audit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuditFinding> findings = new ArrayList<>();

    /** Helper to keep both sides of the association in sync */
    public void addFinding(AuditFinding finding) {
        finding.setAudit(this);
        this.findings.add(finding);
    }
}