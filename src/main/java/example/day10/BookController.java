package example.day10;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    // 1. { "bookid": 1, "member": "홍길동" }
    @PostMapping("/rent")
    public ResponseEntity<Boolean> rent(
            @RequestBody Map<String,Object> body){

        boolean result = false;
        try {
            result = bookService.rent(body);
            // 만약에 커밋이면
            return ResponseEntity.ok( result );
        } catch (RuntimeException e) {
            // 만약에 롤백이면
            return ResponseEntity.status( 405 ).body( result );
        }
    }
}












