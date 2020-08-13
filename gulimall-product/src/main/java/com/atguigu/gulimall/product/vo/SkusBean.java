package com.atguigu.gulimall.product.vo;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SkusBean {

    /**
     * attr : [{"attrId":7,"attrName":"颜色","attrValue":"冰岛幻境"},{"attrId":8,"attrName":"容量","attrValue":"6GB 128GB"}]
     * skuName : 荣耀Play4T Pro 冰岛幻境 6GB 128GB
     * price : 1489
     * skuTitle : 荣耀Play4T Pro 冰岛幻境 6GB 128GB
     * skuSubtitle : 4800万高感光夜拍三摄，光学屏幕指纹，OLED珍珠屏！荣耀爆品特惠，选品质，购荣耀~
     * images : [{"imgUrl":"https://gulimall-action.oss-cn-hangzhou.aliyuncs.com/2020-08-11/a86f392f-b6cd-4299-86af-a84df02b2a18_92eebd7a1e0f0f60.jpg","defaultImg":1},{"imgUrl":"https://gulimall-action.oss-cn-hangzhou.aliyuncs.com/2020-08-11/63eb1642-e862-48eb-8f2c-b6b5ddf7ce4f_21931f19ee2f4874.jpg","defaultImg":0},{"imgUrl":"https://gulimall-action.oss-cn-hangzhou.aliyuncs.com/2020-08-11/d8dc81f5-9c3e-4c64-8b06-8c3f7a736c49_55996410cb5226b3.jpg","defaultImg":0},{"imgUrl":"https://gulimall-action.oss-cn-hangzhou.aliyuncs.com/2020-08-11/2c7c45d7-1931-4c36-8fb3-aab0b52a6bbf_d2db1412dcce1d96.jpg","defaultImg":0},{"imgUrl":"https://gulimall-action.oss-cn-hangzhou.aliyuncs.com/2020-08-11/b5ddeccc-592c-44ea-87b7-b5b836d663b4_dfd6379ab1693d95.jpg","defaultImg":0}]
     * descar : ["冰岛幻境","6GB 128GB"]
     * fullCount : 3
     * discount : 0.9
     * countStatus : 0
     * fullPrice : 1000
     * reducePrice : 50
     * priceStatus : 0
     * memberPrice : [{"id":1,"name":"普通会员","price":1439},{"id":2,"name":"黄金会员","price":1389}]
     */

    private String skuName;
    private BigDecimal price;
    private String skuTitle;
    private String skuSubtitle;
    private int fullCount;
    private double discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<AttrBean> attr;
    private List<ImagesBean> images;
    private List<String> descar;
    private List<MemberPriceBean> memberPrice;

    /**
     * 获取默认的sku图片
     * @return
     */
    public String getDefaultImage() {
        if (CollectionUtils.isEmpty(images)) {
            return "";
        }
        String defaultImage = "";
        for (ImagesBean image : images) {
            if (image.getDefaultImg() == 1) {
                defaultImage = image.getImgUrl();
                break;
            }
        }
        return defaultImage;
    }
}
