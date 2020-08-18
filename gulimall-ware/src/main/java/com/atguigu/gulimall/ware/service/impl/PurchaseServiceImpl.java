package com.atguigu.gulimall.ware.service.impl;

import com.atguigu.common.constant.ware.PurchaseEnum;
import com.atguigu.common.to.MergeTo;
import com.atguigu.gulimall.ware.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.ware.dao.PurchaseDao;
import com.atguigu.gulimall.ware.entity.PurchaseEntity;
import com.atguigu.gulimall.ware.service.PurchaseService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryUnreceivePage(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0).or().eq("status", 1);

        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void merge(MergeTo mergeTo) {
        Long purchaseId = mergeTo.getPurchaseId();
        if (purchaseId == null) {
            PurchaseEntity purchase = createAutoPurchase();
            purchaseId = purchase.getId();
        }

        purchaseDetailService.updatePurchaseDetail(mergeTo.getItems(), purchaseId);
    }

    @Override
    @Transactional
    public void receive(List<Long> purchaseIds) {
        List<PurchaseEntity> purchaseList = this.listByIds(purchaseIds);

        List<PurchaseEntity> filterList = filterUnreceivedAndUpdateStatus(purchaseList);
        if (CollectionUtils.isEmpty(filterList)) {
            return;
        }
        this.updateBatchById(filterList);

        purchaseDetailService.updatePurchaseDetailReceived(filterList);
    }

    private List<PurchaseEntity> filterUnreceivedAndUpdateStatus(List<PurchaseEntity> purchaseList) {
        return purchaseList.stream()
                    .filter(PurchaseEntity::unreceived)
                    .peek(purchase -> {
                        purchase.setStatus(PurchaseEnum.RECEIVED.getCode());
                        purchase.setUpdateTime(new Date());
                    }).collect(Collectors.toList());
    }

    private PurchaseEntity createAutoPurchase() {
        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setStatus(PurchaseEnum.CREATED.getCode());
        purchase.setCreateTime(new Date());
        purchase.setUpdateTime(new Date());
        this.save(purchase);
        return purchase;
    }

}