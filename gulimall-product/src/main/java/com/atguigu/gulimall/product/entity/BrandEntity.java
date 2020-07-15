package com.atguigu.gulimall.product.entity;

import com.atguigu.common.valid.AddGroup;
import com.atguigu.common.valid.ListValue;
import com.atguigu.common.valid.UpdateGroup;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

/**
 * 品牌
 * 
 * @author fangkuangzhang
 * @email 971434648@qq.com
 * @date 2020-06-28 14:52:34
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	@Null(message = "新增不能指定品牌id", groups = { AddGroup.class })
	@NotNull(message = "更新必须指定品牌id", groups = { UpdateGroup.class })
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "品牌名不能为空", groups = { AddGroup.class, UpdateGroup.class })
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotBlank(message = "品牌logo地址不能为空", groups = { AddGroup.class, UpdateGroup.class })
	@URL(message = "品牌logo地址必须是一个合法的URL", groups = { AddGroup.class, UpdateGroup.class })
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@ListValue(values={ 0, 1 }, groups = { AddGroup.class })
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotBlank(message = "检索首字母不能为空", groups = { AddGroup.class, UpdateGroup.class })
	@Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须为a-z或A-Z", groups = { AddGroup.class, UpdateGroup.class })
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(message = "排序不能为null", groups = { AddGroup.class, UpdateGroup.class })
	@Min(value = 0, message = "排序必须为大于等于0的整数", groups = { AddGroup.class, UpdateGroup.class })
	private Integer sort;

}
