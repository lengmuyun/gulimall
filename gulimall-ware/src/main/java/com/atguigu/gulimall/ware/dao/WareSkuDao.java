package com.atguigu.gulimall.ware.dao;

import com.atguigu.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品库存
 * 
 * @author fangkuangzhang
 * @email 971434648@qq.com
 * @date 2020-06-28 17:13:47
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    void addWareSku(@Param("skuId") Long skuId, @Param("wareId") Long wareId, @Param("skuNum") Integer skuNum);

}
