package com.cts.transpogov.dtos.report;

import java.time.LocalDateTime;
import com.cts.transpogov.enums.ReportScope;
import com.cts.transpogov.enums.ReportStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReportResponse {
    private Long reportId;
    private ReportScope scope;
    private String metrics;
    private ReportStatus status;
    private LocalDateTime generatedDate;
}