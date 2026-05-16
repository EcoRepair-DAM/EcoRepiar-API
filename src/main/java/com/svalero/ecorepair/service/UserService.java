package com.svalero.ecorepair.service;

import com.svalero.ecorepair.domain.AppUser;
import com.svalero.ecorepair.domain.UserRole;
import com.svalero.ecorepair.dto.UserResponse;
import com.svalero.ecorepair.exception.UserNotFoundException;
import com.svalero.ecorepair.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public List<UserResponse> findAll() {
        List<UserResponse> users = new ArrayList<>();
        appUserRepository.findAll().forEach(user -> users.add(toResponse(user)));
        return users;
    }

    public UserResponse updateRole(long id, UserRole role) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        user.setRole(role);
        return toResponse(appUserRepository.save(user));
    }

    public void delete(long id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        appUserRepository.delete(user);
    }

    private UserResponse toResponse(AppUser user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getRole());
    }
}
