package com.myke.dao;

import com.myke.model.dto.UserDTO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface UserMapper {
    @Delete({
            "delete from user",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into user (id, name, ",
            "password, create_time)",
            "values (#{id,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, ",
            "#{password,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
    int insert(UserDTO record);


    @Select({
            "select",
            "id, name, password, create_time",
            "from user",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP)
    })
    UserDTO selectByPrimaryKey(Long id);


}