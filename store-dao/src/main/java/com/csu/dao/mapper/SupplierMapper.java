package com.csu.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.csu.model.Entity.Supplier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供应商数据访问接口
 */
@Mapper
public interface SupplierMapper extends BaseMapper<Supplier> {

    /**
     * 查询所有供应商列表
     */
    List<Supplier> selectAllSuppliers();

    /**
     * 根据供应商名称模糊查询
     */
    List<Supplier> searchByName(@Param("name") String name);

    /**
     * 查询活跃的供应商
     */
    List<Supplier> selectActiveSuppliers();
}
