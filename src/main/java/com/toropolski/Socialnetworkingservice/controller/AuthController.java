package com.toropolski.Socialnetworkingservice.controller;

import com.toropolski.Socialnetworkingservice.dto.AuthenticationResponse;
import com.toropolski.Socialnetworkingservice.dto.LoginRequest;
import com.toropolski.Socialnetworkingservice.dto.RegistryRequest;
import com.toropolski.Socialnetworkingservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegistryRequest registryRequest){
        authService.signup(registryRequest);

        return new ResponseEntity<>("User Registration Successful",
                                HttpStatus.OK);
    }
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account Activated Successfully", HttpStatus.OK);
    }
}

