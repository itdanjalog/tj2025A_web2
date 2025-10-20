package web2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// ====================================================================================================
// ✅ CustomAccessDeniedHandler : Spring Security에서 권한이 부족할 때(403 Forbidden) 발생하는 예외 처리 클래스
// - AccessDeniedHandler 인터페이스 구현체
// - @PreAuthorize("hasRole('ADMIN')") 또는 .hasRole("ADMIN") 검사에서 실패할 경우 실행됨
// ====================================================================================================

@Component // 스프링이 자동으로 빈으로 등록하여 SecurityConfig에서 인식 가능하게 함
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // ====================================================================================================
    // ✅ handle() 메서드 : 권한이 부족한 사용자가 보호된 리소스에 접근할 때 자동 호출됨
    // - AccessDeniedException : Spring Security가 던지는 권한 거부 예외
    // - response 객체를 통해 사용자에게 JSON 형태로 에러 메시지 응답을 전달
    // ====================================================================================================
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        // ================================================================================================
        // ✅ 1. 응답 데이터(메시지) 생성
        // ================================================================================================
        // 클라이언트에게 보낼 데이터를 Map 형태로 구성 (JSON 변환 예정)
        Map<String, Object> data = new HashMap<>();
        data.put("data", "권한이 없습니다. 관리자에게 문의하세요."); // 사용자에게 보여줄 메시지

        // ================================================================================================
        // ✅ 2. ObjectMapper를 이용하여 Map → JSON 문자열로 변환
        // ================================================================================================
        // ObjectMapper : Jackson 라이브러리의 핵심 클래스 (Java 객체 ↔ JSON 변환 기능)
        ObjectMapper objectMapper = new ObjectMapper();
        // writeValueAsString() : 자바 객체를 JSON 형식 문자열로 직렬화(Serialize)
        String json = objectMapper.writeValueAsString(data);

        // ================================================================================================
        // ✅ 3. HTTP 응답 설정 및 전송
        // ================================================================================================
        // 응답 컨텐츠 타입을 JSON으로 설정 (한글 깨짐 방지를 위해 UTF-8 지정)
        response.setContentType("application/json; charset=UTF-8");

        // 클라이언트로 JSON 데이터 직접 전송
        response.getWriter().write(json);
    }
}