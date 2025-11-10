package example2.day04.service;

import example2.day04.model.dto.TodoDto;
import example2.day04.model.entity.TodoEntity;
import example2.day04.model.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    // [2]
    public List<TodoDto> query2( String title, String content ){
        // 2-2
        List<TodoEntity> result1 = todoRepository.findByTitleAndContent( title, content );
        System.out.println("result1 = " + result1);
        // 3-2
        List<TodoEntity> result2 = todoRepository.query2( title , content );
        System.out.println("result2 = " + result2);
        // *
        return result2.stream().map( TodoEntity::toDto).collect( Collectors.toList() );
    }
    // [3]
    public List<TodoDto> query3( String title ){
        // 2-3
        List<TodoEntity> result1 = todoRepository.findByTitleContaining( title );
        System.out.println("result1 = " + result1);
        // 3-3
        List<TodoEntity> result2 = todoRepository.query3( title );
        System.out.println("result2 = " + result2);
        // *
        return result2.stream().map( TodoEntity::toDto).collect( Collectors.toList());
    }

    // [4] import org.springframework.data.domain.Page;
    public Page<TodoDto> page( int page , int size ){
        // 4-1 : 페이징처리 옵션 설정한다.
        // PageRequest.of( 조회할페이지번호 , 조회할페이지당데이터수, Sort.by( Sort.Direction.ASC , "정렬속성명") );
        PageRequest pageRequest = PageRequest.of( page-1 , size , Sort.by( Sort.Direction.DESC , "id" ) );
            // page-1 : JPA는 페이지번호를 0부터 시작함으로 1페이지가 0 이고 2페이지가 1 으로 처리됨에 -1
            // size : 현재 (한)페이지에 조회할 자료 개수 , 1페이지에서 3개 조회
            // Sort.by( Sort.Direction.DESC , id ) : 'id' 속성(컬럼) 내림차순 정렬 , order by id desc
        // 4-2 : 조회한다, .findAll( 페이징객체 ); , Page< >타입으로 반환된다. <---> List 타입 동일
        Page<TodoEntity> result = todoRepository.findAll( pageRequest );
            // Page : 페이징 처리 결과를 담는 인터페이스 타입 , 다양한 페이징 결과 속성 제공한다.
        // 4-3 : 조회 결과 반환 , Page 타입은 *스트림*을 기본적으로 제공한다.
        return result.map( TodoEntity::toDto );
    }


} // class end


























