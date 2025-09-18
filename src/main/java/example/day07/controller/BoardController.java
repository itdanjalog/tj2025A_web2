package example.day07.controller;

import example.day07.model.dto.BoardDto;
import example.day07.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
// @CrossOrigin( value = "허용할주소") // CORS( 서로 다른 서버간의 요청/응답 허용 ) 정책을 설정
@CrossOrigin( value = "http://localhost:5173" ) // 리액트서버 와 CORS 통신을 허용
public class BoardController {
    private final BoardService boardService;

    // [1] 등록
    @PostMapping("") // localhost:8080/board
    public ResponseEntity<Boolean> boardWrite(@RequestBody BoardDto boardDto ){
        boolean result = boardService.boardWrite( boardDto );
        return ResponseEntity.status( 200 ).body( result );
    }

    // [2] 전체조회
    @GetMapping("") // localhost:8080/board
    public ResponseEntity< List<BoardDto> >  boardPrint(){
        List<BoardDto> result = boardService.boardPrint();
        return ResponseEntity.ok().body( result );
    }

    // [3] 개별조회
    @GetMapping("/find") // localhost:8080/board/find?bno=1
    public ResponseEntity< BoardDto > boardFind( @RequestParam int bno ){
        BoardDto result = boardService.boardFind( bno );
        return ResponseEntity.ok().body( result );
    }

    // [4] 개별삭제
    @DeleteMapping("") // localhost:8080/board?bno=3
    public ResponseEntity<Boolean> boardDelete( @RequestParam int bno ){
        boolean result = boardService.boardDelete( bno );
        return ResponseEntity.status(200).body( result );
    }

    // [5] 개별수정
    @PutMapping("") // localhost:8080/board
    public ResponseEntity< Boolean > boardUpdate( @RequestBody BoardDto boardDto ){
        boolean result = boardService.boardUpdate( boardDto );
        return ResponseEntity.ok().body( result );
    }

} // class end
