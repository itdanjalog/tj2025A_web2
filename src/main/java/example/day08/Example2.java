package example.day08;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// [4] AOP 커스텀 ,
@Aspect // AOP 클래스를 스프링 AOP 컨테이너 에 등록 , AOP 설정하겠다는 어노테이션
@Component // AOP 클래스를 스프링 빈(객체) 컨테이너 에 등록
class AopClass{
    // [4-1] 애플리케이션 내 AopService내 모든 메소드가 실행되면 같이 실행
    // @Before("execution( 경로와 조건 )")
    // * : 모든 리턴 타입의 메소드들
    // java 이하경로부터  : 적용할 메소드가 위치한 패키지/파일 경로
        // -> 같은패키지 : AopService
        // -> 다른패키지 : example.day08.AopService
    // .메소드명
        // .* : 해당 클래스내 메소드 에 적용
        // .enter1 : 해당 클래스내 enter1 메소드 에만 적용
    // ( 매개변수 )
        // .(..) : 모든 매개변수를 갖는 메소드만 적용
        // .enter1( int , boolean ) : 지정한 매개변수를 갖는 메소드만 적용
    @Before("execution( * AopService.*(..) )")
    public void check1(){
        System.out.println("@Before[코로나] 열체크");
    }
    // [4-2]
    @After("execution( * AopService.*(..) ) ")
    public void check2(){
        System.out.println("@After[퇴장]");
    }
} // class end

// [3] 간단한 서비스
@Service class AopService{
    public void enter1(){
        System.out.println(">> 학원 입장 <<");
    }
    public void enter2(){
        System.out.println(">> 식당 입장 <<");
    }
}

// [2] 간단한 HTTP 컨트롤러
@RestController class AopController{
    @Autowired AopService aopService;
    @GetMapping("/day08/aop") // http://localhost:8080/day08/aop
    public void method(){
        aopService.enter1(); // 서비스 메소드 호출
        aopService.enter2(); // 서비스 메소드 호출
    }
}

// [1] 스프링 시작 클래스
@SpringBootApplication
public class Example2 {
    public static void main(String[] args) {
        SpringApplication.run( Example2.class );
    }
}




// AOP : 관점지향 프로그래밍
// 부가기능을 하나로 모듈화 해서 핵심비즈니스 로직 분리
// 예] 로그(기록), 트랜잭션(SQL시작/종료), 보안(인증/권한) 등등

// 1. AOP 라이브러리 설치
// implementation 'org.springframework.boot:spring-boot-starter-aop'
