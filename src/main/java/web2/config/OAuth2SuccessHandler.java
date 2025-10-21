package web2.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web2.service.UserService;
import web2.service.JwtService;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtUtil;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // ✅ 어떤 Provider(google/kakao)인지 식별
        String provider = authToken.getAuthorizedClientRegistrationId(); // "google" 또는 "kakao"
        System.out.println("✅ OAuth2 Provider: " + provider);

        String email = null;
        String name = null;

        // ✅ provider별 데이터 파싱
        if ("google".equals(provider)) {
            email = oAuth2User.getAttribute("email");
            name = oAuth2User.getAttribute("name");

        } else if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
            if (kakaoAccount != null) {
                email = (String) kakaoAccount.get("email");

                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                if (profile != null) {
                    name = (String) profile.get("nickname");
                }
            }

        } else {
            throw new IllegalArgumentException("지원하지 않는 OAuth2 Provider: " + provider);
        }

        System.out.println("✅ 로그인 사용자 정보: " + name + " / " + email);

        // ✅ DB 처리 (새 유저면 생성)
        userService.findOrCreateGoogleUser(  email, name);

        // ✅ JWT 생성 (OAuth2 로그인 사용자는 USER 권한)
        String token = jwtUtil.generateToken(email, "USER");

        // ✅ HttpOnly 쿠키로 저장
        Cookie cookie = new Cookie("loginUser", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60); // 1시간
        response.addCookie(cookie);

        // ✅ 로그인 성공 후 리다이렉트 (프론트엔드 루트)
        response.sendRedirect("/");
    }

        // ✅ 로그인 후 리다이렉트 (프론트엔드 메인 페이지 등)
        //response.sendRedirect("http://localhost:5173"); // 프론트엔드 루트 페이지
}
