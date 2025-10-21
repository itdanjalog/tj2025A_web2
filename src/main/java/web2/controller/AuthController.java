package web2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web2.service.JwtService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;

    @GetMapping("/check")
    public ResponseEntity<?> checkToken(
            @CookieValue(value = "loginUser", required = false) String token) {
        System.out.println("token = " + token);
        if (token != null && jwtService.validateToken(token)) {
            String uid = jwtService.getUid(token);
            String role = jwtService.getUrole(token);
            return ResponseEntity.ok(Map.of(
                    "isAuthenticated", true,
                    "role", role
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("isAuthenticated", false));
        }
    }


}
