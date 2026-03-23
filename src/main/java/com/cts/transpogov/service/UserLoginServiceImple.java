package com.cts.transpogov.service;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.cts.transpogov.dtos.user.UserCreateRequest;
import com.cts.transpogov.enums.UserRole;
import com.cts.transpogov.models.User;
import com.cts.transpogov.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; 

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j 
public class UserLoginServiceImple implements IUserLoginService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User createUser(UserCreateRequest request) {
        log.info("Attempting to create user with email: {}", request.getEmail());
        
        if (request == null) {
            log.error("User creation failed: Request object is null");
            throw new IllegalArgumentException("Request cannot be null");
        }

        User user = modelMapper.map(request, User.class);
        
        if (user == null) {
            log.error("Mapping failure: ModelMapper returned null for UserCreateRequest");
            throw new IllegalStateException("Failed to map UserCreateRequest to User");
        }

        User savedUser = userRepository.save(user);
        log.info("User successfully created with ID: {}", savedUser.getUserId());
        return savedUser;
    }

    @Override
    public List<User> getAllUser() {
        log.debug("Fetching all users from database");
        return userRepository.findAll();
    }

    @Override
    public void UpdateUser(User user, Long userId) {
        log.info("Attempting to update user details for ID: {}", userId);

        if (userId == null || userId <= 0) {
            log.warn("Update failed: Invalid userId provided: {}", userId);
            throw new IllegalArgumentException("Invalid user UserId");
        }

        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> {
                log.error("Update failed: User with ID {} not found", userId);
                return new RuntimeException("User not found");
            });

        modelMapper.map(user, existingUser);
        existingUser.setUserId(userId);

        userRepository.save(existingUser);
        log.info("User with ID: {} updated successfully", userId);
    }

    @Override
    public void UpdateUserRoles(UserRole userRole, Long userId) {
        log.info("Updating role to {} for user ID: {}", userRole, userId);
        
        if (userId == null || userRole == null) {
            log.warn("Role update failed: userId or userRole is null");
            throw new IllegalArgumentException("Invalid user UserId or UserRole");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> {
                log.error("Role update failed: User {} not found", userId);
                return new RuntimeException("User not found");
            });

        user.setRole(userRole);
        userRepository.save(user);
        log.info("Role updated successfully for user ID: {}", userId);
    }

    @Override
    public User findById(Long id) {
        log.debug("Finding user by ID: {}", id);
        if (id == null) {
            log.warn("findById called with null ID");
            throw new IllegalArgumentException("Invalid user ID");
        }
        return userRepository.findById(id).orElse(null);
    }
}