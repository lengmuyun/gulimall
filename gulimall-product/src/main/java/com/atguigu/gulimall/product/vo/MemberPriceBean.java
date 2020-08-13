package com.atguigu.gulimall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MemberPriceBean {

    /**
     * id : 1
     * name : 普通会员
     * price : 1439
     */

    private Long id;
    private String name;
    private BigDecimal price;

}
