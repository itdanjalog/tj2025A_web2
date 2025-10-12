package example.day13;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface XmlMapper {

    // [1] 등록
    int save(  StudentDto map);

    // [2] 전체조회
    List< StudentDto > findAll();

    // [5] 특정 점수 이상 조회
    List< StudentDto > findStudentScores(Integer minKor, Integer minMath);

    // [6] 여러명 등록
    int saveAll(List< StudentDto > list);

    // [7] 정렬 + 제한 조회
    List< StudentDto > findAllOrderByLimit(String orderBy, Integer limit);
}