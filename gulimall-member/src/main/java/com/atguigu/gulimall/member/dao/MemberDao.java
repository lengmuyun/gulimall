package com.atguigu.gulimall.member.dao;

import com.atguigu.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author fangkuangzhang
 * @email 971434648@qq.com
 * @date 2020-06-28 17:04:32
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
