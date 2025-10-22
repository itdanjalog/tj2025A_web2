package web2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// ===================== 스프링 시큐리티 라이브러리 커스텀 ============
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // 시큐리티(보안) 필터(검증/확인) 체인(연결고리)
    // 미리 만들어진 필터들이 다수...... 그런 필터들을 커스텀(수정)/제외(끄기)

    // !! : HTTP 관련 필터들을 커스텀 , HTTP는 요청과 응답 처리하는 웹 아키텍처
    @Bean // 빈 등록
    public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception{

                // [1] HTTP 요청에 따른 권한 커스텀
                // .authorizeHttpRequests( auth -> auth.requestMatchers("경로").권한 );
                // .permitAll() : 모든 권한 (공개용) 허용
        http.authorizeHttpRequests( auth -> auth.requestMatchers("/**").permitAll() );

                // [2] HTTP 요청에 csrf( 요청간의 해킹 공격 ) POST,PUT 자동 차단 커스텀
                // http.csrf( csrf -> csrf.ignoringRequestMatchers("csrf제외할경로") ); // 운영단계 권장
        http.csrf( csrf -> csrf.disable() ); // 계발단계 권장 , csrf 사용안함.

        // ====================== 완료 ========================== //
        return http.build(); // 커스텀 완료된 객체 반환
    }

} // class end









