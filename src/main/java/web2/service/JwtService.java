package web2.service;

import com.github.dockerjava.api.model.Secret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // [JWT] : JSON(자바스크립트객체) WEB Token(징표)
    // 웹에서 자바스크립트 기반(클라이언트) 의 특정한 데이터를 대신하는 징표
    // 특정한 데이터를 직접적으로 보여주지 않고 징표를 대신 보여주는 구조
    // 웹(클라이언트)에서 데이터 숨기기( 보안 )

    // JWT 토큰 가능하지만 , 데이터들 간의 서로다른 토큰 과 복잡한 계산식 추가
    // SHA-256 알고리즘 사용하여 비밀키( 임의, 개발자만들기, 토큰 풀기 위한 비밀번호)를 32글자이상
    // 1-2 : 내가 만든 임의의 토큰에 사용되는 계산식의 비밀키
    private final String 비밀번호 = "123456789123456789123456789123456789"; // 난수 키 보다는 직접 생성한 (임의)키 권장
    // 1-2 : 비밀키에 알고리즘 적용 하여 계산식(미리 태어난 사람이 SHA) 생성
    private final Key 비밀키 = Keys.hmacShaKeyFor( 비밀번호.getBytes( ) );

    // [1] 토큰 생성
    public String 토큰생성( String 데이터 ){
        // 1-1 토큰 생성 : 라이브러리
        String token = Jwts.builder() // .builder() : 빌더패턴(생성자 대신에 함수형 객체생성) 이용한 토큰 생성
                .claim( "key" , 데이터 ) // .claim( key , value ) : 토큰에 넣을 데이터 대입
                .setIssuedAt( new Date() ) // .setIssuedAt( 토큰발급날짜/시간 )
                // .setExpiration( 토큰만료시간 )
                // .setExpiration( new Date( System.currentTimeMillis()+1000 * 초 ) )
                .setExpiration( new Date( System.currentTimeMillis()+1000 * 30 ) ) // 30초
                .signWith( 비밀키 , SignatureAlgorithm.HS256 )// 1-2 알고리즘 이용한 토큰에 서명하기 + HS256알고리즘
                .compact(); // 최종 JWT 문자열 형태로 생성
        System.out.println("token = " + token); // 확인 soutv
        return token;
    }

    // [2] 토큰 검증 : 토큰 유효 검사
    public boolean 토큰검증( String 토큰 ){
        try {
            Jwts.parser() // parser
                    .setSigningKey(비밀키) // 서명 검증에 필요한 비밀키 대입
                    .build() // 비밀키 확인
                    .parseClaimsJws(토큰); // 검증할 토큰 대입
            return true; // 예외가 발생하지 않으면 토큰 정상
        } catch (  Exception e ) {
            return false; // 토큰 비정상
        }
    }

    // [3] 토큰 payload(내용물) claim 값 추출
    public String 값추출(String 토큰){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey( 비밀키 )
                    .build()
                    .parseClaimsJws( 토큰 )
                    .getBody(); // 검증후 클레임(내용물) 가져오기
            System.out.println("claims = " + claims);
            String value = claims.get("key" , String.class );
            return value;
        } catch (Exception e) {
            return null;
        }
    }

} // class end
// new Date() : 자바에서 시스템(컴퓨터)의 시간을 반환하는 클래스
// 밀리초 : 1/1000초 , new Date(  System.currentTimeMillis() ) : 현재 날짜/시간을 밀리초 반환

















