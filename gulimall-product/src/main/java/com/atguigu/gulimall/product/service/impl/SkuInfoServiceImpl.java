package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.to.SkuReductionTo;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.product.dao.SkuInfoDao;
import com.atguigu.gulimall.product.entity.SkuInfoEntity;
import com.atguigu.gulimall.product.feign.CouponFeignService;
import com.atguigu.gulimall.product.service.SkuImagesService;
import com.atguigu.gulimall.product.service.SkuInfoService;
import com.atguigu.gulimall.product.service.SkuSaleAttrValueService;
import com.atguigu.gulimall.product.vo.SkusBean;
import com.atguigu.gulimall.product.vo.SpuSaveVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkus(SpuSaveVo spuSaveVo, Long spuId) {
        List<SkusBean> skus = spuSaveVo.getSkus();
        if (CollectionUtils.isEmpty(skus)) {
            return;
        }

        for (SkusBean skusBean : skus) {
            SkuInfoEntity skuInfo = toSkuInfo(spuSaveVo, spuId, skusBean);
            this.save(skuInfo);

            Long skuId = skuInfo.getSkuId();
            skuImagesService.saveImages(skuId, skusBean.getImages());
            skuSaleAttrValueService.saveAttrs(skuId, skusBean.getAttr());

            SkuReductionTo skuReductionTo = toSkuReduction(skusBean, skuId);
            R r = couponFeignService.saveSkuReduction(skuReductionTo);
            if (r.getCode() != 0) {
                log.error("saveSkuReduction error!!!");
            }
        }
    }

    private SkuReductionTo toSkuReduction(SkusBean skusBean, Long skuId) {
        SkuReductionTo skuReductionTo = new SkuReductionTo();
        BeanUtils.copyProperties(skusBean, skuReductionTo);
        skuReductionTo.setSkuId(skuId);
        return skuReductionTo;
    }

    private SkuInfoEntity toSkuInfo(SpuSaveVo spuSaveVo, Long spuId, SkusBean skusBean) {
        SkuInfoEntity skuInfo = new SkuInfoEntity();
        BeanUtils.copyProperties(skusBean, skuInfo);
        skuInfo.setSpuId(spuId);
        skuInfo.setCatalogId(spuSaveVo.getCatalogId());
        skuInfo.setBrandId(spuSaveVo.getBrandId());
        skuInfo.setSkuDefaultImg(skusBean.getDefaultImage());
        skuInfo.setSaleCount(0L);
        return skuInfo;
    }

}