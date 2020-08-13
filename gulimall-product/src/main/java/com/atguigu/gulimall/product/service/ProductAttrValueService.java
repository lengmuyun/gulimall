package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.vo.BaseAttrsBean;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gulimall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author fangkuangzhang
 * @email 971434648@qq.com
 * @date 2020-06-28 14:52:34
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 保存spu的规格参数
     * @param spuId
     * @param baseAttrs
     */
    void saveBaseAttrs(Long spuId, List<BaseAttrsBean> baseAttrs);

}

