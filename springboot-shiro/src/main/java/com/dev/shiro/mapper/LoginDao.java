package com.dev.shiro.mapper;

import com.dev.shiro.entity.Login;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Set;

/**
 * @Description : LoginDao  //描述
 *
 * @Date: 2020-12-07 16:16  //时间
 */
public interface LoginDao {

    /**
     * 根据用户登录名、密码查询
     *
     * @param loginName 登录名
     *
     * @return Login 用户账户
     */
    @Select("select id, role_id, login_name, password from login where login_name = #{loginName}")
    @Results(id = "BaseResultMap",value={
            @Result(column = "id",property = "id",jdbcType = JdbcType.INTEGER,id = true),
            @Result(column = "role_id",property = "roleId",jdbcType = JdbcType.INTEGER),
            @Result(column = "login_name",property = "loginName",jdbcType = JdbcType.VARCHAR),
            @Result(column = "password",property = "password",jdbcType = JdbcType.VARCHAR)
    })
    Login queryUserByLoginNameOrUserNameAndPwd(@Param("loginName") String loginName);

    /**
     * 根据登录名校验
     *
     * @param loginName 登录名
     *
     * @return int 账户效验
     */
    @Select("select count(1) from login where login_name = #{loginName}")
    int countUserByLoginName(String loginName);

    /**
     * 获取角色
     *
     * @param loginName 登录名
     *
     * @return Set<String> 角色列表
     */
    @Select("select r.role_value from login l, role r where l.role_id = r.role_id and l.login_name = #{loginName}")
    Set<String> getRoles(String loginName);

    /**
     * 获取权限
     *
     * @param loginName 登录名
     *
     * @return Set<String> 权限列表
     */
    @Select("select p.per_value from login l, role r, permission p where l.role_id = r.role_id and r.role_id = p.role_id and l.login_name = #{loginName}")
    Set<String> getPermissions(String loginName);
}
