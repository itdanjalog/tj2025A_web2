package example2.day03.service;

import example2.day03.dto.BoardDto;
import example2.day03.entity.BoardEntity;
import example2.day03.entity.CategoryEntity;
import example2.day03.repository.BoardRepository;
import example2.day03.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;


    /** ✅ 게시물 등록 */
    public boolean create(BoardDto dto) {
        CategoryEntity category = categoryRepository.findById(dto.getCno())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
        BoardEntity boardEntity = dto.toEntity();
        boardEntity.setCategoryEntity( category );
        boardRepository.save( boardEntity );
        return true;
    }

    /** ✅ 게시물 전체 조회 */
    public List<BoardDto> list() {
        return boardRepository.findAll()
                .stream()
                .map(BoardEntity::toDto)   // ← ✅ Entity 내부 메서드 호출
                .collect(Collectors.toList());
    }
}
