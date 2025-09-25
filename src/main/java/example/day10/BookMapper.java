package example.day10;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BookMapper {

    // [대출] 재고 1 감소 (재고>0 조건)
    @Update("UPDATE books SET stock = stock - 1 WHERE id = #{bookId} AND stock > 0")
    int decStockIfAvailable(@Param("bookId") int bookId);

    // [반납] 재고 1 증가 (도서 존재해야 함)
    @Update("UPDATE books SET stock = stock + 1 WHERE id = #{bookId}")
    int incStockIfExists(@Param("bookId") int bookId);

    // [대출기록] 추가
    @Insert("INSERT INTO rentals (book_id, member) VALUES (#{bookId}, #{member})")
    int insertRental(@Param("bookId") int bookId, @Param("member") String member);

    // [반납처리] 미반납 건만 업데이트
    @Update("UPDATE rentals SET return_date = NOW() " +
            "WHERE book_id = #{bookId} AND member = #{member} AND return_date IS NULL")
    int returnRental(@Param("bookId") int bookId, @Param("member") String member);

}