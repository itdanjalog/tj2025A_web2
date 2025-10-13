package example.day13;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        System.out.println("dto = " + dto); // soutp , save 실행전 , sno 없다
        xmlMapper.save( dto );     // ========== SQL 실행 =========
        System.out.println("dto = " + dto); // soutp , save 실행후 , sno 있다.
        return ResponseEntity.ok( dto ); // 제네릭 ? 이므로 모든 자료가 대입된다.
    }

    // # 2. 전체조회
    @GetMapping("")
    public ResponseEntity< ? > findAll( ){
        List< StudentDto > result = xmlMapper.findAll();
        return ResponseEntity.ok( result );
    }

    // 3. 개별 학생 조회
    @GetMapping("/find") // http://localhost:8080/xml/find?sno=1
    public ResponseEntity<?> find( @RequestParam int sno ){
        StudentDto result = xmlMapper.find( sno );
        return ResponseEntity.ok( result );
    }
    // 4. 개별 학생 삭제
    @DeleteMapping("")  // http://localhost:8080/xml?sno=1
    public ResponseEntity< ? > delete(  @RequestParam int sno ){
        int result = xmlMapper.delete( sno );
        return ResponseEntity.ok( result );
    }
    // 5. 개별 학생 (kor/math) 수정
    @PutMapping("") // http://localhost:8080/xml
    // body : { "sno" : "2 " , "kor" : "100" , "math" : "100" }
    public ResponseEntity<?> update( @RequestBody StudentDto studentDto ){
        int result = xmlMapper.update( studentDto );
        return ResponseEntity.ok( result ); // ok == 200
    }

    // ------------------------------------------------------------
    // # 6. [query1] 국어 점수 기준으로 조회
    // 예) GET http://localhost:8080/xml/query1?kor=80
    @GetMapping("/query1")
    public ResponseEntity<?> query1(@RequestParam int kor) {
        List<StudentDto> result = xmlMapper.query1(kor);
        return ResponseEntity.ok(result);
    }

    // # 7. [query2] XML로 정의된 동일한 조회
    // 예) GET http://localhost:8080/xml/query2?kor=90
    @GetMapping("/query2")
    public ResponseEntity<?> query2(@RequestParam int kor) {
        List<StudentDto> result = xmlMapper.query2(kor);
        return ResponseEntity.ok(result);
    }

    // # 8. [query3] 이름(포함된) 또는 수학점수 이상 검색
    // 예) GET http://localhost:8080/xml/query3?name=유&math=80
    @GetMapping("/query3")
    public ResponseEntity<?> query3(@RequestParam(required = false) String name,
                                    @RequestParam(defaultValue = "0") int math) {
        List<StudentDto> result = xmlMapper.query3(name, math);
        return ResponseEntity.ok(result);
    }

    // # 9. [saveAll] 여러 명 등록
    // 예) POST http://localhost:8080/xml/saveAll
    // body: [ {"name":"유재석","kor":90,"math":80}, {"name":"강호동","kor":85,"math":75} ]
    @PostMapping("/saveAll")
    public ResponseEntity<?> saveAll(@RequestBody List<StudentDto> dtos) {
        int result = xmlMapper.saveAll(dtos);
        return ResponseEntity.ok(result);
    }



} // class end







