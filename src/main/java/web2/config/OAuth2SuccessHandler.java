package web2.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web2.service.UserService;
import web2.util.JwtUtil;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // ✅ 구글에서 받은 기본 정보
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // DB에 사용자 존재하지 않으면 회원가입 처리 (서비스에서 처리 가능)
        userService.findOrCreateGoogleUser(email, name);

        // ✅ JWT 발급 (OAuth2 로그인 사용자는 USER 권한으로)
        String token = jwtUtil.generateToken(email, "USER");

        // ✅ HttpOnly 쿠키 저장
        Cookie cookie = new Cookie("loginUser", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1시간
        response.addCookie(cookie);

        // ✅ 로그인 후 리다이렉트 (프론트엔드 메인 페이지 등)
        response.sendRedirect("http://localhost:5173"); // 프론트엔드 루트 페이지
    }
}