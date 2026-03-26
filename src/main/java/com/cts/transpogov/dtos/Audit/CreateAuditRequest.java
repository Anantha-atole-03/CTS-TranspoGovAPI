package com.cts.transpogov.dtos.Audit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAuditRequest {

	@NotNull(message = "officerId is required")
	private Long officerId;

	@NotBlank(message = "scope must not be blank")
	private String scope;
	@NotBlank(message = "scope must not be blank")
	private String findings;

}