package com.atguigu.gulimall.ware.service.impl;

import com.atguigu.common.constant.ware.PurchaseDetailEnum;
import com.atguigu.gulimall.ware.entity.PurchaseEntity;
import com.atguigu.gulimall.ware.vo.PurchaseDoneVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.ware.dao.PurchaseDetailDao;
import com.atguigu.gulimall.ware.entity.PurchaseDetailEntity;
import com.atguigu.gulimall.ware.service.PurchaseDetailService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseDetailEntity> queryWrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and(qw -> queryWrapper.eq("purchase_id", key).or().eq("sku_id", key));
        }

        String status = (String) params.get("status");
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status", status);
        }

        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(wareId)) {
            queryWrapper.eq("ware_id", wareId);
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void updatePurchaseDetail(List<Long> items, Long purchaseId) {
        List<PurchaseDetailEntity> purchaseDetailList = items.stream().map(item -> buildPurchaseDetail(item, purchaseId)).collect(Collectors.toList());
        this.updateBatchById(purchaseDetailList);
    }

    private PurchaseDetailEntity buildPurchaseDetail(Long itemId, Long purchaseId) {
        PurchaseDetailEntity purchaseDetail = new PurchaseDetailEntity();
        purchaseDetail.setId(itemId);
        purchaseDetail.setPurchaseId(purchaseId);
        purchaseDetail.setStatus(PurchaseDetailEnum.ASSIGNED.getCode());
        return purchaseDetail;
    }

    @Override
    public void updatePurchaseDetailReceived(List<PurchaseEntity> filterList) {
        List<Long> filterPurchaseIds = filterList.stream().map(PurchaseEntity::getId).collect(Collectors.toList());
        List<PurchaseDetailEntity> list = this.list(new QueryWrapper<PurchaseDetailEntity>().in("purchase_id", filterPurchaseIds));
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        List<PurchaseDetailEntity> updateList = list.stream().map(this::getUpdatePurchaseDetail).collect(Collectors.toList());
        this.updateBatchById(updateList);
    }

    private PurchaseDetailEntity getUpdatePurchaseDetail(PurchaseDetailEntity purchaseDetailEntity) {
        PurchaseDetailEntity updatePurchaseDetail = new PurchaseDetailEntity();
        updatePurchaseDetail.setId(purchaseDetailEntity.getId());
        updatePurchaseDetail.setStatus(PurchaseDetailEnum.PURCHASING.getCode());
        return updatePurchaseDetail;
    }

    @Override
    public void updatePurchaseOrderDetail(List<PurchaseDoneVo.PurchaseItemDoneVo> items) {
        List<PurchaseDetailEntity> updateList = items.stream().map(item -> {
            PurchaseDetailEntity purchaseDetail = new PurchaseDetailEntity();
            purchaseDetail.setId(item.getItemId());
            purchaseDetail.setStatus(item.getStatus());
            return purchaseDetail;
        }).collect(Collectors.toList());
        this.updateBatchById(updateList);
    }

}