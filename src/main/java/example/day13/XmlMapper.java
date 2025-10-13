package example.day13;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface XmlMapper {
    // # 1. 등록
    // Mybatis 에서 SQL 매핑하는 방법
    // 방법1 : 추상메소드 위에 @Insert("SQL") 작성, 간단한 SQL CRUD 권장
    // 방법2 : 추상메소드를 매핑하는 XML파일에서 SQL 작성 , 복잡한 SQL 권장

    // [ 생성된 PK값 반환하는 방법 ]
    // @Insert("INSERT INTO student(name, kor, math) VALUES( #{name} , #{kor}, #{math} );")
    // @Options( useGeneratedKeys = true , keyProperty = "sno" ) // 생성된 PK값을 sno 필드에 매핑
    int save( StudentDto dto );

    // # 2. 전체조회
    List<StudentDto> findAll();

    // # 3. 개별 학생 조회
    StudentDto find( int sno );

    // # 4. 개별 학생 삭제
    int delete ( int sno );

    // # 5. 개별 학생 수정
    int update( StudentDto studentDto );

} // i end









