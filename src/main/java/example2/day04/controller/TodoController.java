package example2.day04.controller;

import example2.day04.model.dto.TodoDto;
import example2.day04.service.TodoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
@CrossOrigin( value = "*" ) // 리액트/플러터 CORS 허용
public class TodoController {
    private final TodoService todoService;
    // 전체조회
    @GetMapping
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok( todoService.findAll() );
    }
    // 개별삭제
    @DeleteMapping
    public ResponseEntity<?> delete( @RequestParam int id ){
        return ResponseEntity.ok( todoService.delete( id ) );
    }
    // 개별조회
    @GetMapping("/detail")
    public ResponseEntity<?> findById( @RequestParam int id ){
        return ResponseEntity.ok( todoService.findById( id ));
    }
    // 개별수정
    @PutMapping
    public ResponseEntity<?> update(@RequestBody TodoDto todoDto ){
        return ResponseEntity.ok( todoService.update( todoDto) );
    }

    //===============================================================
    // [1] TodoRepository 2-1 , 3-1
    // http://localhost:8080/api/todo/query1?title=책 읽기
    @GetMapping("/query1")
    public ResponseEntity<?> query1( @RequestParam String title ){
        return ResponseEntity.ok( todoService.query1( title ) );
    }

    // [2] TodoRepository 2-2 , 3-2
    // http://localhost:8080/api/todo/query2?title=책 읽기&content=이펙티브 자바 3장 읽기
    @GetMapping("/query2")
    public ResponseEntity<?> query2(
            @RequestParam String title ,
            @RequestParam String content ){
        return ResponseEntity.ok(
                todoService.query2( title , content ) );
    }

    // [3] TodoRepository 2-3 , 3-3
    // http://localhost:8080/api/todo/query3?title=책
    @GetMapping("/query3")
    public ResponseEntity<?> query3(
            @RequestParam String title ){
        return ResponseEntity.ok(
                todoService.query3( title ) );
    }

    // [4] 페이징처리
    @GetMapping("/page") // http://localhost:8080/api/todo/page?page=2&size=5
    public ResponseEntity<?> page(
            @RequestParam(defaultValue = "1") int page , // 조회할 페이지 번호
            @RequestParam(defaultValue = "3") int size){ // 조회 페이지에 조회할 자료의 총 개수
        return ResponseEntity.ok( todoService.page( page,size ) );
    }

    // [5] 2-5  // http://localhost:8080/api/todo/page2?page=1&size=5&keyword=
    @GetMapping("/page2") // http://localhost:8080/api/todo/page2?page=1&size=5&keyword=공부
    public ResponseEntity<?> page2( @RequestParam String keyword , @RequestParam int page , @RequestParam int size ){
        return ResponseEntity.ok( todoService.page2( keyword, page, size ) );
    }


} // class end













