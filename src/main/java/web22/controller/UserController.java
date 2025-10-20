package web22.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web22.model.dto.UserDto;
import web22.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web22.util.JwtUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

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
//    // 로그인 (세션 기반)
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserDto dto, HttpSession session) {
//        UserDto user = userService.login( dto );
//        if (user != null ) {
//            session.setAttribute("loginUser", user);
//            return ResponseEntity.ok( true );
//        }
//        return ResponseEntity.ok( false );
//    }
    // 로그인 (쿠키 기반)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto dto, HttpServletResponse response) {
        UserDto user = userService.login( dto );
        if (user != null ) {

            // 로그인 성공 시 HttpOnly 쿠키 생성
            // Cookie cookie = new Cookie("sessionUser", user.getUid() );

            // 토큰 발급

            //String token = jwtUtil.createToken( user.getUid() );
            //Cookie cookie = new Cookie("sessionUser", token );


            String token = jwtUtil.generateToken( user.getUid() , user.getUrole() );
            Cookie cookie = new Cookie("sessionUser", token );

            cookie.setHttpOnly(true);   // ✅ 클라이언트 JS 접근 불가
            cookie.setPath("/");        // 모든 경로에 유효
            cookie.setMaxAge(60 * 60);  // 1시간
            cookie.setSecure(false); // ✅ 로컬에서는 false (HTTP 환경) // Secure HTTPS 연결에서만 전송 — 네트워크 탈취 방어용
            response.addCookie(cookie);

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
//    @GetMapping("/me")
//    public ResponseEntity<?> myInfo(HttpSession session) {
//        Object loginUser = session.getAttribute("loginUser");
//        return ResponseEntity.ok( loginUser );
//    }

    @GetMapping("/me")
    public ResponseEntity<?> myInfo( HttpServletRequest request) {
        String uid = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("sessionUser".equals(c.getName())) {
                    String token = c.getValue();

                    uid = jwtUtil.getUid( token );

                    break;
                }
            }
        }

        return ResponseEntity.ok( uid );
    }


    /**
     * [4] 로그아웃
     * GET /users/logout
     */
//    @GetMapping("/logout")
//    public ResponseEntity<?> logout(HttpSession session) {
//        session.removeAttribute("loginUser"); // ✅ 로그인 정보만 삭제
//        return ResponseEntity.ok( true );
//    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout( HttpServletResponse response){

        Cookie cookie = new Cookie("sessionUser", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // maxAge(0) 은 브라우저에게 “즉시 삭제”를 의미합니다.
        response.addCookie(cookie);

        return ResponseEntity.ok( true );
    }


}


// ✅ 세션은 서버 쪽 메모리에 저장되므로 서버가 재시작되면 사라지고,
//✅ 쿠키는 클라이언트(브라우저/앱)에 저장되므로 서버가 꺼져도 유지됩니다.