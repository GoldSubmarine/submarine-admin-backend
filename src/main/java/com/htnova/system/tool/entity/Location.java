package com.htnova.system.tool.entity;

import com.htnova.common.base.BaseTree;
import com.htnova.common.base.BaseTreeEntity;
import com.htnova.system.manage.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.htnova.common.base.BaseEntity;
import com.htnova.system.tool.dto.LocationDto;
import com.htnova.system.tool.mapstruct.LocationMapStruct;
import org.mapstruct.factory.Mappers;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@TableName("t_sys_location")
public class Location extends BaseTreeEntity<Location> {

	/**
	* 当前层次
	*/
	private Integer deep;

	/**
	* 区域名
	*/
	private String name;

	/**
	* 区域中心经纬度
	*/
	private String point;

    
}
