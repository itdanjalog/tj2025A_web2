package example2.day04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@SpringBootApplication
@EnableJpaAuditing
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
