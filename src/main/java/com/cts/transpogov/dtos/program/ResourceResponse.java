package com.cts.transpogov.dtos.program;


import com.cts.transpogov.enums.ResourceStatus;

import lombok.Data;

@Data
public class ResourceResponse {
  private Long resourceId;
  private Long programId;
  private String type;
  private Double quantity;
  private ResourceStatus status;
}
