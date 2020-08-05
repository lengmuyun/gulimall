package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.vo.AttrResponseVo;
import com.atguigu.gulimall.product.vo.AttrVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author fangkuangzhang
 * @email 971434648@qq.com
 * @date 2020-06-28 14:52:34
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    AttrResponseVo getAttrInfo(Long attrId);

    void updateAttrInfo(AttrVo attr);

}

