package example.day07.model.mapper;

import example.day07.model.dto.BoardDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {

    // [1] 등록
    @Insert("insert into board( bcontent, bwriter ) values( #{ bcontent } , #{ bwriter } )")
    public boolean boardWrite(BoardDto boardDto );

    // [2] 전체조회
    @Select("select * from board")
    public List<BoardDto> boardPrint();

    // [3] 개별조회
    @Select("select * from board where bno = #{ bno } ")
    public BoardDto boardFind( int bno );

    // [4] 개별삭제
    @Delete("delete from board where bno = #{ bno } ")
    public boolean boardDelete( int bno );

    // [5] 개별수정
    @Update("update board set bcontent = #{ bcontent } where bno = #{ bno } ")
    public boolean boardUpdate( BoardDto boardDto );


} // class end









