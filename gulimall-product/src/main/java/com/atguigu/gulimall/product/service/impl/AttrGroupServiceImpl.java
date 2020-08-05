package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.constant.product.AttrEnum;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.service.AttrGroupService;
import com.atguigu.gulimall.product.service.AttrService;
import com.atguigu.gulimall.product.vo.AttrRelationVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return getPageUtils(params, new QueryWrapper<>());
    }

    private PageUtils getPageUtils(Map<String, Object> params, QueryWrapper<AttrGroupEntity> queryWrapper) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        QueryWrapper<AttrGroupEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and(wrapper -> wrapper.eq("attr_group_id", key).or().like("attr_group_name", key));
        }

        if (catelogId != 0) {
            queryWrapper.eq("catelog_id", catelogId);
        }
        return getPageUtils(params, queryWrapper);
    }

    @Override
    public List<AttrEntity> attrRelation(Long attrgroupId) {
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityList = attrAttrgroupRelationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId)
        );

        if (CollectionUtils.isEmpty(attrAttrgroupRelationEntityList)) {
            return Collections.emptyList();
        }

        List<Long> attrIds = attrAttrgroupRelationEntityList.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        return attrService.listByIds(attrIds);
    }

    @Override
    public void deleteAttrRelation(AttrRelationVo[] attrRelationList) {
        attrAttrgroupRelationDao.deleteBatch(Arrays.asList(attrRelationList));
    }

    @Override
    public PageUtils noattrRelation(Map<String, Object> params, Long attrgroupId) {
        AttrGroupEntity attrGroupEntity = this.baseMapper.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();

        // 当前分类下的分组
        List<AttrGroupEntity> attrGroupEntityList = this.baseMapper.selectList(
                new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId)
        );
        List<Long> attrGroupIdList = attrGroupEntityList.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());

        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationList = attrAttrgroupRelationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", attrGroupIdList)
        );
        List<Long> attrIdList = attrAttrgroupRelationList.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId)
                .eq("attr_type", AttrEnum.ATTR_TYPE_BASE.getCode());

        if (!CollectionUtils.isEmpty(attrIdList)) {
            queryWrapper.notIn("attr_id", attrIdList);
        }

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and(qw -> qw.eq("attr_id", key).or().like("attr_name", key));
        }

        IPage<AttrEntity> page = attrService.page(new Query<AttrEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }

}