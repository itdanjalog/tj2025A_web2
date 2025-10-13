package example.day13;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/xml")
@RequiredArgsConstructor
public class XmlController {
    // #
    private final XmlMapper xmlMapper;

    // # 1. 등록 , { "name" : "유재석 " , "kor" : "90" , "math" : "70" }
    @PostMapping("") // http://localhost:8080/xml
    public ResponseEntity< ? > save(@RequestBody StudentDto dto ){
        // < ? > : 제네릭타입에 ? 넣으면 모든 타입을 지칭 한다. 와일드카드
        xmlMapper.save( dto );
        return ResponseEntity.ok( true ); // 제네릭 ? 이므로 모든 자료가 대입된다.
    }

    // # 2. 전체조회
    @GetMapping("")
    public ResponseEntity< ? > findAll( ){
        List< StudentDto > result = xmlMapper.findAll();
        return ResponseEntity.ok( result );
    }

} // class end







