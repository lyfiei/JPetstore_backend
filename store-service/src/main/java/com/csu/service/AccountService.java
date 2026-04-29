package com.csu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csu.model.vo.AccountVO;

/**
 * 账户服务接口
 */
public interface AccountService {

    /**
     * 分页获取用户列表
     */
    Page<AccountVO> getUserList(int pageNum, int pageSize, String keyword);

    /**
     * 获取用户详情
     */
    AccountVO getUserDetail(String username);

    /**
     * 更新用户状态
     */
    boolean updateUserStatus(String username, String status);

    /**
     * 删除用户
     */
    boolean deleteUser(String username);
}
