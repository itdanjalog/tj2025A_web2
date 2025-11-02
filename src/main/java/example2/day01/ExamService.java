package example2.day01;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamService {

    // 1. 조작할 엔티티리포지토리의 인터페이스
    private final ExamRepository examRepository;

    // - 등록
    public ExamEntity post(ExamEntity examEntity){
        // 2. 현재 엔티티를 저장하기 .save()
        ExamEntity resultEntity = examRepository.save(examEntity);
        // tableEntity : 영속 전 객체
        // resultEntity : 영속 된 객체 , 매핑
        return resultEntity;
    }
    // - 전체조회
    public List<ExamEntity> get(){
        // 3. 모든 엔티티를 리스트로 반환 , .findAll()
        List<ExamEntity> examEntityList = examRepository.findAll();
        return examEntityList;
    }
    // - 수정
    public ExamEntity put(ExamEntity examEntity){
        // 4. 현재 엔티티의 ID가 존재하면 UPDATE / 없으면 INSERT , .save()
        ExamEntity resultEntity = examRepository.save(examEntity);
        return resultEntity;
    }
    // - 수정 : 존재하는 ID만 수정 , .findById( pk값 )
    @Transactional // 아래 메소드 에서 하나라도 sql 문제발생하면 전체 취소
    public boolean put2( ExamEntity examEntity){
        // 1. id 해당하는 엔티티 찾기
        Optional<ExamEntity> optionalExamEntity = examRepository.findById( examEntity.getCol1() );
        // 2. 만약에 조회한 엔티티가 있으면 .isPresent()
        if( optionalExamEntity.isPresent() ){
            // 3. Optional 객체에서 (영속된)엔티티 꺼내기
            ExamEntity entity = optionalExamEntity.get();
            entity.setCol2( examEntity.getCol2() );
            entity.setCol3( examEntity.getCol3() );
            return true;
        }
        return false;
    }

    // 삭제
    public boolean delete( int col1 ){
        examRepository.deleteById( col1 );
        //tableRepository.delete( entity );
        //tableRepository.deleteAll();
        System.out.println( examRepository.count() );
        System.out.println( examRepository.existsById( col1 ));
        return true;
    }

}
