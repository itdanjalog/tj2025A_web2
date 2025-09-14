package example.day06._1마이바티스;
// 패키지 경로: src/main/java/example/day06/_1마이바티스/StudentService.java

import org.springframework.beans.factory.annotation.Autowired;   // 스프링 DI(의존성 주입) 관련 애너테이션
import org.springframework.stereotype.Service;                 // @Service: 비즈니스 로직 계층을 나타내는 애너테이션

import java.util.List;
import java.util.Map;

/**
 * StudentService 클래스
 * - 역할: 컨트롤러와 매퍼(DAO, Mapper) 사이에서 '비즈니스 로직'을 담당하는 계층
 * - 현재 예제에서는 단순히 Mapper를 호출하는 수준
 * - 추후: 유효성 검증, 트랜잭션 처리, 예외 처리 등을 추가할 수 있음
 *
 * 주요 메소드 요약
 * [1] save()    : 학생 등록
 * [2] findAll() : 전체 학생 목록 조회
 *     find()    : 단일 학생 조회
 * [3] update()  : 학생 정보 수정
 * [4] delete()  : 학생 삭제
 */
@Service // 스프링이 관리하는 Service 계층 빈으로 등록
public class StudentService {

    @Autowired
    private StudentMapper studentMapper; // 의존성 주입: 실제 DB 작업을 처리하는 MyBatis 매퍼 인터페이스

    // ------------------------------ [1] 학생 등록(INSERT) ------------------------------
    public int save(StudentDto studentDto ){
        // 매개변수: StudentDto (클라이언트 요청 데이터)
        // 반환값: int (DB에 삽입된 행 수, 1=성공 / 0=실패)
        return studentMapper.save(studentDto);
        // TIP: 여기서 추가 검증 로직(예: 이름 중복 체크)을 넣을 수 있음.
    }

    // ------------------------------ [2] 전체 학생 조회(SELECT ALL) ------------------------------
    public List<StudentDto> findAll(){
        // 반환값: List<StudentDto> (조회된 학생 전체 목록)
        return studentMapper.findAll();
        // TIP: 페이징 처리(page, size) 필요 시 Mapper 메소드에 파라미터 추가
    }

    // ------------------------------ (선택) 단일 학생 조회(SELECT ONE) ------------------------------
    public Map<String,Object> find(int sno ){
        // 매개변수: sno (학생 번호, 기본키)
        // 반환값: Map<String,Object> (학생 1명의 데이터: key=컬럼명, value=값)
        return studentMapper.find(sno);
        // TIP: DTO를 반환하면 더 타입 안정적. Map은 유연하지만 키 오타 시 문제 가능.
    }

    // ------------------------------ [3] 학생 정보 수정(UPDATE) ------------------------------
    public int update(StudentDto studentDto ){
        // 매개변수: StudentDto (수정할 학생 정보 포함)
        // 반환값: int (업데이트된 행 수, 1=성공 / 0=대상 없음)
        return studentMapper.update(studentDto);
        // TIP: 부분 수정 시에는 null 필드 무시 로직, PATCH 방식을 고려할 수 있음.
    }

    // ------------------------------ [4] 학생 삭제(DELETE) ------------------------------
    public int delete(int sno ){
        // 매개변수: id (삭제할 학생의 기본키)
        // 반환값: int (삭제된 행 수, 1=성공 / 0=대상 없음)
        return studentMapper.delete( sno );
        // TIP: 삭제 전 참조 무결성 체크(외래키 존재 여부) 등을 추가하는 것이 실무에서는 일반적임.
    }

}
