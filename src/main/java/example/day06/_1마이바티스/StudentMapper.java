package example.day06._1마이바티스;
// 패키지 경로: src/main/java/example/day06/_1마이바티스/StudentMapper.java

import org.apache.ibatis.annotations.*; // MyBatis 매퍼 관련 애너테이션 (Mapper, Insert, Select, Update, Delete)
import java.util.List;
import java.util.Map;

/**
 * StudentMapper 인터페이스
 * - 역할: MyBatis를 활용해 DB에 직접 접근하는 계층 (DAO 역할)
 * - 특징: SQL문을 XML 파일이 아닌, 애너테이션(@Insert, @Select, @Update, @Delete)으로 작성
 * - MyBatis가 런타임에 프록시 객체를 생성하여 SQL을 실행하고 결과를 DTO/Map에 매핑
 *
 * 매핑 방식
 * - 방법1: XML 매퍼 파일 (*.xml) 사용 → SQL 분리 관리
 * - 방법2: 애너테이션 방식 (@SQL) 사용 → 코드와 SQL을 한 곳에서 관리 (현재 예제 방식)
 *
 * 주요 메소드 요약
 * [1] save()    : 학생 등록 (INSERT)
 * [2] findAll() : 전체 학생 목록 조회 (SELECT ALL)
 *     find()    : 단일 학생 조회 (SELECT ONE)
 * [3] update()  : 학생 정보 수정 (UPDATE)
 * [4] delete()  : 학생 삭제 (DELETE)
 */
@Mapper // 해당 인터페이스를 MyBatis Mapper로 등록 (스프링 컨테이너가 DAO처럼 사용 가능하게 함)
public interface StudentMapper {

    // ------------------------------ [1] 학생 등록(INSERT) ------------------------------
    @Insert(" INSERT INTO student( name, kor, math ) VALUES( #{name}, #{kor}, #{math} ) ")
    int save(StudentDto studentDto);
    // - #{name}, #{kor}, #{math} : StudentDto 객체의 필드와 매핑
    // - 반환값: int (INSERT된 행 수, 1=성공)

    // ------------------------------ [2] 전체 학생 조회(SELECT ALL) ------------------------------
    @Select(" SELECT * FROM student ")
    List<StudentDto> findAll();
    // - 결과: 테이블의 모든 행을 StudentDto 리스트로 변환
    // - 컬럼명과 DTO 필드명이 자동 매핑됨 (일치하지 않으면 @Results/@ResultMap 필요)

    // ------------------------------ (선택) 단일 학생 조회(SELECT ONE) ------------------------------
    @Select(" SELECT * FROM student WHERE sno = #{sno} ")
    Map<String,Object> find(int sno);
    // - 매개변수: sno (학생 번호, PK)
    // - 반환값: Map (컬럼명=Key, 값=Value)
    // TIP: 수업용으로 Map을 사용했지만, StudentDto로 매핑하는 편이 더 권장됨

    // ------------------------------ [3] 학생 정보 수정(UPDATE) ------------------------------
    @Update(" UPDATE student SET name = #{name}, kor = #{kor}, math = #{math} WHERE sno = #{sno} ")
    int update(StudentDto studentDto);
    // - 매개변수: 수정할 데이터가 담긴 StudentDto (sno 필수)
    // - 반환값: int (수정된 행 수, 1=성공 / 0=대상 없음)

    // ------------------------------ [4] 학생 삭제(DELETE) ------------------------------
    @Delete(" DELETE FROM student WHERE sno = #{sno} ")
    int delete(int sno);
    // - 매개변수: sno (삭제할 학생의 PK)
    // - 반환값: int (삭제된 행 수, 1=성공 / 0=대상 없음)

}
