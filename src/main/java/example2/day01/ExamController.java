package example2.day01;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exam")
public class ExamController {

    private final ExamService examService;
    ;

    // 1. C , 등록, http://localhost:8080/api/exam
    // {"col1" : "1" , "col2"  :"유재석" , "col3" : "90.5"  }
    @PostMapping
    public ResponseEntity<?> post(@RequestBody ExamEntity examEntity) {
        return ResponseEntity.ok(examService.post(examEntity));
    }

    // 2. R , 전체조회, http://localhost:8080/api/exam
    @GetMapping
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(examService.get());
    }

    // 3. U 특정한 엔티티 수정
    @PutMapping
    // { "col1" : "1" , "col2" : "유재석2" , "col3":"100" }
    public ResponseEntity<?> put(
            @RequestBody ExamEntity examEntity) {
        return ResponseEntity
                .ok( examService.put2( examEntity ) );
    }

    // 4. D , 특정한 엔티티 삭제
    @DeleteMapping // http://localhost:8080/api/exam?col1=1
    public ResponseEntity<?> delete(
        @RequestParam int col1 ){
        return ResponseEntity
                .ok( examService.delete( col1 ) );
    }

} // class end












