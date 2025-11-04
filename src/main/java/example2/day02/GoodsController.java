package example2.day02;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;
    // 1. 저장
    @PostMapping // { "gname":"키보드", "gprice":35000, "gdesc":"기계식 청축" }
    public ResponseEntity<?> goodsSave( @RequestBody GoodsDto goodsDto  ){
        return ResponseEntity.ok( goodsService.goodsSave( goodsDto ) );
    }
    // 2. 전체조회
    @GetMapping("/list")
    public ResponseEntity<?> goodsAll(){
        return ResponseEntity.ok( goodsService.goodsAll() );
    }

    // 3. 개별조회
    @GetMapping // http://localhost:8080/api/goods?gno=1
    public ResponseEntity<?> goodsGet( @RequestParam int gno ){
        return ResponseEntity.ok( goodsService.goodsGet( gno ) );
    }

    // 4. 개별삭제
    @DeleteMapping
    public ResponseEntity<?> goodsDelete( @RequestParam int gno ){
        return  ResponseEntity.ok( goodsService.goodsDelete( gno ) );
    }
    // 5. 개별수정
    @PutMapping
    public ResponseEntity<?> goodsUpdate( @RequestBody GoodsDto goodsDto ){
        return ResponseEntity.ok( goodsService.goodsUpdate( goodsDto ) );
    }

}











