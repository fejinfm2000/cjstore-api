package com.cjstore.api.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String ownerName;
}

@Data
class LoginRequest {
    private String email;
    private String password;
}

@Data
class AuthResponse {
    private String token;
    private UUID id;
    private String email;
    private String ownerName;
    private String role;
    private UUID storeId;
}
