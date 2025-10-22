package web2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// ===================== 스프링 시큐리티 라이브러리 커스텀 ============
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // 시큐리티(보안) 필터(검증/확인) 체인(연결고리)
    // 미리 만들어진 필터들이 다수...... 그런 필터들을 커스텀(수정)/제외(끄기)

    private final JwtAuthFilter jwtAuthFilter; // 내가만든 토큰을 시큐리티 토큰 방식으로 통합한 클래스

    // !! : HTTP 관련 필터들을 커스텀 , HTTP는 요청과 응답 처리하는 웹 아키텍처
    @Bean // 빈 등록
    public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception{

                // [1] HTTP 요청에 따른 권한 커스텀
                // .authorizeHttpRequests( auth -> auth.requestMatchers("경로").권한 );
                    // .permitAll() : 모든 권한 (공개용) 허용
                    // .hasRole("권한명") , .hasAnyRole("권한명", "권한명");
        http.authorizeHttpRequests( auth -> auth
                .requestMatchers("/api/user/info").hasAnyRole( "USER", "ADMIN") // 2개이상 가능, 권한명은 대문자 권장
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // admin 관련 controller는 "ADMIN"권한일때 만가능
                .requestMatchers("/**").permitAll() ); // 모든 권한 허용은 항상 최하단에 정의한다.

                // [2] HTTP 요청에 csrf( 요청간의 해킹 공격 ) POST,PUT 자동 차단 커스텀
                // http.csrf( csrf -> csrf.ignoringRequestMatchers("csrf제외할경로") ); // 운영단계 권장
        http.csrf( csrf -> csrf.disable() ); // 계발단계 권장 , csrf 사용안함.

                // [3] 시큐리티 내부에서 사용되는 (세션)토큰 , UsernamePasswordAuthenticationToken
                // web2 실습에서는 쿠키 기반의 토큰 구현중 = 시큐리티가 제공하는 세션 사용안함
                // [3-1] 시큐리티 세션 끄기
        http.sessionManagement( s -> s.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) );
                // [3-2] UsernamePasswordAuthenticationToken 을 개발자가 만든 토큰 대체
        // http.addFilterBefore( 내가만든토큰객체필터 , UsernamePasswordAuthenticationFilter.class );
        http.addFilterBefore( jwtAuthFilter , UsernamePasswordAuthenticationFilter.class );

        // ====================== 완료 ========================== //
        return http.build(); // 커스텀 완료된 객체 반환
    }

} // class end
