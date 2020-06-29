package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author fangkuangzhang
 * @email 971434648@qq.com
 * @date 2020-06-28 17:10:05
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
