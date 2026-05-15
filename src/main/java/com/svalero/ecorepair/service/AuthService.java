package com.svalero.ecorepair.service;

import com.svalero.ecorepair.domain.AppUser;
import com.svalero.ecorepair.domain.UserRole;
import com.svalero.ecorepair.dto.AuthResponse;
import com.svalero.ecorepair.dto.LoginRequest;
import com.svalero.ecorepair.dto.RegisterRequest;
import com.svalero.ecorepair.dto.UserResponse;
import com.svalero.ecorepair.exception.UserAlreadyExistsException;
import com.svalero.ecorepair.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (appUserRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        AppUser user = new AppUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : UserRole.USER);

        AppUser savedUser = appUserRepository.save(user);
        return buildAuthResponse(savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        AppUser user = (AppUser) authentication.getPrincipal();
        return buildAuthResponse(user);
    }

    public UserResponse me(AppUser user) {
        return toUserResponse(user);
    }

    private AuthResponse buildAuthResponse(AppUser user) {
        return new AuthResponse(jwtService.generateToken(user), toUserResponse(user));
    }

    private UserResponse toUserResponse(AppUser user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getRole());
    }
}
