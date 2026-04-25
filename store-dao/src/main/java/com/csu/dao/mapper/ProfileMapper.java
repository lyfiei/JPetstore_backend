package com.csu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.model.Entity.Profile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户配置数据访问接口
 */
@Mapper
public interface ProfileMapper extends BaseMapper<Profile> {

    /**
     * 根据用户ID查询配置信息
     */
    Profile selectByUserId(@Param("userId") String userId);

    /**
     * 插入或更新用户配置
     */
    int insertOrUpdateProfile(@Param("profile") Profile profile);
}
