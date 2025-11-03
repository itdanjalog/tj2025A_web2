package example2.day02;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    /** ✅ 상품 등록 */
    @PostMapping // { "gname":"키보드", "gprice":35000, "gdesc":"기계식 청축" }
    public ResponseEntity<?> create(@RequestBody GoodsDto dto) {
        return ResponseEntity.ok( goodsService.create(dto) );
    }

    /** ✅ 전체 상품 조회 */
    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok( goodsService.findAll()  );
    }

    /** ✅ 상품 상세 조회 */
    @GetMapping // ?gno=1
    public ResponseEntity<?> read(@RequestParam int gno) {
        return ResponseEntity.ok( goodsService.findById(gno)  );
    }

    /** ✅ 상품 수정 */
    @PutMapping // { "gno" : "1", "gname":"키보드 PRO", "gprice":45000, "gdesc":"광축 모델" }
    public ResponseEntity<?> update( @RequestBody GoodsDto dto) {
        return ResponseEntity.ok( goodsService.update( dto)  );
    }

    /** ✅ 상품 삭제 */
    @DeleteMapping // ?gno=1
    public ResponseEntity<?> delete(@RequestParam int gno) {
        return ResponseEntity.ok( goodsService.delete(gno)  );
    }
}