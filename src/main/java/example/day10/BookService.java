package example.day10;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper mapper;

    /** 도서 대출 트랜잭션
     *  1) 재고 1 감소 (재고 0이면 영향행 0 → 예외)
     *  2) rentals insert (실패 시 예외)
     *  한 단계라도 실패하면 전체 롤백
     */
    @Transactional
    public boolean rentBook( Map<String, Object> body ) {
        int bookId = ((Number) body.get("bookId")).intValue();
        String member = String.valueOf(body.get("member"));

        int updated = mapper.decStockIfAvailable(bookId);
        if (updated == 0) {
            throw new RuntimeException("대출 실패: 재고가 없거나 도서가 존재하지 않습니다. bookId=" + bookId);
        }
        int inserted = mapper.insertRental(bookId, member);
        if (inserted == 0) {
            // 여기서 예외가 발생하면 위의 stock 감소도 롤백됨
            throw new RuntimeException("대출 실패: 대출 기록을 추가하지 못했습니다.");
        }
        return true;
    }

    /** 도서 반납 트랜잭션
     *  1) 재고 1 증가 (해당 도서 없음 → 영향행 0 → 예외)
     *  2) 해당 member의 미반납 대출기록 return_date = NOW() (없거나 이미 반납 → 영향행 0 → 예외)
     *  한 단계라도 실패하면 전체 롤백
     */
    @Transactional
    public boolean returnBook( Map<String, Object> body ) {
        int bookId = ((Number) body.get("bookId")).intValue();
        String member = String.valueOf(body.get("member"));

        int updatedBook = mapper.incStockIfExists(bookId);
        if (updatedBook == 0) {
            throw new RuntimeException("반납 실패: 도서가 존재하지 않습니다. bookId=" + bookId);
        }
        int updatedRental = mapper.returnRental(bookId, member);
        if (updatedRental == 0) {
            // 여기서 예외가 발생하면 위의 stock 증가도 롤백됨
            throw new RuntimeException("반납 실패: 미반납 대출기록이 없거나 이미 반납되었습니다.");
        }
        return true;
    }
}
