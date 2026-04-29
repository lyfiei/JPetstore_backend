package com.csu.service.impl;

import com.csu.common.exception.BusinessException;
import com.csu.dao.mapper.AccountMapper;
import com.csu.dao.mapper.SignOnMapper;
import com.csu.model.DTO.LoginDTO;
import com.csu.model.Entity.Account;
import com.csu.model.Entity.SignOn;
import com.csu.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 登录服务实现类
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final SignOnMapper signOnMapper;
    private final AccountMapper accountMapper;

    @Override
    public boolean login(LoginDTO loginDTO) {
        if (!StringUtils.hasText(loginDTO.getUsername()) || !StringUtils.hasText(loginDTO.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }
        
        // 验证用户是否存在且密码正确
        SignOn signOn = signOnMapper.validateLogin(loginDTO.getUsername(), loginDTO.getPassword());
        if (signOn == null) {
            throw new BusinessException("用户名或密码错误");
        }
        
        // 验证账户状态
        Account account = accountMapper.selectByUsername(loginDTO.getUsername());
        if (account == null) {
            throw new BusinessException("用户不存在");
        }
        
        if ("OFF".equals(account.getStatus())) {
            throw new BusinessException("账户已被禁用");
        }
        
        return true;
    }

    @Override
    public boolean validateUser(String username) {
        Account account = accountMapper.selectByUsername(username);
        if (account == null) {
            return false;
        }
        
        return !"OFF".equals(account.getStatus());
    }
}
