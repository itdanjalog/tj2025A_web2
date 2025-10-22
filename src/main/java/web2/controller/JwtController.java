package web2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import web2.service.JwtService;

@RestController
@RequestMapping("/api/jwt")
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    // [1] 토큰 생성
    @GetMapping("/create") // http://localhost:8080/api/jwt/create?데이터=바나나
    public ResponseEntity<?> 토큰생성(
            @RequestParam String 데이터){
        String token = jwtService.토큰생성( 데이터 );
        return ResponseEntity.ok( token );
    }

    // [2] 토큰 검증
    @GetMapping("/read")// http://localhost:8080/api/jwt/read?토큰=xxxxx
    public ResponseEntity<?> 토큰검증(
            @RequestParam String 토큰){
        boolean result = jwtService.토큰검증( 토큰 );
        return ResponseEntity.ok( result );
    }

    // [3] 토큰 값 추출
    @GetMapping("/value")
    public ResponseEntity<?> 토큰값추출(
            @RequestParam String 토큰 ){
        String value = jwtService.값추출( 토큰 );
        return ResponseEntity.ok( value );
    }

}















