//package web2.config;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.RequestDispatcher;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * ✅ SPA(React) 새로고침(F5) 시 404 방지용 필터
// * - /api/**, /static/**, /assets/**, Swagger 등 백엔드/정적 리소스는 제외
// * - 나머지 경로는 index.html 로 forward → React Router가 처리
// */
//@Component
//@Order(Ordered.LOWEST_PRECEDENCE) // 🔽 SecurityFilterChain 이후 실행
//public class SpaWebFilter implements Filter {
//
//    private static final Logger log = LoggerFactory.getLogger(SpaWebFilter.class);
//
//    // 제외할 경로 prefix
//    private static final List<String> EXCLUDE_PATHS = Arrays.asList(
//            "/api/",
//            "/static/",
//            "/assets/",
//            "/swagger-ui",
//            "/v3/api-docs"
//    );
//
//    // 제외할 특정 파일
//    private static final List<String> EXCLUDE_FILES = Arrays.asList(
//            "/favicon.ico",
//            "/manifest.json",
//            "/robots.txt",
//            "/index.html"
//    );
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        String path = req.getRequestURI();
//
//        if (shouldForwardToSpa(path)) {
//            log.debug("SpaWebFilter → forwarding to /index.html : {}", path);
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.html");
//            dispatcher.forward(request, response);
//            return; // 포워드 후 체인 중단
//        }
//
//        // API 또는 정적 리소스는 그대로 통과
//        chain.doFilter(request, response);
//    }
//
//    private boolean shouldForwardToSpa(String path) {
//        // 1️⃣ 특정 파일 제외
//        if (EXCLUDE_FILES.stream().anyMatch(path::equals)) return false;
//
//        // 2️⃣ API/정적 리소스 prefix 제외
//        if (EXCLUDE_PATHS.stream().anyMatch(path::startsWith)) return false;
//
//        // 3️⃣ 확장자(.)가 있는 경우 (ex: .js, .css, .png 등) 제외
//        int lastSlash = path.lastIndexOf('/');
//        int lastDot = path.lastIndexOf('.');
//        if (lastDot > lastSlash) return false;
//
//        // ✅ 그 외 경로는 React 라우트로 간주
//        return true;
//    }
//}