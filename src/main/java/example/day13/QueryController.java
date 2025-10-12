package example.day13;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
public class QueryController {

    private final QueryMapper queryMapper;

    // [1] 등록
    @PostMapping("") // post , http://localhost:8080/query  , { "name" : "유재석" , "kor" : "80" , "math" : "100" }
    public ResponseEntity<?> save(@RequestBody StudentDto map ){
        System.out.println("StudentController.save");
        System.out.println("map = " + map);

        queryMapper.save( map );

        System.out.println("map = " + map);

        return ResponseEntity.ok( map.getSno() );
    }


    // [2] 전체조회
    @GetMapping("") // get , http://localhost:8080/query
    public ResponseEntity<?> findAll(){
        System.out.println("StudentController.findAll");
        List<StudentDto> result = queryMapper.findAll();
        return  ResponseEntity.ok( result );
    }


//    // [3] 수정
//    @PutMapping("") // put , http://localhost:8080/day05/students ,  {  "sno" : "1" ,  "kor" : "10" , "math" : "20" }
//    public int update( @RequestBody Map<String, Object> map ){
//        System.out.println("StudentController.update");
//        System.out.println("map = " + map);
//        return queryMapper.update( map );
//    }
//    // [4] 삭제
//    @DeleteMapping("") // delete , http://localhost:8080/day05/students?sno=4 ,
//    public boolean delete( @RequestParam int sno ){
//        System.out.println("StudentController.delete");
//        System.out.println("sno = " + sno);
//        return queryMapper.delete( sno );
//    }

    // [5] 특정 점수 이상 학생 조회
    @GetMapping("/find") // get , http://localhost:8080/query/find?minKor=80&minMath=80
    public  ResponseEntity<?>  findStudentScores(
            @RequestParam int minKor ,
            @RequestParam int minMath ){
        System.out.println("StudentController.findStudentScores");
        System.out.println("minKor = " + minKor + ", minMath = " + minMath);
        List< StudentDto >  result =  queryMapper.findStudentScores( minKor , minMath);
        return ResponseEntity.ok( result );
    }

    // [6] 여러명의 학생 등록하기
    @PostMapping("/save")
    // post , http://localhost:8080/query/save
    // body : [ { "name" : "유재석" , "kor" : "100", "math" : "90" } ,{ "name" : "유재석2" , "kor" : "50", "math" : "40" } ]
    public  ResponseEntity<?>  saveAll( @RequestBody List< StudentDto > list ){
        System.out.println("StudentController.saveAll");
        System.out.println("list = " + list);
        int result = queryMapper.saveAll( list );
        System.out.println("list = " + list);
        return ResponseEntity.ok( result );
    }
    /*
        [ JS(fetch/axios) / TalendApi ] ----------- HTTP -------------------->  [JAVA]
                ( JSON 알고있음 )                   ( JSON 알고있음 )              ( JSON 몰라 / 타입변환  )
                body : { }                                                      DTO , MAP
                body : [ ]                                                      List
     */



    // [7] 정렬 + 제한 조회
    @GetMapping("/findAll") // GET , http://localhost:8080/query/findAll?orderBy=kor&limit=2
    public  ResponseEntity<?>  findAllOrderByLimit(
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        System.out.println("StudentController.findAllOrderByLimit");
        System.out.println("orderBy = " + orderBy + ", limit = " + limit);
        List< StudentDto > result = queryMapper.findAllOrderByLimit(orderBy, limit);
        return ResponseEntity.ok( result );
    }

}