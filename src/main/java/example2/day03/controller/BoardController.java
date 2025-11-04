package example2.day03.controller;

import example2.day03.dto.BoardDto;
import example2.day03.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BoardController {

    private final BoardService boardService;

    /** ✅ 게시물 등록 */
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody BoardDto dto) {
        boolean result = boardService.create(dto);
        return ResponseEntity.ok(result ? "등록 성공" : "등록 실패");
    }

    /** ✅ 게시물 전체 조회 */
    @GetMapping("")
    public ResponseEntity<List<BoardDto>> list() {
        return ResponseEntity.ok(boardService.list());
    }
}
