package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.to.SpuBoundTo;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.product.dao.SpuInfoDao;
import com.atguigu.gulimall.product.entity.SpuInfoEntity;
import com.atguigu.gulimall.product.feign.CouponFeignService;
import com.atguigu.gulimall.product.service.ProductAttrValueService;
import com.atguigu.gulimall.product.service.SkuInfoService;
import com.atguigu.gulimall.product.service.SpuImagesService;
import com.atguigu.gulimall.product.service.SpuInfoDescService;
import com.atguigu.gulimall.product.service.SpuInfoService;
import com.atguigu.gulimall.product.vo.BoundsBean;
import com.atguigu.gulimall.product.vo.SpuSaveVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveSpuSaveVo(SpuSaveVo spuSaveVo) {
        SpuInfoEntity spuInfo = saveSpuInfo(spuSaveVo);
        Long spuId = spuInfo.getId();

        spuInfoDescService.saveDecript(spuId, spuSaveVo.getDecript());
        spuImagesService.saveImages(spuId, spuSaveVo.getImages());
        productAttrValueService.saveBaseAttrs(spuId, spuSaveVo.getBaseAttrs());

        SpuBoundTo spuBoundTo = toSpuBoundTo(spuId, spuSaveVo);
        R r = couponFeignService.saveSpuBounds(spuBoundTo);
        if (r.getCode() != 0) {
            log.error("saveSpuBounds error!!!");
        }

        skuInfoService.saveSkus(spuSaveVo, spuId);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> queryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and(qw -> qw.eq("id", key).or().like("spu_name", key));
        }

        String catelogId = (String) params.get("catelogId");
        if (catelogId != null && !"0".equals(catelogId)) {
            queryWrapper.eq("catalog_id", catelogId);
        }

        String brandId = (String) params.get("brandId");
        if (brandId != null && !"0".equals(brandId)) {
            queryWrapper.eq("brand_id", brandId);
        }

        String status = (String) params.get("status");
        if (status != null && !"0".equals(status)) {
            queryWrapper.eq("publish_status", status);
        }

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    private SpuBoundTo toSpuBoundTo(Long spuId, SpuSaveVo spuSaveVo) {
        SpuBoundTo spuBoundTo = new SpuBoundTo(spuId);
        BoundsBean bounds = spuSaveVo.getBounds();
        spuBoundTo.setBuyBounds(bounds.getBuyBounds());
        spuBoundTo.setGrowBounds(bounds.getGrowBounds());
        return spuBoundTo;
    }

    /**
     * 保存spu基本信息
     * @param spuSaveVo
     * @return
     */
    private SpuInfoEntity saveSpuInfo(SpuSaveVo spuSaveVo) {
        SpuInfoEntity spuInfo = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveVo, spuInfo);
        spuInfo.setCreateTime(new Date());
        spuInfo.setUpdateTime(new Date());
        this.save(spuInfo);
        return spuInfo;
    }

}