package web2.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web2.model.dto.UserDto;
import web2.service.UserService;

@RestController
@RequestMapping("/api/user") // 공통URL 정의
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 1. 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto userDto ){
       int result = userService.signup( userDto );
       return ResponseEntity.ok( result ); // ok (200 성공의 의미 )
    }

    // 2-1. 로그인(+세션 : 자바웹서버(톰캣)의 임시 저장소 , 한번 로그인 성공했다는 증거 )
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserDto userDto , HttpSession session ){
//        UserDto result = userService.login( userDto );
//        if( result != null ){ // 만약에 로그인 성공 했다면 성공한 유저의 아이디를 세션에 저장
//            session.setAttribute( "loginUser" , result.getUid() );
//        }
//        return ResponseEntity.ok( result );
//    }

    // 2-2. 로그인(+쿠키 : 클라이언트 브라우저 의 임시 저장소 , 세션:서버 / 쿠키:클라이언트  )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto , HttpServletResponse response ){
        UserDto result = userService.login( userDto );
        if( result != null ){ // 만약에 로그인 성공 했다면 성공한 유저의 아이디를 *쿠키*에 저장
            // * 로그인정보를 세션에 저장하면 서버이므로 안전하다. 쿠키(클라이언트) 저장하면 위험하다.
            // Cookie cookie = new Cookie( "쿠키명" , 값 ); // 주로 사용자들의 설정값/임시 저장소
            // response.addCookie( 생성한쿠키 );
            Cookie cookie = new Cookie( "loginUser" , result.getUid() );
            // 클라이언트 에서 해당 쿠키를 노출(탈취) 방지 = 주로 민감한정보는 httpOnly 설정한다.
            cookie.setHttpOnly( true ); // .setHttpOnly( true ) : 무조건 http 에서만 사용. 즉] JS로 접근 불가능
            cookie.setSecure( false ); //  HTTP 암호화 : 단 https(true) 에서만 가능 하므로 테스트 단계에서는 false 한다.
            // + 설정
            cookie.setPath("/"); // 쿠키에 접근할수 있는 경로
            cookie.setMaxAge( 60 * 60 ); // 쿠키의 유효기간(초) , 1시간
            response.addCookie( cookie ); // 생성한 쿠키를 클라이언트에게 반환한다.
        }
        return ResponseEntity.ok( result );
    }


    // 3. 현재 로그인된 정보 호출 ( + 마이페이지 )
    @GetMapping("/info")
    public ResponseEntity<?> myInfo( HttpServletRequest request ){ // 쿠키 활용한 로그인상태를 확인
        // 3-1 : 현재 클라이언트(브라우저) 저장된 모든 쿠키 가져오기
        Cookie[] cookies = request.getCookies();
        // 3-2 : 반복문 이용한 특정한 쿠키명 찾기
        if( cookies != null ){ // 만약에 쿠키들이 존재하면
            for( Cookie c : cookies ){ // 하나씩 쿠키들을 반복하여
                if( c.getName().equals( "loginUser") ){ // "loginUser" 쿠키명과 같다면
                    String uid = c.getValue(); // 쿠키의 저장된 값 반환 ex] uid
                    // 3-3 : 서비스를 호출하여 내정보 조회
                    UserDto result = userService.myInfo( uid );
                    return ResponseEntity.ok( result ); // 로그인 상태로 회원정보 조회
                }
            }
        }
        return ResponseEntity.ok( null ); // 비로그인 상태
    }

    // 4. 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout( HttpServletResponse response ){
        // 4-1 : 삭제할 쿠키명을 null 값으로 변경한다.
        Cookie cookie = new Cookie( "loginUser" , null );
        cookie.setHttpOnly( true );
        cookie.setSecure( false );
        cookie.setPath("/");
        cookie.setMaxAge( 0 ); // 즉시 삭제 하라는 뜻 : 0초
        response.addCookie( cookie ); // 동일한 쿠키명으로 null 저장하면 기존 쿠키명 사라진다.

        // 세션 : 서버에 저장하는 임시 저장소 이므로 서버가 종료되면 사라진다.
        // 쿠키 : 클라이언트에 저장하는 임시 저장소 이므로 서버가 종료되도 유지된다.

        return ResponseEntity.ok( true );
    }
}









