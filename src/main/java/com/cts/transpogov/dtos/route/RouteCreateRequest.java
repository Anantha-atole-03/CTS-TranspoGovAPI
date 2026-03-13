package com.cts.transpogov.dtos.route;


import com.cts.transpogov.enums.RouteStatus;

import lombok.Data;

@Data
public class RouteCreateRequest {
  private String title;
  private String type;
  private String startPoint;
  private String endPoint;
  private RouteStatus status;
}
