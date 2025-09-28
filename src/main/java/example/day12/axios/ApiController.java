package example.day12.axios;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    // 1. JSON 요청/응답
    @PostMapping("/json")
    public ResponseEntity<Map<String, Object>> jsonTest(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        result = body;
        return ResponseEntity.ok( result );
    }

    // 2. Multipart (파일 업로드)
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> upload(@RequestParam MultipartFile file,
                                                      @RequestParam String desc) {
        Map<String, Object> result = new HashMap<>();
        result.put("filename", file.getOriginalFilename());
        result.put("size", file.getSize());
        result.put("desc", desc);
        return ResponseEntity.ok( result );
    }

    // 3. Form-urlencoded
    @PostMapping("/loginForm")
    public ResponseEntity<Map<String, Object>> loginForm(@RequestParam String username,
                                                         @RequestParam String password) {
        Map<String, Object> result = new HashMap<>();
        result.put("login", "success");
        result.put("user", username);
        return ResponseEntity.ok(result);
    }

    // 4. Text 응답
    @GetMapping("/text")
    public ResponseEntity<String> getText(@RequestParam String msg,
                                          @RequestParam String user) {
        return ResponseEntity.ok("메시지: " + msg + ", 사용자: " + user);
    }

    // 5. 세션 로그인 (withCredentials)
    @PostMapping("/loginSession")
    public ResponseEntity<Map<String, Object>> loginSession(@RequestBody Map<String, String> body,
                                                            HttpSession session) {
        String user = body.get("username");
        session.setAttribute("user", user);

        Map<String, Object> result = new HashMap<>();
        result.put("login", "success");
        result.put("sessionUser", session.getAttribute("user"));
        return ResponseEntity.ok(result);
    }

    // 6. 로그인된 회원 확인
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getSessionUser(HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        String user = (String) session.getAttribute("user");

        if (user != null) {
            result.put("login", "yes");
            result.put("sessionUser", user);
            return ResponseEntity.ok(result);
        } else {
            result.put("login", "no");
            result.put("message", "로그인한 사용자가 없습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }

    // 7. 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
        session.invalidate(); // 세션 삭제
        Map<String, Object> result = new HashMap<>();
        result.put("logout", "success");
        return ResponseEntity.ok(result);
    }

}
