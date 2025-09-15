package example.day07_re.controller; // 패키지 경로


import example.day07_re.model.dto.BoardDto;
import example.day07_re.service.BoardService;
import lombok.RequiredArgsConstructor;         // RequiredArgsConstructor import
import org.springframework.http.ResponseEntity; // ResponseEntity import
import org.springframework.web.bind.annotation.*; // REST 관련 어노테이션 import

import java.util.List; // List import

// @RestController : 해당 클래스가 REST API 요청을 처리하는 컨트롤러임을 선언
@RestController
// @RequestMapping : 공통 URL 경로 지정 (/board)
@RequestMapping("/board")
// @RequiredArgsConstructor : final 필드를 매개변수로 받는 생성자를 자동 생성 (DI)
@RequiredArgsConstructor
public class BoardController {

    // BoardService 주입 (final 필드 + @RequiredArgsConstructor로 DI)
    private final BoardService boardService;

    // [1] 게시물 등록
    // @PostMapping("") : POST 요청 처리 (localhost:8080/board)
    // @RequestBody : 요청 본문(body)의 JSON 데이터를 BoardDto로 매핑
    @PostMapping("")
    public ResponseEntity<Integer> boardWrite(@RequestBody BoardDto boardDto) {
        System.out.println("BoardController.boardWrite"); // 실행 로그
        System.out.println("boardDto = " + boardDto);     // 입력 데이터 출력
        int result = boardService.boardWrite(boardDto); // 서비스 호출
        return ResponseEntity.ok(result); // ResponseEntity로 결과 반환
    }

    // [2] 전체 게시물 조회
    // @GetMapping("") : GET 요청 처리 (localhost:8080/board)
    @GetMapping("")
    public ResponseEntity<List<BoardDto>> boardPrint() {
        System.out.println("BoardController.boardPrint"); // 실행 로그
        List<BoardDto> result = boardService.boardPrint(); // 서비스 호출
        return ResponseEntity.ok(result); // ResponseEntity로 결과 반환
    }

    // [3] 개별 게시물 조회
    // @GetMapping("/find") : GET 요청 처리 (localhost:8080/board/find?bno=1)
    // @RequestParam : URL 쿼리스트링 파라미터 값 추출
    @GetMapping("/find")
    public ResponseEntity<BoardDto> boardFind(@RequestParam int bno) {
        System.out.println("BoardController.boardFind"); // 실행 로그
        System.out.println("bno = " + bno);              // 입력 파라미터 출력
        BoardDto result = boardService.boardFind(bno);   // 서비스 호출
        return ResponseEntity.ok(result); // ResponseEntity로 결과 반환
    }

    // [4] 게시물 삭제
    // @DeleteMapping("") : DELETE 요청 처리 (localhost:8080/board?bno=3)
    @DeleteMapping("")
    public ResponseEntity<Integer> boardDelete(@RequestParam int bno) {
        System.out.println("BoardController.boardDelete"); // 실행 로그
        System.out.println("bno = " + bno);                // 입력 파라미터 출력
        int result = boardService.boardDelete(bno);    // 서비스 호출
        return ResponseEntity.ok(result); // ResponseEntity로 결과 반환
    }

    // [5] 게시물 수정
    // @PutMapping("") : PUT 요청 처리 (localhost:8080/board)
    // @RequestBody : 요청 본문(body)의 JSON 데이터를 BoardDto로 매핑
    @PutMapping("")
    public ResponseEntity<Integer> boardUpdate(@RequestBody BoardDto boardDto) {
        System.out.println("BoardController.boardUpdate"); // 실행 로그
        System.out.println("boardDto = " + boardDto);       // 입력 데이터 출력
        int result = boardService.boardUpdate(boardDto); // 서비스 호출
        return ResponseEntity.ok(result); // ResponseEntity로 결과 반환
    }

} // CLASS END
