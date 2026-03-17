package com.cts.transpogov.dtos.Audit;



import com.cts.transpogov.enums.AuditStatus;

import java.time.LocalDateTime;
import java.util.List;

public class AuditResponse {

    private Long id;
    private Long officerId;
    private String scope;
    private AuditStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime closedAt;
    private String reportUrl;
    private List<AuditFindingResponse> findings;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOfficerId() { return officerId; }
    public void setOfficerId(Long officerId) { this.officerId = officerId; }
    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }
    public AuditStatus getStatus() { return status; }
    public void setStatus(AuditStatus status) { this.status = status; }
    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }
    public LocalDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(LocalDateTime closedAt) { this.closedAt = closedAt; }
    public String getReportUrl() { return reportUrl; }
    public void setReportUrl(String reportUrl) { this.reportUrl = reportUrl; }
    public List<AuditFindingResponse> getFindings() { return findings; }
    public void setFindings(List<AuditFindingResponse> findings) { this.findings = findings; }
}
