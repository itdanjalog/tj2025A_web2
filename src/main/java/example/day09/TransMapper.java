package example.day09;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransMapper {
    // (1) insert
    @Insert( "insert into trans( name ) values( # {name} ) ")
    public boolean trans1(String name );
}




