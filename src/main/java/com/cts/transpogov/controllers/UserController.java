package com.cts.transpogov.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cts.transpogov.dtos.user.UserCreateRequest;
import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.models.User;
import com.cts.transpogov.repositories.UserRepository;
import com.cts.transpogov.service.UserLoginServiceImple;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserLoginServiceImple userLoginServiceImple;

    @PreAuthorize("hasRole('ADMINISTRATOR','CITIZEN_PASSENGER')")
    @PostMapping("/")
    public ResponseEntity<User> addUser(@RequestBody UserCreateRequest user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userLoginServiceImple.createUser(user));
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PROGRAM_MANAGER','GOVERNMENT_AUDITOR')")
    @GetMapping("/")
    public List<User> allUsers() {
        return userLoginServiceImple.getAllUser();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PROGRAM_MANAGER')")
    @PutMapping("/{userId}")
    public void updateUser(@RequestBody User user, @PathVariable Long userId) {
        userLoginServiceImple.UpdateUser(user, userId);
        System.out.println("User updated successfully");
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','COMPLIANCE_OFFICER')")
    @PutMapping("/{userId}/role")
    public void updateRole(@RequestBody UserRole userRole, @PathVariable Long userId) {
        userLoginServiceImple.UpdateUserRoles(userRole, userId);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR','PROGRAM_MANAGER','GOVERNMENT_AUDITOR','TRANSPORT_OFFICER','COMPLIANCE_OFFICER')")
    @GetMapping("/{id}")
    public User getuser(@PathVariable Long id) {
        return userLoginServiceImple.findById(id);
    }
}