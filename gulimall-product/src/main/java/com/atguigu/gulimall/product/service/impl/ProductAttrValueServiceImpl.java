package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.vo.BaseAttrsBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.atguigu.gulimall.product.dao.ProductAttrValueDao;
import com.atguigu.gulimall.product.entity.ProductAttrValueEntity;
import com.atguigu.gulimall.product.service.ProductAttrValueService;
import org.springframework.util.CollectionUtils;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBaseAttrs(Long spuId, List<BaseAttrsBean> baseAttrs) {
        if (CollectionUtils.isEmpty(baseAttrs)) {
            return;
        }
        List<ProductAttrValueEntity> productAttrValueList = baseAttrs.stream().map(attrs -> toProductAttrValue(spuId, attrs)).collect(Collectors.toList());
        this.saveBatch(productAttrValueList);
    }

    private ProductAttrValueEntity toProductAttrValue(Long spuId, BaseAttrsBean attrs) {
        ProductAttrValueEntity productAttrValue = new ProductAttrValueEntity();
        productAttrValue.setSpuId(spuId);
        productAttrValue.setAttrId(attrs.getAttrId());
        productAttrValue.setAttrValue(attrs.getAttrValues());
        productAttrValue.setQuickShow(attrs.getShowDesc());
        return productAttrValue;
    }

}