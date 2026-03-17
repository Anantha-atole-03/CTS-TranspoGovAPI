package com.cts.transpogov.dtos.Audit;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateAuditRequest {

    @NotNull(message = "officerId is required")
    private Long officerId;

    @NotBlank(message = "scope must not be blank")
    private String scope;

    public Long getOfficerId() { return officerId; }
    public void setOfficerId(Long officerId) { this.officerId = officerId; }
    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }
}