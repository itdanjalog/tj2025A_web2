package web2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 설정 관련 빈 등록
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins( "http://localhost:5173" , "http://localhost:5174" )
                .allowedMethods("GET","POST","PUT","DELETE")
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}
