package example.day13;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface XmlMapper {

    // # 1. 등록
    // Mybatis 에서 SQL 매핑하는 방법
    // 방법1 : 추상메소드 위에 @Insert("SQL") 작성, 간단한 SQL CRUD 권장
    // 방법2 : 추상메소드를 매핑하는 XML파일에서 SQL 작성 , 복잡한 SQL 권장
    int save( StudentDto dto );

    // # 2. 전체조회
    List<StudentDto> findAll();


} // i end









