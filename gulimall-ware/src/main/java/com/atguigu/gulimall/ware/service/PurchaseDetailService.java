package com.atguigu.gulimall.ware.service;

import com.atguigu.gulimall.ware.entity.PurchaseEntity;
import com.atguigu.gulimall.ware.vo.PurchaseDoneVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.ware.entity.PurchaseDetailEntity;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author fangkuangzhang
 * @email 971434648@qq.com
 * @date 2020-06-28 17:13:47
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updatePurchaseDetail(List<Long> items, Long purchaseId);

    void updatePurchaseDetailReceived(@NotEmpty List<PurchaseEntity> filterList);

    /**
     * 更新采购单的采购详情
     * @param items
     */
    void updatePurchaseOrderDetail(List<PurchaseDoneVo.PurchaseItemDoneVo> items);

}

