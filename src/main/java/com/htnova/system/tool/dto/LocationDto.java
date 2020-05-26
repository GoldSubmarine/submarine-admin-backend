package com.htnova.system.tool.dto;

import com.htnova.common.base.BaseTreeDto;
import com.htnova.common.base.BaseTreeEntity;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.htnova.common.base.BaseEntity;
import com.htnova.system.tool.entity.Location;
import com.htnova.system.tool.mapstruct.LocationMapStruct;
import org.mapstruct.factory.Mappers;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class LocationDto extends BaseTreeDto<LocationDto> {

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
