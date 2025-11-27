package com.iush.ca.transversal.infrastructure.controller;

import com.iush.ca.transversal.domain.models.dto.AuthResponse;
import com.iush.ca.transversal.domain.models.dto.LoginRequest;
import com.iush.ca.transversal.domain.models.responses.ResponseObject;
import com.iush.ca.transversal.infrastructure.component.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import static com.iush.ca.transversal.infrastructure.adapters.ResponseBuilder.buildBasicResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject<AuthResponse>> login(@RequestBody LoginRequest request) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var user = userDetailsService.loadUserByUsername(request.username());

        String role = user.getAuthorities().stream()
                .findFirst()
                .orElseThrow()
                .getAuthority()
                .replace("ROLE_", "");

        String token = jwtUtil.generateToken(request.username(), role);

        ResponseCookie cookie = ResponseCookie.from("AUTH_TOKEN", token)
                .httpOnly(true)
                .path("/")
                .maxAge(4 * 60 * 60)
                .sameSite("Lax")
                .build();

        return buildBasicResponse(
                new AuthResponse(token, request.username(), role),
                HttpStatus.OK,
                cookie
        );

    }

    @GetMapping("/me")
    public ResponseEntity<AuthResponse> me(@AuthenticationPrincipal UserDetails user) {
        String username = user.getUsername();
        String role = user.getAuthorities().iterator().next().getAuthority();
        return ResponseEntity.ok(new AuthResponse(null, username, role));
    }


    @PostMapping("/logout")
    public ResponseEntity<ResponseObject<Object>> logout() {
        ResponseCookie deleteCookie = ResponseCookie.from("AUTH_TOKEN", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

        return buildBasicResponse(
                null,
                HttpStatus.OK,
                deleteCookie
        );
    }
}

