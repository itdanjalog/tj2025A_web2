package web2.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web2.model.dto.UserDto;
import web2.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * [1] 회원가입
     * POST http://localhost:8080/users/signup
     * Body(JSON):  {  "uid": "danja", "upwd": "1234", "uname": "아이티단자",  "uphone": "010-2222-3333",  "urole": "USER" }
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto dto) {
        int uno = userService.signup(dto);
        return ResponseEntity.ok( uno );
    }

    /**
     * [2] 로그인
     * POST http://localhost:8080/users/login
     * Body(JSON):  {   "uid": "danja",   "upwd": "1234" }
     * Response: { "success": true, "message": "로그인 성공", "uno": 1, "uname": "아이티단자" }
     */
    // 로그인 (세션 기반)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto dto, HttpSession session) {
        UserDto user = userService.login( dto );
        if (user != null ) {
            session.setAttribute("loginUser", user);
            return ResponseEntity.ok( true );
        }
        return ResponseEntity.ok( false );
    }


    /**
     * [3] 내 정보 조회
     * GET /users/me
     * Header: Cookie(JSESSIONID)
     * Response:  { "success": true, "user": {...} }
     */

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<?> myInfo(HttpSession session) {
        Object loginUser = session.getAttribute("loginUser");
        return ResponseEntity.ok( loginUser );
    }

    /**
     * [4] 로그아웃
     * GET /users/logout
     */
    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.removeAttribute("loginUser"); // ✅ 로그인 정보만 삭제
        return ResponseEntity.ok( true );
    }
}
