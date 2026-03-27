package com.cts.transpogov.dtos.report;

import com.cts.transpogov.enums.ReportScope;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequest {
    private ReportScope scope;
}