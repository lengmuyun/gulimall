package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseDoneVo {

    /**
     * 采购单id
     */
    private Long id;

    private List<PurchaseItemDoneVo> items;

    public static class PurchaseItemDoneVo {

        private Long itemId;

        private Integer status;

        private String reason;

        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

}
