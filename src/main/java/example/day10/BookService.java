package example.day10;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookMapper bookMapper;

    // 1. 책 재고 차감하고 책대여기록 하는데 하나의 흐름으로 구성한다.
    // 즉] 하나라도 SQL 문제가 발생하면 전체 취소한다. 트랜잭션
    @Transactional // 트랜잭션 발동조건 : 실행예외
    public boolean rent( Map<String,Object> body  ){
        // * MAP : 타입변환
        int bookid = Integer.parseInt( String.valueOf(body.get( "bookid"))) ;
        String member = String.valueOf( body.get("member") );
        // 1-1  책 재고 차감
        int result1 = bookMapper.decStock( bookid );
        // 1-2 만약에 책 재고 처리가 실패이면 롤백
        if( result1 == 0 ) throw new RuntimeException("[재고부족]"); //
        // 2-1 책 대여 기록
        int result2 = bookMapper.insertRental( bookid , member );
        // 2-1 책 대여 기록 처리가 실패이면 롤백
        if( result2 == 0 ) throw new RuntimeException("[대여실패]");
        return true;
    }
}








