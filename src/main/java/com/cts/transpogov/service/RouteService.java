package com.cts.transpogov.service;

import org.springframework.stereotype.Service;

import com.cts.transpogov.enums.RouteStatus;
import com.cts.transpogov.repositories.IRouteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteService implements IRouteService {

    private final IRouteRepository routeRepo;

    @Override
    public int countActiveRoutes() {
        return (int) routeRepo.countByStatus(RouteStatus.ACTIVE);
    }
}