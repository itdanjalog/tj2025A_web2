package example.day09;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransMapper {
    // (1) insert1 : 정상
    @Insert( "insert into trans( name ) values( #{name} )")
    public boolean trans1(String name );
    // (1) insert2 : 비정상
    @Insert( "insert into trans( name ) 오타( #{name} ")
    public boolean trans2( String name );

}




