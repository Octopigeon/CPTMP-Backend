package io.github.octopigeon.cptmpdao.mapper;

import io.github.octopigeon.cptmpdao.model.EnterpriseAdmin;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


/**
 * @author Gh Li
 * @version 1.0
 * @date 2020/7/7
 */
@Repository
@Mapper
public interface EnterpriseAdminMapper {
    @Insert("insert into enterprise_admin (gmt_create, idx_name, uk_user_id, uk_employee_id) values (#{gmtCreate, name, userId, employeeId)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addEnterpriseAdmin(EnterpriseAdmin enterpriseAdmin);

    @Delete("delete from enterprise_admin where uk_user_id = #{userId}")
    void removeEnterprseAdminByUserId(BigInteger userId);

    @Update("update enterprise_admin set gmt_modified = #{gmtModified}, idx_name = #{name}, uk_employee_id = #{employeeId} where uk_user_id = #{userId}")
    void updateEnterprseAdminByUserId(BigInteger userId, Date gmtModified, String name, BigInteger employeeId);

    @Select("select uk_user_id, idx_name, uk_employee_id from enterprise_admin")
    @Results({
            @Result(column = "uk_user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_employee_id", property = "employeeId", jdbcType = JdbcType.BIGINT)
    })
    List<EnterpriseAdmin> findAllEnterpriseAdmins();

    @Select("select idx_name, uk_employee_id from enterprise_admin where uk_user_id = #{userId}")
    @Results({
            @Result(column = "idx_name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uk_employee_id", property = "employeeId", jdbcType = JdbcType.BIGINT)
    })
    EnterpriseAdmin findEnterpriseAdminByUserId(BigInteger userId);
}
