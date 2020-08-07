package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.vo.AttrGroupWithAttrsVo;
import com.atguigu.gulimall.product.vo.AttrRelationVo;
import com.atguigu.gulimall.product.vo.AttrResponseVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author fangkuangzhang
 * @email 971434648@qq.com
 * @date 2020-06-28 14:52:34
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttrAttrgroupRelation(Long attrId, Long attrGroupId);

    void fillAttrGroupName(AttrResponseVo attrEntity);

    void fillAttrGroupId(AttrResponseVo attrResponseVo);

    void saveOrUpdate(Long attrId, Long attrGroupId);

    void saveAttrRelation(List<AttrRelationVo> relationVoList);

    void deleteBatch(List<AttrRelationVo> asList);

}

