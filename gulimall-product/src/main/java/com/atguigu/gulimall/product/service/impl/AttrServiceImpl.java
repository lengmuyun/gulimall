package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.constant.product.AttrEnum;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.dao.AttrDao;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.product.service.AttrService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.AttrResponseVo;
import com.atguigu.gulimall.product.vo.AttrVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return queryPage(params, new QueryWrapper<>());
    }

    @Override
    @Transactional
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.baseMapper.insert(attrEntity);

        if (attr.getAttrType() == AttrEnum.ATTR_TYPE_BASE.getCode()) {
            attrAttrgroupRelationService.saveAttrAttrgroupRelation(attrEntity.getAttrId(), attr.getAttrGroupId());
        }
    }

    @Override
    public PageUtils queryAttrPage(Map<String, Object> params, Long catelogId, String attrType) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type", "base".equalsIgnoreCase(attrType) ? AttrEnum.ATTR_TYPE_BASE.getCode() : AttrEnum.ATTR_TYPE_SALE.getCode());
        if (catelogId != 0) {
            queryWrapper.eq("catelog_id", catelogId);
        }

        String key = (String) params.get("key");
        if (StringUtils.isNotEmpty(key)) {
            queryWrapper.and(qw -> qw.eq("attr_id", key).or().like("attr_name", key));
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        List<AttrEntity> records = page.getRecords();
        List<AttrResponseVo> attrResponseVoList = records.stream()
                .map(this::toAttrResponseVo)
                .collect(Collectors.toList());

        PageUtils pageUtils = new PageUtils(page);
        pageUtils.setList(attrResponseVoList);

        return pageUtils;
    }

    private AttrResponseVo toAttrResponseVo(AttrEntity attrEntity) {
        AttrResponseVo attrResponseVo = new AttrResponseVo();
        BeanUtils.copyProperties(attrEntity, attrResponseVo);

        attrAttrgroupRelationService.fillAttrGroupName(attrResponseVo);
        categoryService.fillCatelogName(attrResponseVo);

        return attrResponseVo;
    }

    private PageUtils queryPage(Map<String, Object> params, QueryWrapper<AttrEntity> queryWrapper) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public AttrResponseVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrResponseVo attrResponseVo = new AttrResponseVo();
        BeanUtils.copyProperties(attrEntity, attrResponseVo);

        attrAttrgroupRelationService.fillAttrGroupId(attrResponseVo);

        Long[] catelogPath = categoryService.getCatelogPath(attrEntity.getCatelogId());
        attrResponseVo.setCatelogPath(catelogPath);

        return attrResponseVo;
    }

    @Override
    public void updateAttrInfo(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        this.updateById(attrEntity);

        if (attr.getAttrType() == AttrEnum.ATTR_TYPE_BASE.getCode()) {
            attrAttrgroupRelationService.saveOrUpdate(attr.getAttrId(), attr.getAttrGroupId());
        }
    }

}