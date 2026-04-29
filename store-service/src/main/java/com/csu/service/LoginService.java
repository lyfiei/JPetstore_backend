package com.csu.service;

import com.csu.model.DTO.LoginDTO;

/**
 * 登录服务接口
 */
public interface LoginService {

    /**
     * 用户登录
     */
    boolean login(LoginDTO loginDTO);

    /**
     * 验证用户是否存在且状态正常
     */
    boolean validateUser(String username);
}
