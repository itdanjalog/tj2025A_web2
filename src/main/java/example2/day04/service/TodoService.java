package example2.day04.service;

import example2.day04.model.dto.TodoDto;
import example2.day04.model.entity.TodoEntity;
import example2.day04.model.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;

    // [1] TodoRepository 2-1 , 3-1
    public List<TodoDto> query1( String title ){
        // 2-1 내가 만든 명명 규칙 사용한 쿼리 메소드
        List<TodoEntity> result1 =
                todoRepository.findByTitle( title );
        System.out.println("result1 = " + result1);
        // 3-1 내가 만든 네이티브 쿼리 메소드
        List<TodoEntity> result2 =
                todoRepository.query1( title );
        System.out.println("result2 = " + result2);
        // *
        return result2.stream()
                .map( TodoEntity::toDto)
                .collect( Collectors.toList() );
    }

}














