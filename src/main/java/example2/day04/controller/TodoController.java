package example2.day04.controller;

import example2.day04.service.TodoService;
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
    @GetMapping("/query1") // http://localhost:8080/api/todo/query1?title=책 읽기
    public ResponseEntity<?> query1(
            @RequestParam String title ){
        return ResponseEntity.ok(
                todoService.query1( title ) );
    }

} // class end













