package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.vo.ImagesBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.atguigu.gulimall.product.dao.SkuImagesDao;
import com.atguigu.gulimall.product.entity.SkuImagesEntity;
import com.atguigu.gulimall.product.service.SkuImagesService;
import org.springframework.util.CollectionUtils;


@Service("skuImagesService")
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesDao, SkuImagesEntity> implements SkuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuImagesEntity> page = this.page(
                new Query<SkuImagesEntity>().getPage(params),
                new QueryWrapper<SkuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveImages(Long skuId, List<ImagesBean> images) {
        if (CollectionUtils.isEmpty(images)) {
            return;
        }
        List<SkuImagesEntity> skuImagesList = images.stream().map(imagesBean -> toSkuImages(skuId, imagesBean)).collect(Collectors.toList());
        this.saveBatch(skuImagesList);
    }

    private SkuImagesEntity toSkuImages(Long skuId, ImagesBean imagesBean) {
        SkuImagesEntity skuImages = new SkuImagesEntity();
        skuImages.setSkuId(skuId);
        skuImages.setImgUrl(imagesBean.getImgUrl());
        skuImages.setDefaultImg(imagesBean.getDefaultImg());
        return skuImages;
    }

}