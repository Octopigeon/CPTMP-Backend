package octopigeon.github.io.cptmpdao.mapper;

import octopigeon.github.io.cptmpdao.module.Pigeon;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PigeonMapper {

    @Insert("insert into test_tb (pigeon_name) values (#{pigeonName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addPigeon(Pigeon pigeon);

    @Delete("delete from test_tb")
    void removeAllPigeons();

    @Select("select * from test_tb")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(column = "pigeon_name", property = "pigeonName", jdbcType = JdbcType.VARCHAR)
    })
    List<Pigeon> findAllPigeons();

}
