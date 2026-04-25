package com.csu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.model.Entity.SignOn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 登录凭证数据访问接口
 */
@Mapper
public interface SignOnMapper extends BaseMapper<SignOn> {

    /**
     * 根据用户名查询密码
     */
    SignOn selectByUsername(@Param("username") String username);

    /**
     * 验证用户名和密码
     */
    SignOn validateLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 更新密码
     */
    int updatePassword(@Param("username") String username, @Param("password") String password);
}
