package example2.day02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@SpringBootApplication
@EnableJpaAuditing // ✅ JPA Auditing 기능 활성화 (BaseTime 자동 기록)
@EnableWebSecurity // 시큐리티 설정 어노테이션
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run(AppStart.class, args);
        System.out.println("✅ AppStart 실행 완료!");
    }
    @Bean // 특정 경로 전체를 Spring Security 검사에서 제외
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/api/goods/**");
    }
}
