package com.atguigu.gulimall.coupon.service.impl;

import com.atguigu.common.to.MemberPriceTo;
import com.atguigu.common.to.SkuReductionTo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.coupon.dao.MemberPriceDao;
import com.atguigu.gulimall.coupon.entity.MemberPriceEntity;
import com.atguigu.gulimall.coupon.service.MemberPriceService;
import org.springframework.util.CollectionUtils;


@Service("memberPriceService")
public class MemberPriceServiceImpl extends ServiceImpl<MemberPriceDao, MemberPriceEntity> implements MemberPriceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberPriceEntity> page = this.page(
                new Query<MemberPriceEntity>().getPage(params),
                new QueryWrapper<MemberPriceEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveMemberPrice(SkuReductionTo skuReductionTo) {
        saveMemberPrice(skuReductionTo.getSkuId(), skuReductionTo.getMemberPrice());
    }

    private void saveMemberPrice(Long skuId, List<MemberPriceTo> memberPriceList) {
        if (CollectionUtils.isEmpty(memberPriceList)) {
            return;
        }

        List<MemberPriceEntity> mpList = memberPriceList.stream().map(mp -> toMemberPrice(skuId, mp)).collect(Collectors.toList());
        this.saveBatch(mpList);
    }

    private MemberPriceEntity toMemberPrice(Long skuId, MemberPriceTo mp) {
        MemberPriceEntity memberPrice = new MemberPriceEntity();
        memberPrice.setSkuId(skuId);
        memberPrice.setMemberLevelId(mp.getId());
        memberPrice.setMemberLevelName(mp.getName());
        memberPrice.setMemberPrice(mp.getPrice());
        memberPrice.setAddOther(1);
        return memberPrice;
    }

}