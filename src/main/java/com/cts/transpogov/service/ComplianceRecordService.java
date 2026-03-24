package com.cts.transpogov.service;

import org.springframework.stereotype.Service;

import com.cts.transpogov.enums.ComplianceResultStatus;
import com.cts.transpogov.repositories.IComplianceRecordRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ComplianceRecordService implements IComplianceRecordService {

    private final IComplianceRecordRepository repo;

    @Override
    public int getComplianceAlerts() {
        return (int) repo.countByResult(ComplianceResultStatus.FAIL);
    }

}

