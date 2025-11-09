package example2.day04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@SpringBootApplication
@EnableJpaAuditing // JPA 감사 기능 활성화 = 레코드(영속) 등록/수정 시점
public class AppStart {
    public static void main(String[] args) {
        SpringApplication.run( AppStart.class );
    }
    @Bean // 특정한 경로는 시큐리티 무시.
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web)-> web.ignoring()
                .requestMatchers("/api/todo/**");
    }
}
