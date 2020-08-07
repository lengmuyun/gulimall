package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author fangkuangzhang
 * @email 971434648@qq.com
 * @date 2020-06-28 14:52:34
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryBrandRelationEntity> queryByBrandId(String brandId);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    /**
     * 根据品牌id更新品牌名称
     * @param brandId
     * @param brandName
     */
    void updateBrandName(Long brandId, String brandName);

    /**
     * 根据分类id更新分类名称
     * @param catId
     * @param categoryName
     */
    void updateCategoryName(Long catId, String categoryName);

    List<CategoryBrandRelationEntity> getRelatedBrand(Long catId);

}

