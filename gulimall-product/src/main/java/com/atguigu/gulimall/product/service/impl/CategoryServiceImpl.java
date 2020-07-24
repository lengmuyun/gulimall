package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(null);
        return categoryEntityList.stream()
                .filter(c -> c.getCatLevel() == 1)
                .peek(categoryEntity -> categoryEntity.setChildren(getChildren(categoryEntity, categoryEntityList)))
                .sorted(CategoryEntity.getSortComparator())
                .collect(Collectors.toList());
    }

    private List<CategoryEntity> getChildren(CategoryEntity categoryEntity, List<CategoryEntity> categoryEntityList) {
        return categoryEntityList.stream()
                .filter(c -> categoryEntity.getCatId().equals(c.getParentCid()))
                .peek(c -> c.setChildren(getChildren(c, categoryEntityList)))
                .sorted(CategoryEntity.getSortComparator())
                .collect(Collectors.toList());
    }

    @Override
    public void removeMenuByIds(List<Long> menuIds) {
        baseMapper.deleteBatchIds(menuIds);
    }

    @Override
    public Long[] getCatelogPath(Long catelogId) {
        List<Long> catelogPath = new ArrayList<>();
        CategoryEntity categoryEntity = baseMapper.selectById(catelogId);
        catelogPath.add(catelogId);

        while (categoryEntity != null && categoryEntity.getParentCid() != 0) {
            catelogPath.add(categoryEntity.getParentCid());
            categoryEntity = baseMapper.selectById(categoryEntity.getParentCid());
        }

        Collections.reverse(catelogPath);
        return catelogPath.toArray(new Long[0]);
    }

    @Override
    public void updateCascade(CategoryEntity category) {
        CategoryEntity categoryEntity = this.baseMapper.selectById(category.getCatId());
        this.baseMapper.updateById(category);

        // 分类名称变更则更新关联表中的分类名称
        if (!category.getName().equals(categoryEntity.getName())) {
            categoryBrandRelationService.updateCategoryName(category.getCatId(), category.getName());
        }
    }

}