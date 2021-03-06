package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.vo.ImagesBean;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.SkuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * sku图片
 *
 * @author fangkuangzhang
 * @email 971434648@qq.com
 * @date 2020-06-28 14:52:34
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存sku的图片信息
     * @param skuId
     * @param images
     */
    void saveImages(Long skuId, List<ImagesBean> images);

}

