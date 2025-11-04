package example2.day02;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/goods")
@RequiredArgsConstructor
public class GoodsController {
    private final GoodsService goodsService;
    // 1. 저장
    @PostMapping
    public ResponseEntity<?> goodsSave( @RequestBody GoodsDto goodsDto  ){
        return ResponseEntity.ok( goodsService.goodsSave( goodsDto ) );
    }

    // 2. 전체조회

    // 3. 개별조회

    // 4. 개별삭제

    // 5. 개별수정

}











