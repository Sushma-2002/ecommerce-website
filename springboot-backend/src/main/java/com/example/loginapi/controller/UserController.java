package com.example.loginapi.controller;

import com.example.loginapi.dto.UserProfileDto;
import com.example.loginapi.entity.User;
import com.example.loginapi.repository.UserRepository;
import com.example.loginapi.util.TokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getProfile(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long userId = TokenUtil.getUserIdFromAuthHeader(authHeader);
        if (userId == null) {
            return ResponseEntity.status(401).build();
        }
        return userRepository.findById(userId)
                .map(this::toProfile)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private UserProfileDto toProfile(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
