package com.atguigu.gulimall.ware.service.impl;

import com.atguigu.common.constant.ware.PurchaseDetailEnum;
import com.atguigu.gulimall.ware.entity.PurchaseDetailEntity;
import com.atguigu.gulimall.ware.service.PurchaseDetailService;
import com.atguigu.gulimall.ware.vo.PurchaseDoneVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.ware.dao.WareSkuDao;
import com.atguigu.gulimall.ware.entity.WareSkuEntity;
import com.atguigu.gulimall.ware.service.WareSkuService;
import org.springframework.util.StringUtils;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();

        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }

        String skuId = (String) params.get("skuId");
        if (!StringUtils.isEmpty(skuId)) {
            queryWrapper.eq("sku_id", skuId);
        }

        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void putInStorage(List<PurchaseDoneVo.PurchaseItemDoneVo> items) {
        items.stream()
                .filter(item -> item.getStatus() != PurchaseDetailEnum.FAILED.getCode())
                .forEach(item -> {
                    PurchaseDetailEntity purchaseDetail = purchaseDetailService.getById(item.getItemId());
                    saveOrUpdateWareSku(purchaseDetail);
                });
    }

    private void saveOrUpdateWareSku(PurchaseDetailEntity purchaseDetail) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<WareSkuEntity>()
                .eq("sku_id", purchaseDetail.getSkuId())
                .eq("ware_id", purchaseDetail.getWareId());

        int count = this.count(queryWrapper);
        if (count == 0) {
            saveWareSku(purchaseDetail);
        } else {
            this.baseMapper.addWareSku(purchaseDetail.getSkuId(), purchaseDetail.getWareId(), purchaseDetail.getSkuNum());
        }
    }

    private void saveWareSku(PurchaseDetailEntity purchaseDetail) {
        WareSkuEntity wareSku = new WareSkuEntity();
        wareSku.setSkuId(purchaseDetail.getSkuId());
        wareSku.setWareId(purchaseDetail.getWareId());
        wareSku.setStock(purchaseDetail.getSkuNum());
        wareSku.setStockLocked(0);
        this.save(wareSku);
    }

}