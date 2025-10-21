package web2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web2.service.JwtService;

import java.util.Map;

@RestController
@RequestMapping("/api/token") // 공통URL 정의
@RequiredArgsConstructor
public class TokenController {
    private final JwtService jwtService;

    // ✅ 1. 토큰 생성 API
    // 예시: POST /api/token
    @GetMapping("/create") // http://localhost:8080/api/token/create?value=사과
    // eyJhbGciOiJIUzI1NiJ9.eyJ2YWx1ZSI6IuyCrOqzvCIsImlhdCI6MTc2MTA3Mzg2NywiZXhwIjoxNzYxMDczODY4fQ.DciH1EhkP5uIXgEPUZhiDkndpRT_PCzMm9DoHyhxNMA
    public ResponseEntity<?> createToken(@RequestParam String value ) {
        String token = jwtService.토큰생성( value );
        return ResponseEntity.ok( token );
    }

    // ✅ 2. 토큰 유효성 검사 API
    // 예시: GET /api/test/validate?token=xxx.yyy.zzz
    // http://localhost:8080/api/token/validate?token=
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestParam String token) {
        boolean result = jwtService.토큰검증(token);
        return ResponseEntity.ok( result );
    }

    // ✅ 3. 토큰 정보 추출 API
    // 예시: GET /api/test/info?token=xxx.yyy.zzz
    @GetMapping("/info")
    public ResponseEntity<?> getTokenInfo(@RequestParam String token) {
        String value = jwtService.값추출(token);
        return ResponseEntity.ok( value );
    }
}
