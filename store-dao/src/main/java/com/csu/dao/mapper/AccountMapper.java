package com.csu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.model.Entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账户数据访问接口
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    /**
     * 根据用户名查询账户信息
     */
    Account selectByUsername(@Param("username") String username);

    /**
     * 模糊查询用户列表（按姓名或邮箱）
     */
    List<Account> searchAccounts(@Param("keyword") String keyword);

    /**
     * 查询所有账户列表（分页）
     */
    List<Account> selectAllAccounts(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计账户总数
     */
    int countAllAccounts();

    /**
     * 更新账户状态
     */
    int updateAccountStatus(@Param("username") String username, @Param("status") String status);
}
