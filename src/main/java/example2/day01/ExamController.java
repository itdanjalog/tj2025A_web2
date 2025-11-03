package example2.day01;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exam")
public class ExamController {

    private final ExamService examService;;

    // 1. C , 등록, http://localhost:8080/api/exam
    // {"col1" : "1" , "col2"  :"유재석" , "col3" : "90.5"  }
    @PostMapping
    public ResponseEntity<?> post( @RequestBody ExamEntity examEntity ){
        return ResponseEntity.ok( examService.post( examEntity ) );
    }

    // 2. R , 전체조회, http://localhost:8080/api/exam
    @GetMapping
    public ResponseEntity<?> get( ){
        return ResponseEntity.ok( examService.get() );
    }

}












