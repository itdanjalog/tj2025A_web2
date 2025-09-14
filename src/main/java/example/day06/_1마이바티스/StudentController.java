package example.day06._1마이바티스;
// 패키지(폴더) 경로. 예: src/main/java/example/day06/_1마이바티스/StudentController.java

import org.springframework.beans.factory.annotation.Autowired;   // 스프링 DI(의존성 주입) 관련 애너테이션 제공
import org.springframework.http.ResponseEntity;               // HTTP 응답 본문과 상태 코드를 함께 다루는 래퍼 타입
import org.springframework.web.bind.annotation.*;            // @RestController, @RequestMapping, @Get/Post/Put/DeleteMapping 등 Web MVC 애너테이션

import java.util.List;                                       // 컬렉션 타입 List
import java.util.Map;                                        // key/value 맵 타입 Map

/**
 * 수업자료용 컨트롤러 개요
 * - 역할: 클라이언트의 HTTP 요청을 받아 서비스 계층(StudentService)로 위임하고, 응답을 HTTP 형태로 반환
 * - URL 규칙: 클래스에 선언한 @RequestMapping("/day04/student")를 공통 prefix로 사용
 * - 반환 타입: 전부 ResponseEntity<...>로 통일 → 상태 코드와 바디를 명확히 제어 가능
 *
 * 엔드포인트 요약
 * [1] POST   /day04/student            : 학생 등록 (JSON 바디 → StudentDto)
 * [2] GET    /day04/student            : 전체 학생 목록 조회 (List<StudentDto>)
 *      GET    /day04/student/find?sno=1 : 단건 조회 (Map<String,Object>)
 * [3] PUT    /day04/student            : 학생 정보 수정 (JSON 바디 → StudentDto)
 * [4] DELETE /day04/student?sno=1      : 학생 삭제 (쿼리스트링 → sno)
 *
 * TIP: 실제 서비스에서는 로깅(println → 로거), 예외 처리(@ControllerAdvice), 검증(@Valid) 등을 함께 구성합니다.
 */
@RestController // @Controller + @ResponseBody 결합: 메소드 반환값을 JSON 등으로 직렬화하여 HTTP 응답 본문에 담아줌
@RequestMapping("/day04/student") // 이 클래스의 모든 핸들러 메소드에 공통으로 붙는 URL 경로 prefix
public class StudentController {

    @Autowired // 스프링 컨테이너가 관리하는 StudentService 빈(객체)을 주입
    private StudentService studentService;
    // TIP: 교육용으로 필드 주입을 사용. 실무에서는 '생성자 주입'을 권장(불변/테스트 용이성↑).

    // ------------------------------ [1] 학생 등록(INSERT) ------------------------------
    @PostMapping("") // HTTP POST: http://localhost:8080/day04/student
    public ResponseEntity<Integer> save(@RequestBody StudentDto studentDto ){
        // @RequestBody: 요청 본문(JSON)을 StudentDto로 변환. 예)
        // {
        //   "name": "유재석",
        //   "kor": 90,
        //   "math": 100
        // }
        System.out.println("StudentController.save");          // 교육용 콘솔 로그
        System.out.println("studentDto = " + studentDto);      // 변환된 DTO 확인

        int result = studentService.save(studentDto);           // 서비스 계층 호출 → DB INSERT 수행(영향 행 수 등 반환 가정)
        return ResponseEntity.status(200).body(result);         // HTTP 200 OK + 결과(예: 1=성공, 0=실패 등)
        // TIP: REST 규약상 신규 생성에는 201 Created와 Location 헤더를 쓰는 것이 이상적이지만, 수업용으로 200 사용.
        //      간단히 쓰려면 ResponseEntity.ok(result)도 가능.
    }

    // ------------------------------ [2] 전체 학생 조회(SELECT ALL) ------------------------------
    @GetMapping("") // HTTP GET: http://localhost:8080/day04/student
    public ResponseEntity<List<StudentDto>> findAll(){
        System.out.println("StudentController.findAll");        // 교육용 콘솔 로그

        List<StudentDto> result = studentService.findAll();     // 서비스 계층 호출 → 전체 목록 조회
        return ResponseEntity.status(200).body(result);         // HTTP 200 OK + List<StudentDto> (비어있으면 [] 리턴)
        // TIP: 목록 응답은 보통 200을 사용. 페이징이 필요하면 page/size 파라미터를 추가 설계.
    }

    // ------------------------------ (선택) 단건 조회(SELECT ONE) ------------------------------
    @GetMapping("/find") // HTTP GET: http://localhost:8080/day04/student/find?sno=1
    public ResponseEntity<Map<String,Object>> find(@RequestParam int sno ){
        // @RequestParam: 쿼리스트링의 sno 값을 int로 바인딩 (예: ?sno=1)
        Map<String,Object> result = studentService.find(sno);   // 서비스 계층 호출 → 단건 조회 (필드 키/값 맵 가정)
        return ResponseEntity.status(200).body(result);         // HTTP 200 OK + 단건 데이터(Map)
        // TIP: 응답 타입은 DTO로 고정하는 것이 타입 안전성/가독성에 좋음. 수업의 유연성을 위해 Map 사용 예시.
        // TIP: 미존재 시 404를 주는 것이 일반적. 여기서는 단순화(200 + 빈 맵) 가정. 실무는 예외/조건 분기 처리.
    }

    // ------------------------------ [3] 학생 정보 수정(UPDATE) ------------------------------
    @PutMapping("")  // HTTP PUT: http://localhost:8080/day04/student
    public ResponseEntity<Integer> update(@RequestBody StudentDto studentDto ){
        // PUT은 '전체 자원 교체' 의미가 일반적이나, 실무에서는 부분 수정을 위해 PATCH를 쓰기도 함.
        // 본 예제에서는 단순화하여 PUT으로 업데이트 처리.
        // 요청 예시)
        // {
        //   "sno": 1,
        //   "name": "유재석",
        //   "kor": 95,
        //   "math": 100
        // }
        System.out.println("StudentController.update");         // 교육용 콘솔 로그
        System.out.println("studentDto = " + studentDto);       // 업데이트할 데이터 확인

        int result = studentService.update(studentDto);         // 서비스 계층 호출 → DB UPDATE 수행(영향 행 수 반환 가정)
        return ResponseEntity.status(200).body(result);         // HTTP 200 OK + 결과(예: 1=성공)
        // TIP: 수정 성공이지만 바디가 필요 없으면 204 No Content도 고려 가능.
    }

    // ------------------------------ [4] 학생 삭제(DELETE) ------------------------------
    @DeleteMapping("") // HTTP DELETE: http://localhost:8080/day04/student?sno=1
    public ResponseEntity<Integer> delete(@RequestParam int sno ){
        // @RequestParam: 쿼리스트링의 sno 값을 int로 바인딩
        System.out.println("StudentController.delete");         // 교육용 콘솔 로그
        System.out.println("sno = " + sno);                     // 삭제 대상 식별자 확인

        int result = studentService.delete(sno);                // 서비스 계층 호출 → DB DELETE 수행(영향 행 수 반환 가정)
        return ResponseEntity.status(200).body(result);         // HTTP 200 OK + 결과(예: 1=성공, 0=대상 없음)
        // TIP: 삭제 성공 시 204 No Content도 자주 사용. 미존재 삭제 시 404 Not Found 처리 권장(실무).
    }

}
