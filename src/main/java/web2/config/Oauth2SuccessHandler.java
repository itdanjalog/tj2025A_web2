package web2.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    // [1] AuthenticationSuccessHandler 구현체를 만든다.
    // [2] onAuthenticationSuccess 메소드 구현한다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 타사 *로그인 성공 이후* 로직 커스텀( 실패 커스텀 없다 )

        System.out.println( authentication ); // authentication 인증 ( 토큰 , 개인정보 등 )

        // [3] 로그인 성공한 회원의 타사 발급한 토큰 확인
        // 3-1 : oauth2 관련 라이브러리 설치 : implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
        // 3-2 : 로그인 성공한 토큰 확인 , Oauth2authenticationToken , 타사의 회사명
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        System.out.println("authToken = " + authToken);

        // 3-3 : 로그인 성공한 회원의 동의항목(정보) , Oauth2User
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal(); // Principal() 주체(로그인성공한정보)
        System.out.println("oauth2User = " + oauth2User);

        // 1. 어느 타사의 로그인 성공 인지 확인
        // 2. 로그인 성공한 동의항목(정보) 가져오기 , 개인정보(아이디,회원명,주소,전화번호)
        // 3. 자사(우리)서버와 타사서버 통합 로그인( web2 : 토큰/쿠키 발급 )
        // 4. 자사(우리)서버와 타사서버 통합 DB( 최초 로그인이면 DB저장 , 아니면 DB처리 없음 )

    }
}






