package com.cts.transpogov.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.transpogov.dtos.citizen.CitizenCreateRequest;
import com.cts.transpogov.dtos.citizen.CitizenResponse;
import com.cts.transpogov.dtos.citizen.CitizenUpdateRequest;
import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.service.CitizenServiceImpl;
import com.cts.transpogov.service.UserLoginServiceImple;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/citizens")
@RequiredArgsConstructor
public class CitizenController {

	@Autowired
    private final CitizenServiceImpl citizenServiceImpl;
	private final UserLoginServiceImple UserLoginServiceImple;

    @PostMapping
    public CitizenResponse createCitizen(@RequestBody CitizenCreateRequest request) {
        return citizenServiceImpl.addCitizen(request);
    }

    @PutMapping("/{id}")
    public CitizenResponse updateCitizen(@PathVariable Long id,
                                         @RequestBody CitizenUpdateRequest request) {
        return citizenServiceImpl.updateCitizen(id, request);
    }

    @GetMapping("/{id}")//as a admin get all data
    public CitizenResponse getCitizen(@PathVariable Long id) {
        return citizenServiceImpl.getCitizenById(id);
    }

    @GetMapping
    public List<CitizenResponse> getAllCitizens() {
        return citizenServiceImpl.getAll();
    }
    @PutMapping("/{userId}/role")
    public void updateRole(@RequestBody UserRole userRole, @PathVariable Long userId) {
        UserLoginServiceImple.UpdateUserRoles(userRole, userId);
    }
}