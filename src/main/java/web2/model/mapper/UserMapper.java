package web2.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import web2.model.dto.UserDto;

import java.util.List;

@Mapper
public interface UserMapper {

    // 회원 등록
    @Insert("""
        INSERT INTO users (uid, upwd, uname, uphone, urole)
        VALUES (#{uid}, #{upwd}, #{uname}, #{uphone}, #{urole})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "uno")
    int save(UserDto dto);

    // 아이디로 회원 조회
    @Select("SELECT * FROM users WHERE uid = #{uid}")
    UserDto findByUid(String uid);

}
