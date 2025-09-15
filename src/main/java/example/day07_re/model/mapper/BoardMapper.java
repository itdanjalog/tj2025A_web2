package example.day07_re.model.mapper; // 패키지 경로

import example.day07_re.model.dto.BoardDto; // DTO import
import org.apache.ibatis.annotations.*;     // MyBatis 어노테이션 import
import java.util.List;                      // List import

// @Mapper : MyBatis 매퍼 인터페이스로 등록
@Mapper
public interface BoardMapper {

    // [1] 등록 (INSERT)
    @Insert("INSERT INTO board(bcontent, bwriter) VALUES(#{bcontent}, #{bwriter})")
    int boardWrite(BoardDto boardDto);
    // 반환타입을 int로 두는게 일반적 → 삽입된 row 개수 (1이면 성공)

    // [2] 전체조회 (SELECT ALL)
    @Select("SELECT * FROM board")
    List<BoardDto> boardPrint();
    // 결과는 자동으로 BoardDto의 필드와 매핑

    // [3] 개별조회 (SELECT ONE)
    @Select("SELECT * FROM board WHERE bno = #{bno}")
    BoardDto boardFind(int bno);
    // 파라미터 bno → SQL의 #{bno}와 매핑

    // [4] 개별삭제 (DELETE)
    @Delete("DELETE FROM board WHERE bno = #{bno}")
    int boardDelete(int bno);
    // 삭제된 row 개수 반환 (1이면 성공)

    // [5] 개별수정 (UPDATE)
    @Update("UPDATE board SET bcontent = #{bcontent} WHERE bno = #{bno}")
    int boardUpdate(BoardDto boardDto);
    // 수정된 row 개수 반환
}
