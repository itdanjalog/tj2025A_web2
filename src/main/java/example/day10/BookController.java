package example.day10;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Log4j2
public class BookController {

    private final BookService service;

    // 1. 도서 대출
    @PostMapping("/rent")
    public ResponseEntity<Boolean> rent(@RequestBody Map<String, Object> body) {
        try {
            log.debug("도서 대출 요청: {}", body);
            boolean result = service.rentBook(body);
            log.debug("도서 대출 성공: {}", body);
            return ResponseEntity.ok(result); // 200 OK + true
        } catch (RuntimeException e) {
            log.debug("도서 대출 실패 - 요청: {}, 에러: {}", body, e.getMessage());
            return ResponseEntity.status(400).body(false); // 400 Bad Request + false
        }
    }

    // 2. 도서 반납
    @PostMapping("/return")
    public ResponseEntity<Boolean> returnBook(@RequestBody Map<String, Object> body) {
        try {
            log.debug("도서 반납 요청: {}", body);
            boolean result = service.returnBook(body);
            log.debug("도서 반납 성공: {}", body);
            return ResponseEntity.ok(result); // 200 OK + true
        } catch (RuntimeException e) {
            log.debug("도서 반납 실패 - 요청: {}, 에러: {}", body, e.getMessage());
            return ResponseEntity.status(400).body(false); // 400 Bad Request + false
        }
    }

    // 3. 로깅 예제
    @GetMapping("/log")
    public void log(){
        log.debug("debug log - 디버깅용 상세 정보");
        log.info("info log - 서비스 흐름, 주요 상태");
        log.warn("warn log - 잠재적 문제");
        log.error("error log - 예외/실패 상황");
    }
}
