package com.atguigu.common.to;

import lombok.Data;

import java.util.List;

@Data
public class MergeTo {

    /**
     * 整单id
     */
    private Long purchaseId;

    /**
     * 合并项集合
     */
    private List<Long> items;

}
