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

/**
 * Service implementation for managing user login and profile state.
 * Annotated with @Transactional to ensure database integrity during updates.
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j 
public class UserLoginServiceImple implements IUserLoginService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /**
     * Converts a request DTO into a User entity and persists it.
     * @param request The user creation details.
     * @return The newly created User entity.
     * @throws IllegalStateException if the mapping process fails.
     */
    @Override
    public User createUser(UserCreateRequest request) {
        log.info("Attempting to create user with email: {}", request.getEmail());
        
        User user = modelMapper.map(request, User.class);
        
        if (user == null) {
            log.error("Mapping failure: ModelMapper returned null for UserCreateRequest");
            throw new IllegalStateException("Failed to map UserCreateRequest to User");
        }

        User savedUser = userRepository.save(user);
        log.info("User successfully created with ID: {}", savedUser.getUserId());
        return savedUser;
    }

    /**
     * Retrieves all users currently stored in the system.
     * @return A list of all User entities.
     */
    @Override
    public List<User> getAllUser() {
        log.debug("Fetching all users from database");
        return userRepository.findAll();
    }

    /**
     * Updates an existing user's profile by merging new data into the existing record.
     * @param user   The object containing updated data.
     * @param userId The ID of the user to update.
     * @throws IllegalArgumentException if ID is invalid.
     * @throws RuntimeException if the user is not found.
     */
    @Override
    public void updateUser(User user, Long userId) {
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

        // Merges non-null fields from 'user' into 'existingUser'
        modelMapper.map(user, existingUser);
        
        // Ensure the ID remains consistent during the merge
        existingUser.setUserId(userId);

        userRepository.save(existingUser);
        log.info("User with ID: {} updated successfully", userId);
    }

    /**
     * Specifically updates the administrative or access role of a user.
     * @param userRole The new role to assign.
     * @param userId   The ID of the target user.
     */
    @Override
    public void updateUserRoles(UserRole userRole, Long userId) {
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

    /**
     * Finds a single user by their primary key.
     * @param id The user ID.
     * @return The User entity if found, or null if not present.
     */
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