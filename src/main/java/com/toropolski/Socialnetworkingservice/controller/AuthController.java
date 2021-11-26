package com.toropolski.Socialnetworkingservice.controller;

import com.toropolski.Socialnetworkingservice.dto.AuthenticationResponse;
import com.toropolski.Socialnetworkingservice.dto.LoginRequest;
import com.toropolski.Socialnetworkingservice.dto.RefreshTokenRequst;
import com.toropolski.Socialnetworkingservice.dto.RegistryRequest;
import com.toropolski.Socialnetworkingservice.service.AuthService;
import com.toropolski.Socialnetworkingservice.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

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

    @PostMapping("/refresh/token")
    public AuthenticationResponse  refreshTokens(@Valid @RequestBody RefreshTokenRequst refreshTokenRequst){
        return authService.refreshToken(refreshTokenRequst);
    }

    @PostMapping("/logaut")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequst refreshTokenRequst){
        refreshTokenService.deleteRefreshToken(refreshTokenRequst.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
    }
}

