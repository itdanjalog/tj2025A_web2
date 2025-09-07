package example.day04._2응답객체;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task/day04")
public class ResponseController {

    // Boolean 반환 // http://localhost:8080/task/day04/bool?flag=false
    @GetMapping("/bool")
    public ResponseEntity<Boolean> getBoolean(@RequestParam(defaultValue = "true") boolean flag) {
        if (!flag) {
            return ResponseEntity.status(400).body(false); // 잘못된 요청
        }
        return ResponseEntity.status(200).body(true);
    }

    // Integer 반환 // http://localhost:8080/task/day04/int?value=-1
    @GetMapping("/int")
    public ResponseEntity<Integer> getInteger(@RequestParam(defaultValue = "0") int value) {
        if (value < 0) {
            return ResponseEntity.status(400).body(null); // 음수는 잘못된 요청
        }
        return ResponseEntity.status(201).body(value);
    }

    // String 반환 // http://localhost:8080/task/day04/string?name=
    @GetMapping("/string")
    public ResponseEntity<String> getString(@RequestParam(required = false) String name) {
        if (name == null || name.isBlank()) {
            return ResponseEntity.status(401).body("이름이 필요합니다."); // 인증 실패 비슷한 예제
        }
        return ResponseEntity.status(200).body("Hello " + name);
    }

    // DTO 반환 // http://localhost:8080/task/day04/member?id=-1
    @GetMapping("/member")
    public ResponseEntity<MemberDto> getMember(@RequestParam(defaultValue = "1") int id) {
        if (id <= 0) {
            return ResponseEntity.status(400).body(null); // 잘못된 요청
        }

        MemberDto dto = MemberDto.builder()
                .id(id)
                .name("홍길동")
                .email("hong@test.com")
                .build();

        return ResponseEntity.status(200).body(dto);
    }

    // Map 반환 // http://localhost:8080/task/day04/map?status=error
    @GetMapping("/map")
    public ResponseEntity<Map<String, Object>> getMap(@RequestParam(defaultValue = "ok") String status) {
        Map<String, Object> result = new HashMap<>();
        if ("error".equalsIgnoreCase(status)) {
            result.put("code", 500);
            result.put("msg", "서버 오류 발생");
            return ResponseEntity.status(500).body(result);
        }
        result.put("code", 200);
        result.put("msg", "success");
        return ResponseEntity.status(200).body(result);
    }

    // List<DTO> 반환 // http://localhost:8080/task/day04/member-list?count=-1
    @GetMapping("/member-list")
    public ResponseEntity<List<MemberDto>> getMemberList(@RequestParam(defaultValue = "2") int count) {
        if (count <= 0) {
            return ResponseEntity.status(400).body(null); // 잘못된 요청
        }

        List<MemberDto> list = new ArrayList<>();
        list.add(MemberDto.builder()
                .id(1).name("홍길동").email("hong@test.com").build());
        list.add(MemberDto.builder()
                .id(2).name("김철수").email("kim@test.com").build());

        return ResponseEntity.status(200).body(list);
    }

    // List<Map> 반환 // http://localhost:8080/task/day04/map-list?status=forbidden
    @GetMapping("/map-list")
    public ResponseEntity<List<Map<String, Object>>> getMapList(@RequestParam(defaultValue = "ok") String status) {
        if ("forbidden".equalsIgnoreCase(status)) {
            return ResponseEntity.status(403).body(null); // 접근 권한 없음
        }

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 1);
        map1.put("name", "Item1");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 2);
        map2.put("name", "Item2");

        list.add(map1);
        list.add(map2);

        return ResponseEntity.status(200).body(list);
    }

    // Void (본문 없음, 상태코드만 반환) // http://localhost:8080/task/day04/delete?success=false
    @GetMapping("/delete")
    public ResponseEntity<Void> deleteTask(@RequestParam(defaultValue = "true") boolean success) {
        if (!success) {
            return ResponseEntity.status(400).build(); // 삭제 실패
        }
        return ResponseEntity.status(204).build(); // 삭제 성공
    }

}