package example.day12.query;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface QueryMapper2 {

    // [1] 등록
    int save(Map<String, Object> map);

    // [2] 전체조회
    List<Map<String, Object>> findAll();

    // [2] 전체조회
    List< StudentDto > findAll2();

    // [3] 수정
    int update(Map<String, Object> map);

    // [4] 삭제
    int delete(int sno);

    // [5] 특정 점수 이상 조회
    List<Map<String,Object>> findStudentScores(Integer minKor, Integer minMath);

    // [6] 여러명 등록
    int saveAll(List<Map<String, Object>> list);

    // [7] 정렬 + 제한 조회
    List<Map<String,Object>> findAllOrderByLimit(String orderBy, Integer limit);
}
