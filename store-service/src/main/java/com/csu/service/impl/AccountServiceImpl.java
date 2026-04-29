package com.csu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csu.common.exception.BusinessException;
import com.csu.dao.mapper.AccountMapper;
import com.csu.dao.mapper.CategoryMapper;
import com.csu.dao.mapper.ProfileMapper;
import com.csu.model.Entity.Account;
import com.csu.model.Entity.Category;
import com.csu.model.Entity.Profile;
import com.csu.model.Enum.AccountStatusEnum;
import com.csu.model.vo.AccountVO;
import com.csu.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 账户服务实现类
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final ProfileMapper profileMapper;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<AccountVO> getUserList(int pageNum, int pageSize, String keyword) {
        Page<Account> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Account> queryWrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like(Account::getUsername, keyword)
                       .or()
                       .like(Account::getFirstName, keyword)
                       .or()
                       .like(Account::getLastName, keyword)
                       .or()
                       .like(Account::getEmail, keyword);
        }
        
        queryWrapper.orderByAsc(Account::getUsername);
        
        Page<Account> accountPage = accountMapper.selectPage(page, queryWrapper);
        
        // 转换为VO
        Page<AccountVO> voPage = new Page<>(accountPage.getCurrent(), accountPage.getSize(), accountPage.getTotal());
        List<AccountVO> voList = accountPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        
        return voPage;
    }

    @Override
    public AccountVO getUserDetail(String username) {
        Account account = accountMapper.selectByUsername(username);
        if (account == null) {
            throw new BusinessException("用户不存在");
        }
        return convertToVO(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserStatus(String username, String status) {
        Account account = accountMapper.selectByUsername(username);
        if (account == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 验证状态值
        try {
            AccountStatusEnum.fromCode(status);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("无效的用户状态");
        }
        
        return accountMapper.updateAccountStatus(username, status) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(String username) {
        Account account = accountMapper.selectByUsername(username);
        if (account == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 删除用户配置
        profileMapper.deleteById(username);
        
        return accountMapper.deleteById(username) > 0;
    }

    /**
     * 将Entity转换为VO
     */
    private AccountVO convertToVO(Account account) {
        AccountVO vo = new AccountVO();
        BeanUtils.copyProperties(account, vo);
        
        // 设置状态描述
        if (StringUtils.hasText(account.getStatus())) {
            try {
                AccountStatusEnum statusEnum = AccountStatusEnum.fromCode(account.getStatus());
                vo.setStatusDescription(statusEnum.getDescription());
            } catch (IllegalArgumentException e) {
                vo.setStatusDescription("未知状态");
            }
        }
        
        // 获取用户配置信息
        Profile profile = profileMapper.selectByUserId(account.getUsername());
        if (profile != null) {
            vo.setFavouriteCategoryId(profile.getFavouriteCategoryId());
            vo.setLanguagePreference(profile.getLanguagePreference());
            vo.setListOption(profile.isListOption());
            vo.setBannerOption(profile.isBannerOption());
            
            // 获取偏好分类名称
            if (StringUtils.hasText(profile.getFavouriteCategoryId())) {
                Category category = categoryMapper.selectById(profile.getFavouriteCategoryId());
                if (category != null) {
                    vo.setFavouriteCategoryName(category.getName());
                }
            }
            
            // 设置横幅广告名称
            vo.setBannerName(profile.isBannerOption() ? "宠物商店横幅" : "无横幅");
        }
        
        return vo;
    }
}
