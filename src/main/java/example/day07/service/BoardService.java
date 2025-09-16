package example.day07.service;

import example.day07.model.dto.BoardDto;
import example.day07.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    // [1] 등록
    public boolean boardWrite(BoardDto boardDto ){
        boolean result = boardMapper.boardWrite( boardDto );
        return result;
    }
    // [2] 전체조회
    public List<BoardDto> boardPrint(){
        List<BoardDto> result = boardMapper.boardPrint();
        return result;
    }
    // [3] 개별조회
    public BoardDto boardFind( int bno ){
        BoardDto result = boardMapper.boardFind( bno );
        return result;
    }

}






