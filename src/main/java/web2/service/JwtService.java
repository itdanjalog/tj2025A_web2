//package web2.util;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Date;
//import java.util.concurrent.TimeUnit;
//
//@Component // Spring 컨테이너에 빈 등록
//public class JwtUtil {
//
//    // 비밀키 알고리즘 : HS256알고리즘 , HS512알고리즘
//    // private String secretKey = "인코딩된 HS512 비트 키";
//    // (1) 개발자 임의로 지정한 키 : private String secretKey = "2C68318E352971113645CBC72861E1EC23F48D5BAA5F9B405FED9DDDCA893EB4";
//
//    //  256비트(32글자) 이상의 문자열을 사용
////    private static final String SECRET = "MySecretKeyForJWT_ChangeThisToLongerString123!@#";
////    private final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
//
//    private static final String SECRET = "MySecretKeyForJWT_ChangeThisToLongerString123!@#";
//    private final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
//
//    // (2) 라이브러리 이용한 임의(랜덤) 키 :
//    // import java.security.Key;
//    // Keys.secretKeyFor( SignatureAlgorithm.알고리즘명 );
//    // private Key secretKey = Keys.secretKeyFor( SignatureAlgorithm.HS256 );
//
//    // [1] JWT 토큰 발급 , 사용자의 이메일을 받아서 토큰 만들기
//    public String createToken( String memail ){
//        //return Jwts.builder()
//        String token = Jwts.builder() // +해당 반환된 토큰을 변수에 저장
//                .setSubject(memail) // 토큰에 넣을 내용물 , 로그인 성공한 회원의 이메일을 넣는다.
//                .setIssuedAt(new Date())  // 토큰이 발급된 날짜 , new Date() : 자바에서 제공하는 현재날짜 클래스
//                // 토큰 만료시간 , 밀리초(1000/1) , new Date( System.currentTimeMillis() ) : 현재시간의 밀리초
//                // new Date( System.currentTimeMillis() + ( 1000 * 초 * 분 * 시 ) )
//                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60)))  // 1시간 , 1일 의 토큰 유지기간
//                .signWith(secretKey) // 지정한 비밀키 로 암호화 한다.
//                .compact(); // 위 정보로 JWT 토큰 생성하고 반환한다.
//        // + 중복 로그인 방지 하고자 웹서버 가 아닌 Redis 에 토큰 정보 저장 ( 분산 처리 , MSA구축 , AI 속도 등등  )
//        // (1) Redis에 토큰 저장하기.  .opsForValue().set(key , value ); , .opsForValue().set( 계정식별정보 , 토큰 );
//        // stringRedisTemplate.opsForValue().set("JWT:"+memail , token , 24 , TimeUnit.HOURS ); // 토큰 유지시간 과 일치
//        // (2) 현재 Redis에 저장된 key 들을 확인 , .keys("*") : 현재 redis의 저장된 모든 key 반환
//        //System.out.println( stringRedisTemplate.keys("*") );
//        // (3) 현재 Reids에 저장된 특정한 key의 값 확인 .opsForValue().get( key );
//        //System.out.println( stringRedisTemplate.opsForValue().get("JWT:"+memail) );
//        System.out.println("token = " + token);
//        return token;
//    } // f end
//
//    // [2] JWT 토큰 검증
//    public String validateToken( String token  ){
//        try{
//            Claims claims = Jwts.parser() // 1. parser() : JWT토큰 검증하기 위한함수
//                    .setSigningKey( secretKey ) // 2.  .setSigningKey( 비밀키 ) : 검증에 필요한 비밀키 지정.
//                    .build() // 3. 검증을 실행할 객체 생성 ,
//                    .parseClaimsJws( token ) // 4. 검증에 사용할 토큰 지정
//                    .getBody(); // 5. 검증된 (claims) 객체 생성후 반환
//            // claims 안에는 다양한 토큰 정보 들어있다.
//            System.out.println( claims.getSubject() ); // 토큰에 저장된 (로그인된)회원이메일
//
//            // + 중복 로그인 방지 하고자 Redis 에서 최근 로그인된 토큰 확인
//            String memail = claims.getSubject(); // + 현재 전달받은 토큰의 저장된 회원정보(이메일)
//            // (1) 레디스에서 최신 토큰 가져오기
//            // String redisToken = stringRedisTemplate.opsForValue().get("JWT:"+memail);
//            // (2) 현재 전달받은 토큰과 레디스에 저장된 토큰 비교  , 두 토큰이 같으면
//            //if( token.equals( redisToken ) ){ return  memail; } // 현재 로그인상태 정상(중복 로그인이 아니다.)
//            // (3) 만약에 두 토큰이 다르면 아래 코드에 null이 리턴된다. ( 토큰 불일치 또는 중복 로그인 감지 )
//            //else{  System.out.println(" >> 중복 로그인 감지 또는 토큰 없음");  }
//            System.out.println("memail = " + memail);
//            return memail;
//        }catch ( JwtException e ){
//            // 그외 모든 토큰 예외 클래스
//            System.out.println(" >> JWT 예외 : " + e );
//        }
//        return null;// 유효하지 않은 토큰 또는 오류 발생시 null 반환
//    }
//
//    // [3] 로그아웃 시 redis에 저장된 토큰 삭제 서비스
////    public void deleteToken( String memail ){
////        stringRedisTemplate.delete( "JWT:"+memail );
////    }
//
//
//    public String generateToken(String username, String role) {
//        return Jwts.builder()
//                .claim("username", username)
//                .claim("role", role)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
//                .signWith(SignatureAlgorithm.HS256, SECRET)
//                .compact();
//    }
//
//    public String getUrole(String token) {
//        return getClaims(token).get("role", String.class);
//    }
//
//    public String getUid(String token) {
//        return getClaims(token).get("role", String.class);
//    }
//
//
//} // class end

//
//
//package web2.util;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    // ✅ 비밀키 (256비트 이상 문자열)
//    private static final String SECRET = "MySecretKeyForJWT_ChangeThisToLongerString123!@#";
//    private final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
//
//    // =====================================================================================
//    // ✅ 1. 토큰 생성 (username + role)
//    // =====================================================================================
//    public String generateToken(String uid, String urole) {
//        return Jwts.builder()
//                .claim("uid", uid)
//                .claim("urole", urole)
//                .setIssuedAt(new Date()) // 발급 시간
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간 유효
//                .signWith(secretKey, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    // =====================================================================================
//    // ✅ 2. 토큰 유효성 검증
//    // =====================================================================================
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (ExpiredJwtException e) {
//            System.out.println("❌ 토큰 만료: " + e.getMessage());
//        } catch (JwtException e) {
//            System.out.println("❌ 유효하지 않은 토큰: " + e.getMessage());
//        }
//        return false;
//    }
//
//    // =====================================================================================
//    // ✅ 3. 토큰에서 정보 추출
//    // =====================================================================================
//    private Claims getClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public String getUid(String token) {
//        return getClaims(token).get("uid", String.class);
//    }
//
//    public String getUrole(String token) {
//        return getClaims(token).get("urole", String.class);
//    }
//
//}



package web2.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

// 해당 클래스를 스프링이 자동으로 감지하고 빈(Bean)으로 등록하도록 지정
// => SecurityConfig 등에서 @Autowired 또는 생성자 주입으로 사용 가능
@Component
public class JwtService {

    // ==========================================================================================================
    // ✅ JWT 서명(Signature)에 사용할 비밀키 정의
    // - JWT의 서명(Signature) 부분을 생성할 때 사용되는 개인키(서버만 알고 있어야 함)
    // - 최소 256bit(=32자 이상) 이상의 문자열이어야 안전함
    // ==========================================================================================================
    private static final String SECRET = "123456789123456789123456789123456789";

    // HMAC-SHA 알고리즘에 사용할 수 있도록 비밀키를 Key 객체로 변환
    // Keys.hmacShaKeyFor()는 jjwt 라이브러리 제공 메서드로, HMAC-SHA용 SecretKey를 생성해줌
    private final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));


//    @Value("${jwt.secret}")
//    private String secret; // ① 문자열로 먼저 주입받음
//
//    private Key secretKey; // ② 변환 후 저장할 필드
//
//    @PostConstruct
//    public void init() { // ③ 빈 초기화 이후 실행
//        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
//    }

    // =====================================================================================
    // ✅ 1. 토큰 생성 메소드 : generateToken(String uid, String urole)
    // =====================================================================================
    // - 로그인 성공 시 사용자 ID(uid)와 권한(urole)을 포함한 JWT 토큰을 발급함
    // - JWT 구조: Header + Payload(클레임) + Signature
    // - 결과: 문자열 형태의 JWT 토큰 반환
    // =====================================================================================
    public String generateToken(String uid , String urole ) {
        // 콘솔 출력 (로그용)
        System.out.println("uid = " + uid + ", urole = " + urole);

        // JJWT 라이브러리의 빌더 패턴을 이용해 토큰 구성
        String token = Jwts.builder()
                // Payload 부분에 커스텀 데이터(claim) 추가
                // uid = 사용자 아이디
                .claim("uid", uid)
                // urole = 사용자 권한 (예: ROLE_USER, ROLE_ADMIN)
                .claim("urole" , urole)

                // 토큰 발급 시간 (iat: issued at)
                .setIssuedAt(new Date())

                // 토큰 만료 시간 (exp: expiration)
                // 현재 시각 + 1시간 (1000ms * 60초 * 60분)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))

                // 서명(Signature) 생성 : secretKey + HS256 알고리즘 사용
                .signWith(secretKey, SignatureAlgorithm.HS256)

                // 최종 문자열 형태로 직렬화(compact) → 완성된 JWT 문자열 생성
                .compact();

        // 생성된 토큰 로그 출력
        System.out.println("token = " + token);

        // 최종 반환
        return token;
    }

    // =====================================================================================
    // ✅ 2. 토큰 유효성 검증 메소드 : validateToken(String token)
    // =====================================================================================
    // - 클라이언트가 보낸 토큰이 위조되었는지, 만료되지 않았는지 검사
    // - 유효하면 true, 문제 있으면 false 반환
    // =====================================================================================
    public boolean validateToken(String token) {
        try {
            // 토큰 파서(parser) 생성 후 검증 실행
            Jwts.parser()
                    .setSigningKey(secretKey)  // 서명 검증 시 사용할 비밀키 지정
                    .build()
                    .parseClaimsJws(token);    // 토큰 파싱 및 유효성 검증 수행
            return true; // 정상적인 토큰이면 true 반환
        } catch (JwtException e) {
            // JWT 검증 중 오류 발생 시 (만료, 위조, 형식 오류 등)
            System.out.println("❌ 유효하지 않은 토큰: " + e.getMessage());
        }
        // 검증 실패 시 false 반환
        return false;
    }

    // =====================================================================================
    // ✅ 3. 토큰에서 정보(클레임) 추출 : getClaims(String token)
    // =====================================================================================
    // - 토큰이 유효하다고 가정했을 때, 내부의 Payload(Claims) 영역에서 데이터 추출
    // =====================================================================================
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)  // 서명키 설정
                .build()
                .parseClaimsJws(token)     // 토큰 파싱 후 JWS 객체로 변환
                .getBody();                // Payload 부분(Claims)만 추출
    }

    // =====================================================================================
    // ✅ 4. 토큰에서 uid 값(사용자 아이디) 추출
    // =====================================================================================
    public String getUid(String token) {
        // getClaims() 호출 후 uid 키로 값 반환
        return getClaims(token).get("uid", String.class);
    }

    // =====================================================================================
    // ✅ 5. 토큰에서 urole 값(사용자 권한) 추출
    // =====================================================================================
    public String getUrole(String token) {
        // getClaims() 호출 후 urole 키로 값 반환
        return getClaims(token).get("urole", String.class);
    }


    // ====================================================================
    // ✅ (1) 간단한 토큰 생성 메소드 : 토큰생성(String value)
    // - 입력받은 문자열(value)을 클레임으로 저장한 토큰 생성
    // ====================================================================
    public String 토큰생성(String value) {

        String token = Jwts.builder()
                .claim("value", value)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 )) // 1분
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    // ====================================================================
    // ✅ (2) 토큰 검증 메소드 : 토큰검증(String token)
    // - 토큰이 위조 또는 만료되었는지 검사
    // ====================================================================
    public boolean 토큰검증(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // ====================================================================
    // ✅ (3) 토큰 값 추출 메소드 : 값추출(String token)
    // - 토큰 내부 클레임(value) 값 반환
    // ====================================================================
    public String 값추출(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String value = claims.get("value", String.class);
            return value;

        } catch (JwtException e) {
            return null;
        }
    }


}

// 종류	대칭키 (서버가 발급·검증 모두 수행)
//복호화 가능 여부	❌ Payload는 Base64 인코딩이므로 누구나 볼 수 있으나, 서명(Signature)은 복호화 불가
//보안 핵심	서버의 secretKey가 외부에 노출되지 않아야 함