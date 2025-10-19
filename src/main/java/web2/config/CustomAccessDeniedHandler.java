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

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper(); // ✅ Jackson 객체

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
        response.setContentType("application/json; charset=UTF-8");

        // ✅ Map 객체로 응답 데이터 구성
//        Map<String, Object> errorResponse = new HashMap<>();
//        errorResponse.put("message", "권한이 없습니다. 관리자에게 문의하세요.");

        // ✅ ObjectMapper를 이용해 JSON 변환 후 전송
        String json = objectMapper.writeValueAsString(  "권한이 없습니다. 관리자에게 문의하세요." );
        response.getWriter().write(json);
    }
}
