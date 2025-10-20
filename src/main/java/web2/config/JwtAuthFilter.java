package web2.config;//package web22.config;

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
// ====================================================================================================
// ✅ JwtAuthFilter : 모든 요청(Request)마다 실행되는 JWT 인증 필터
// - OncePerRequestFilter : 요청당 단 한 번만 실행되는 스프링 시큐리티 기본 필터 클래스 상속
// - 역할 : 요청 헤더(또는 쿠키)에 있는 JWT 토큰을 검증하고, 인증 정보를 SecurityContext에 등록
// ====================================================================================================

@Component // 스프링이 자동으로 감지하여 Bean(컴포넌트)으로 등록
@RequiredArgsConstructor // final 필드(jwtUtil)를 자동 주입하기 위한 생성자 생성 (Lombok)
public class JwtAuthFilter extends OncePerRequestFilter {

    // ✅ JwtUtil 주입 : 토큰 검증 및 정보 추출 기능을 담당
    private final JwtUtil jwtUtil;

    // ====================================================================================================
    // ✅ doFilterInternal() : 스프링 시큐리티 필터 체인에서 매 요청마다 자동 실행되는 메소드
    // - 매 요청 시 JWT를 검증하고 인증 정보를 SecurityContext에 저장
    // ====================================================================================================
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        // ================================================================================================
        // ✅ 1. 쿠키에서 JWT 토큰 추출
        // ================================================================================================
        // - 클라이언트가 요청을 보낼 때 HttpOnly 쿠키(loginUser)에 JWT가 담겨옴
        // - 요청 헤더 대신 쿠키에서 토큰을 찾는 구조
        // ================================================================================================
        String token = null; // 초기값 null로 설정
        if (req.getCookies() != null) { // 요청에 쿠키가 존재하는 경우
            for (Cookie c : req.getCookies()) { // 모든 쿠키 반복 탐색
                if ("loginUser".equals(c.getName())) { // 쿠키 이름이 "loginUser"이면
                    token = c.getValue(); // JWT 토큰 값 추출
                }
            }
        }

        // ================================================================================================
        // ✅ 2. 토큰이 존재하고 유효한 경우, SecurityContext에 인증 객체 등록
        // ================================================================================================
        // - validateToken() : 서명 및 만료 여부 검증 (JwtUtil에서 수행)
        // - 유효한 토큰이면 UID, ROLE 정보를 추출해 인증 객체 생성
        // ================================================================================================
        if (token != null && jwtUtil.validateToken(token)) { // 토큰이 있고 유효하다면
            // 토큰에서 사용자 아이디(uid) 추출
            String username = jwtUtil.getUid(token);
            // 토큰에서 사용자 권한(role) 추출 (예: USER / ADMIN)
            String role = jwtUtil.getUrole(token);

            // ✅ 인증 객체 생성
            // UsernamePasswordAuthenticationToken : 스프링 시큐리티가 인식할 수 있는 표준 인증 객체
            // - principal(사용자 이름)
            // - credentials(비밀번호, 여기서는 null)
            // - authorities(권한 목록)
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            username, // 사용자 이름
                            null, // 비밀번호(불필요)
                            List.of(new SimpleGrantedAuthority("ROLE_" + role)) // 권한을 ROLE_ 형식으로 변환
                    );

            // ✅ SecurityContext에 인증 정보 등록
            // - 등록된 인증 객체는 이후 Controller, @PreAuthorize 등에서 사용 가능
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // ================================================================================================
        // ✅ 3. 필터 체인 계속 실행
        // ================================================================================================
        // - 다른 필터들이 계속 요청을 처리하도록 전달 (반드시 호출해야 함)
        chain.doFilter(req, res);
    }
}
