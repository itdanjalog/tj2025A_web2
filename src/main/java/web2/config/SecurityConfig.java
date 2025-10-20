package web2.config; // 현재 설정 클래스가 위치한 패키지

// 롬복 어노테이션
import lombok.RequiredArgsConstructor;

// 스프링 설정 관련 어노테이션들
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// HTTP 요청 보안 설정 관련 클래스
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

// 보안 필터 체인 및 인증 관련 클래스
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// JWT 관련 유틸리티 (토큰 검증용)


//@EnableMethodSecurity // @PreAuthorize, @Secured 등을 사용할 수 있게 허용
@RequiredArgsConstructor // final 필드 자동 주입 (생성자 생성)
@Configuration // 해당 클래스가 스프링 설정 파일임을 명시
public class SecurityConfig {

    // JWT 관련 유틸리티 클래스 (토큰 검증용)
    //private final JwtUtil jwtUtil;

    // 권한 부족(403 Forbidden) 발생 시 실행될 핸들러
    private final CustomAccessDeniedHandler accessDeniedHandler;

    // JWT 인증 필터 (토큰 검증 및 SecurityContext 설정 담당)
    private final JwtAuthFilter jwtAuthFilter; // ✅ 외부 클래스로 분리된 필터 주입

    private final OAuth2SuccessHandler oAuth2SuccessHandler;


    // Spring Security의 필터 체인 구성 메소드
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(Customizer.withDefaults()) // ✅ CORS 설정 활성화 (WebMvcConfigurer 연결됨)
                // [1] CSRF 보호 비활성화 (JWT는 토큰 기반이므로 불필요)
                .csrf(csrf -> csrf.disable())

                // [2] 세션 관리 정책 : STATELESS (세션 사용 안함, JWT만 사용)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // [3] URL별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // 모든 요청 허용 (임시, 실제 운영 시 세분화 필요)
                        .requestMatchers("/**").permitAll()
                        // /api/admin/** 경로는 ADMIN 권한 필요
                )

                // [4] 예외(오류) 처리 핸들러 등록
                .exceptionHandling(ex -> ex
                        // 권한 부족 시 CustomAccessDeniedHandler 실행
                        .accessDeniedHandler(accessDeniedHandler)
                )

                // [5] UsernamePasswordAuthenticationFilter 이전에 JWT 필터 추가
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth -> oauth
                        .successHandler(oAuth2SuccessHandler) // ✅ OAuth2 로그인 성공 시 커스텀 처리
                );

        // [6] 최종 SecurityFilterChain 객체 반환
        return http.build();
    }

}
