package com.cts.transpogov.dtos.program;


import com.cts.transpogov.enums.ResourceStatus;
import com.cts.transpogov.enums.ResourceType;

import lombok.Data;

@Data
public class ResourceResponse {
  private Long resourceId;
  private Long programId;
  private ResourceType type;
  private Double quantity;
  private ResourceStatus status;
}
