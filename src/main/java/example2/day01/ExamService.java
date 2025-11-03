package example2.day01;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {
    // * 매퍼객체 --> 리포지토리객체
    private final ExamRepository examRepository;
    // 1. C , 등록
    public ExamEntity post( ExamEntity examEntity ){ // 1. 저장할 엔티티를 (매개변수)받는다.
        // 2. .save() , 리포지토리의 저장 메소드 , 저장성공시 성공된 엔티티 반환
        ExamEntity saveEntity
            = examRepository.save( examEntity ); // insert 자동 처리
        // 엔티티객체( 레코드=행 ) 엔티티(테이블)
        return saveEntity;
    }
    // 2. R , 전체조회
    public List<ExamEntity> get(){
        // 1. .findAll() , 리포지토리의 전체조회 메소드 , 모든 엔티티객체를 반환한다.
        List<ExamEntity> entityList
            = examRepository.findAll(); // select 자동 처리
        return entityList;
    }

    // 3. U

    // 4. D

}// class end





