package example2.day04.controller;

import example2.day04.service.TodoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;

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

} // class end













