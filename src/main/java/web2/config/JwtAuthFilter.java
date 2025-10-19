package web2.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import web2.util.JwtUtil;

import java.io.IOException;
import java.util.List;

/**
 * ✅ JWT 인증 필터 (SecurityConfig에서 분리)
 * - 매 요청마다 실행되어 JWT 토큰을 검증하고, SecurityContext에 인증정보 저장
 * - 쿠키(HttpOnly)에 저장된 JWT 사용
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        // ✅ 1. 쿠키에서 JWT 토큰 추출
        String token = null;
        if (req.getCookies() != null) {
            for (Cookie c : req.getCookies()) {
                if ("sessionUser".equals(c.getName())) {
                    token = c.getValue();
                }
            }
        }

        // ✅ 2. 토큰이 존재하고 유효한 경우 SecurityContext에 등록
        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.getUid(token);
            String role = jwtUtil.getUrole(token);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            username, null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // ✅ 3. 필터 체인 계속 실행
        chain.doFilter(req, res);
    }
}
