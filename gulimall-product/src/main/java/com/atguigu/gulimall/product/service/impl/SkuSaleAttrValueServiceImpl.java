package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.vo.AttrBean;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.atguigu.gulimall.product.dao.SkuSaleAttrValueDao;
import com.atguigu.gulimall.product.entity.SkuSaleAttrValueEntity;
import com.atguigu.gulimall.product.service.SkuSaleAttrValueService;
import org.springframework.util.CollectionUtils;


@Service("skuSaleAttrValueService")
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueDao, SkuSaleAttrValueEntity> implements SkuSaleAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuSaleAttrValueEntity> page = this.page(
                new Query<SkuSaleAttrValueEntity>().getPage(params),
                new QueryWrapper<SkuSaleAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveAttrs(Long skuId, List<AttrBean> attrList) {
        if (CollectionUtils.isEmpty(attrList)) {
            return;
        }

        List<SkuSaleAttrValueEntity> skuSaleAttrValueList = attrList.stream().map(attrBean -> toSkuSaleAttrValue(skuId, attrBean)).collect(Collectors.toList());
        this.saveBatch(skuSaleAttrValueList);
    }

    private SkuSaleAttrValueEntity toSkuSaleAttrValue(Long skuId, AttrBean attrBean) {
        SkuSaleAttrValueEntity skuSaleAttrValue = new SkuSaleAttrValueEntity();
        BeanUtils.copyProperties(attrBean, skuSaleAttrValue);
        skuSaleAttrValue.setSkuId(skuId);
        return skuSaleAttrValue;
    }

}