package com.atguigu.gulimall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BoundsBean {

    /**
     * buyBounds : 500
     * growBounds : 500
     */

    private BigDecimal buyBounds;
    private BigDecimal growBounds;

}
