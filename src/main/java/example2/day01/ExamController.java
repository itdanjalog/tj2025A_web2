package example2.day01;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exam")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;
    // 1. C : 등록
    @PostMapping // http://localhost:8080/api/exam // { "col1": "1",  "col2": "유재석",  "col3": "95.0" }
    public ResponseEntity<?> post(@RequestBody ExamEntity examEntity){
        // (영속 전 / 매핑 전) examEntity
        ExamEntity result = examService.post(examEntity);
        return ResponseEntity.ok( result );
    }
    // 2. R : 전체 조회
    @GetMapping
    public  ResponseEntity<?>  get( ){
        return ResponseEntity.ok( examService.get() );
    }
    // 3. U : 수정
    @PutMapping // { "col1": "1",  "col2": "유재석2",  "col3": "100.0" }
    public  ResponseEntity<?>  put( @RequestBody ExamEntity examEntity){
        boolean result = examService.put2(examEntity);
        return ResponseEntity.ok( result );
    }

    // 4. D : 삭제
    @DeleteMapping //http://localhost:8080/api/exam?col1=1
    public  ResponseEntity<?>  delete( @RequestParam int col1 ){
        boolean result =  examService.delete( col1 );
        return ResponseEntity.ok( result );
    }

}
