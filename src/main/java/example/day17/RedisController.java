package example.day17;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.PartialUpdate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    // [*] 간단한 텍스트를 레디스에 접근하는 객체
    private final RedisTemplate redisTemplate; // 템플릿이란? 미리 만들어진 틀/형식

    // [1] 간단한 텍스트를 레디스 서버에 저장 / 호출 하기
    @GetMapping("/test")
    public ResponseEntity<?> test(){
        System.out.println("RedisController.test");
        // [저장] 레디스템플릿객체명.opsForValue().set( key , value ); , key 값은 중복이 안되므로 중복이면 덮여쓰기 적용
        // { "유재석" : "90"  } , { "강호동" : "80" }
        redisTemplate.opsForValue().set( "유재석" , "90" ); // 임의 데이터1
        redisTemplate.opsForValue().set( "강호동" , "80" ); // 임의 데이터2
        redisTemplate.opsForValue().set( "유재석" , "100" ); // key는 중복을 허용하지 않고 , value 중복 허용
        // [ 모든 키 호출] 레디스템플릿객체명.keys("*")         : 레디스에 저장된 모든 키 반환
        // [ 특정한 키의 값 호출] 레디스템플릿객체명.opsForValue().get( key );
        Set< String > keys = redisTemplate.keys("*");
            //  List vs Map vs Set 컬렉션 프레임워크이란?차이?
        List< Object > result = new ArrayList<>(); // 임의의 리스트
        for( String key : keys ){
            result.add( redisTemplate.opsForValue().get( key ) );
        }
        return ResponseEntity.ok( result );
    } // method end

    // day13/day06 CRUD 를 데이터베이스 없이 레디스로 실습 변환

    private final RedisTemplate<String,Object> studentTemplate;
    // 1. 등록
    @PostMapping("") // { "sno" : 1 , "name" : "유재석 " , "kor" : "90" , "math" : "70" }
    private  ResponseEntity<?> save(@RequestBody StudentDto studentDto ){
        // 0. 중복없는 key 구상
        String key = "student:"+studentDto.getSno(); // sno를 key로 조합하여 , 예] student:1 , student:2
        // 1. 레디스에 전달받은 값 저장한다.
        // 예상 : { "student:1" : { sno : 1 , name : "강호동" , math : 80 , kor : 100 } }
        studentTemplate.opsForValue().set( key , studentDto );
        return ResponseEntity.ok().body("[저장성공]");
    }
    // 2. 전체 조회
    @GetMapping("")
    private ResponseEntity<?> findAll(){
        // 0. 조회할 key를 모두 가져온다.  * : 레디스내 모든 키 / xxxx:* : xxxx:동일한 * 자리는 임의의 문자 대응
        // studentTemplate.keys( "문자열*"); // 문자열까지는 동일하면 * 위치는 서로다른 문자열 패턴
        Set<String> keys = studentTemplate.keys("student:*"); // student:1 , student:2 ~~
        // 1. 반복문 이용한 value 꺼내서 리스트에 담기
        List<Object> result = new ArrayList<>();
        for( String key : keys ){ result.add( studentTemplate.opsForValue().get( key ) ); }
        return ResponseEntity.ok().body( result );
    }

    // 3. 개별 조회

    // 4. 개별 삭제

    // 5. 개별 수정



} // class end














