package com.cjstore.api.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String ownerName;
}
