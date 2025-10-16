package example.day17;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisTemplate<String, Object> redisTemplate;

    // ✅ 등록 (RequestBody) // { "sno" : 1 , "name" : "유재석" , "kor" : "100", "math" : "90" }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody StudentDto dto) throws Exception {
        String key = "student:" + dto.getSno();
        redisTemplate.opsForValue().set(key,  dto );
        return ResponseEntity.ok().body("✅ 저장 완료: " + dto);
    }

    // ✅ 수정 (RequestBody)
    @PutMapping // { "sno" : 1 , "name" : "유재석2" , "kor" : "100", "math" : "90" }
    public  ResponseEntity<?> update(@RequestBody StudentDto dto) throws Exception {
        String key = "student:" + dto.getSno();
        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.opsForValue().set(key, dto);
            return ResponseEntity.ok().body( "🔄 수정 완료: " + dto ) ;
        }
        return ResponseEntity.ok().body( "❌ 수정 실패: 해당 학생 없음 (sno=" + dto.getSno() + ")" ) ;
    }

    // ✅ 개별 조회 (RequestParam)
    @GetMapping
    public  ResponseEntity<?> getStudent(@RequestParam int sno) {
        String key = "student:" + sno;
        Object value = redisTemplate.opsForValue().get(key);
        return ResponseEntity.ok().body( value != null ? value : "❌ 해당 학생 없음" );
    }

    // ✅ 전체 조회
    @GetMapping("/list")
    public  ResponseEntity<?> getAllStudents() {
        Set<String> keys = redisTemplate.keys("student:*");
        // keys("student:*")	"student:" 로 시작하는 key만 검색
        // keys("*")	Redis의 모든 key 검색 (전체 스캔)
        if (keys == null || keys.isEmpty())  return ResponseEntity.ok().body( List.of("❌ 등록된 학생 없음") ) ;

        List<Object> result = new ArrayList<>();
        for (String key : keys) {
            result.add(redisTemplate.opsForValue().get(key));
        }
        return ResponseEntity.ok().body( result );
    }

    // ✅ 개별 삭제 (RequestParam)
    @DeleteMapping
    public  ResponseEntity<?> deleteStudent(@RequestParam int sno) {
        String key = "student:" + sno;
        Boolean result = redisTemplate.delete(key);
        return ResponseEntity.ok().body( Boolean.TRUE.equals(result)
                ? "🗑️ 삭제 완료: sno=" + sno
                : "❌ 삭제 실패: sno=" + sno + " 존재하지 않음" );
    }

    // ✅ 인증코드 발급 (예: 이메일, 전화번호 기반)
    // 예: /redis/send?phone=01012345678
    @GetMapping("/auth/send")
    public ResponseEntity<?> sendAuthCode(@RequestParam String phone) {
        String key = "auth:" + phone; // 기존 key가 존재하더라도 덮어쓰기(Overwrite) 합니다.

        // 6자리 인증코드 생성
        String code = String.format("%06d", new Random().nextInt(999999));

        // Redis에 저장 (유효기간 3분)
        redisTemplate.opsForValue().set(key, code, Duration.ofSeconds(10));

        return ResponseEntity.ok("📨 인증코드 발급 완료 (1분 유효): " + code);
    }

    // ✅ 인증코드 확인
    // 예: /redis/verify?phone=01012345678&code=123456
    @GetMapping("/auth/verify")
    public ResponseEntity<?> verifyAuthCode(@RequestParam String phone, @RequestParam String code) {
        String key = "auth:" + phone;
        Object savedCode = redisTemplate.opsForValue().get(key);

        if (savedCode == null) {
            return ResponseEntity.ok("⏰ 인증 실패: 코드 만료 또는 존재하지 않음");
        }

        if (savedCode.toString().equals(code)) {
            redisTemplate.delete(key); // 사용 후 삭제
            return ResponseEntity.ok("✅ 인증 성공!");
        } else {
            return ResponseEntity.ok("❌ 인증 실패: 코드 불일치");
        }
    }

    // ✅ 현재 Redis에 저장된 모든 인증코드 목록 조회
    // 예: GET /redis/list
    @GetMapping("/auth/list")
    public ResponseEntity<?> getAllAuthCodes() {
        // "auth:" 로 시작하는 모든 key 검색
        Set<String> keys = redisTemplate.keys("auth:*");

        // Redis에 인증코드가 하나도 없는 경우
        if (keys == null || keys.isEmpty()) {
            return ResponseEntity.ok(List.of("❌ 현재 발급된 인증코드 없음"));
        }

        // 각 key에 대한 value(인증코드)를 가져와 Map 형태로 구성
        List< Object > result = new ArrayList<>();
        for (String key : keys) {
            Map<String, Object> item = new LinkedHashMap<>();

            Object value = redisTemplate.opsForValue().get(key);
            Long ttl = redisTemplate.getExpire(key); // 🔹 남은 시간(초 단위)

            item.put("key", key);
            item.put("code", value);
            item.put("ttl(sec)", ttl); // 남은 시간 표시
            result.add(item);
        }

        // 결과 반환
        return ResponseEntity.ok(result);
    }

    @GetMapping("/reserve") // http://localhost:8080/redis/reserve?seatNo=1&user=유재석
    public ResponseEntity<?> reserveSeat(@RequestParam String seatNo, @RequestParam String user) {
        String key = "reserve:seat:" + seatNo;


        // 이미 예약된 좌석이라면 저장되지 않음 (TTL 2분 동안 Lock 유지)
        boolean locked = Boolean.TRUE.equals(
                redisTemplate.opsForValue().setIfAbsent(key, user, Duration.ofMinutes(2))
        );


        if (!locked) {
            Object currentUser = redisTemplate.opsForValue().get(key);
            return ResponseEntity.ok("⚠️ 이미 예약된 좌석입니다. 예약자: " + currentUser);
        }


        return ResponseEntity.ok("✅ 좌석 예약 성공! 좌석번호: " + seatNo + ", 예약자: " + user);
    }


    @DeleteMapping("/reserve/cancel")
    public ResponseEntity<?> cancelReservation(@RequestParam String seatNo) {
        String key = "reserve:seat:" + seatNo;
        Boolean deleted = redisTemplate.delete(key);
        return ResponseEntity.ok(Boolean.TRUE.equals(deleted)
                ? "🗑️ 예약이 취소되었습니다. (좌석: " + seatNo + ")"
                : "❌ 이미 만료되었거나 존재하지 않는 예약입니다.");
    }




}