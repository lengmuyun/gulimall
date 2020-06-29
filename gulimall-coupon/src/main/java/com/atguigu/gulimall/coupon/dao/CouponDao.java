package com.atguigu.gulimall.coupon.dao;

import com.atguigu.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author fangkuangzhang
 * @email 971434648@qq.com
 * @date 2020-06-28 16:46:46
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
