package example.day17;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

// 스프링이 설정 클래스임을 인식하도록 지정
// → 스프링 부트 실행 시 자동으로 이 Bean을 등록함
@Configuration
public class RedisConfig {

    // RedisTemplate 빈(Bean) 생성
    // String 타입의 key와 Object 타입의 value를 처리하도록 설정
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        // RedisTemplate 객체 생성 (Redis 연동의 핵심 클래스)
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // Redis 서버 연결 설정 주입
        // Spring Boot가 관리하는 RedisConnectionFactory를 이용
        template.setConnectionFactory(connectionFactory);

        // ✅ Key Serializer 설정
        // Redis의 key는 문자열로 저장되므로 StringRedisSerializer 사용
        // 예: "student:1", "auth:01012345678"
        template.setKeySerializer(new StringRedisSerializer());

        // ✅ Value Serializer 설정
        // Object(자바 객체)를 JSON 형태로 직렬화하여 Redis에 저장
        // JSON 기반이라 Serializable 인터페이스가 없어도 동작함
        // 저장 예시: {"@class":"example.StudentDto","sno":1,"name":"유재석"}
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 모든 설정이 완료된 RedisTemplate 객체 반환 → 스프링이 Bean 등록
        return template;
    }
}
// 직렬화(Serialization) 는 자바 객체(Object)를 파일, 네트워크, 또는 데이터베이스(Redis 등)에 저장할 수 있도록 바이트(Byte) 형태로 변환하는 과정을 말합니다.
//
//즉, 객체를 그대로 저장할 수 없기 때문에, 저장 가능한 형태(문자열, JSON, 이진 데이터 등)로 바꾸는 과정입니다.
//
//반대로, 저장된 데이터를 다시 자바 객체로 복원하는 것을 역직렬화(Deserialization) 라고 합니다.