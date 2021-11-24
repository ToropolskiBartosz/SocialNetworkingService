package com.toropolski.Socialnetworkingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistryRequest {
    private String email;
    private String username;
    private String password;
}
