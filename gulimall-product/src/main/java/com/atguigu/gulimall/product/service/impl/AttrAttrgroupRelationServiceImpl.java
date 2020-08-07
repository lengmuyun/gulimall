package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.product.service.AttrGroupService;
import com.atguigu.gulimall.product.vo.AttrRelationVo;
import com.atguigu.gulimall.product.vo.AttrResponseVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Autowired
    private AttrGroupService attrGroupService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveAttrAttrgroupRelation(Long attrId, Long attrGroupId) {
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrId(attrId);
        attrAttrgroupRelationEntity.setAttrGroupId(attrGroupId);
        this.baseMapper.insert(attrAttrgroupRelationEntity);
    }

    @Override
    public void fillAttrGroupName(AttrResponseVo attrResponseVo) {
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = this.baseMapper.selectOne(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrResponseVo.getAttrId())
        );
        if (attrAttrgroupRelationEntity != null && attrAttrgroupRelationEntity.getAttrGroupId() != null) {
            AttrGroupEntity attrGroupEntity = attrGroupService.getById(attrAttrgroupRelationEntity.getAttrGroupId());
            attrResponseVo.setGroupName(attrGroupEntity.getAttrGroupName());
        }
    }

    @Override
    public void fillAttrGroupId(AttrResponseVo attrResponseVo) {
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = this.baseMapper.selectOne(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrResponseVo.getAttrId())
        );
        if (attrAttrgroupRelationEntity != null) {
            attrResponseVo.setAttrGroupId(attrAttrgroupRelationEntity.getAttrGroupId());
        }
    }

    @Override
    public void saveOrUpdate(Long attrId, Long attrGroupId) {
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrId(attrId);
        attrAttrgroupRelationEntity.setAttrGroupId(attrGroupId);
        this.saveOrUpdate(attrAttrgroupRelationEntity);
    }

    @Override
    public void saveAttrRelation(List<AttrRelationVo> relationVoList) {
        List<AttrAttrgroupRelationEntity> relationList = relationVoList.stream().map(this::toAttrAttrgroupRelation).collect(Collectors.toList());
        this.saveBatch(relationList);
    }

    private AttrAttrgroupRelationEntity toAttrAttrgroupRelation(AttrRelationVo vo) {
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        BeanUtils.copyProperties(vo, attrAttrgroupRelationEntity);
        return attrAttrgroupRelationEntity;
    }

    @Override
    public void deleteBatch(List<AttrRelationVo> attrRelationList) {
        this.baseMapper.deleteBatch(attrRelationList);
    }

}