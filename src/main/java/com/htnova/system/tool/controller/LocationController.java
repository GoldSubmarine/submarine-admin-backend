package com.htnova.system.tool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.TreeConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.common.converter.DtoConverter;
import com.htnova.system.manage.dto.PermissionDto;
import com.htnova.system.manage.entity.Permission;
import com.htnova.system.manage.mapstruct.PermissionMapStruct;
import com.htnova.system.tool.mapstruct.LocationMapStruct;
import com.htnova.system.tool.dto.LocationDto;
import com.htnova.system.tool.entity.Location;
import com.htnova.system.tool.service.LocationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.annotation.Resource;
import java.util.List;

/**
 * @menu 省市区
 */
@RestController
@RequestMapping("/location")
public class LocationController {

    @Resource
    private LocationService locationService;

    /**
     * 分页查询
     */
//    @PreAuthorize("hasAnyAuthority('location', 'location.find')")
    @GetMapping("/list/page")
    public XPage<LocationDto> findListByPage(LocationDto locationDto, XPage<Void> xPage) {
        IPage<Location> locationPage = locationService.findLocationList(locationDto, XPage.toIPage(xPage));
        return DtoConverter.toDto(locationPage, LocationMapStruct.class);
    }

    /**
     * 查询
     */
//    @PreAuthorize("hasAnyAuthority('location', 'location.find')")
    @GetMapping("/list/all")
    public List<LocationDto> findList(LocationDto locationDto) {
        List<Location> locationList = locationService.findLocationList(locationDto);
        return DtoConverter.toDto(locationList, LocationMapStruct.class);
    }

    /**
     * 获取省市区的tree
     */
    @GetMapping("/tree")
    public List<LocationDto> getLocationTree(LocationDto locationDto) {
        List<Location> locationList = locationService.findLocationList(locationDto);
        List<Location> treeList = TreeConverter.listToTree(locationList);
        return DtoConverter.toDto(treeList, LocationMapStruct.class);
    }

    /**
     * 详情
     */
//    @PreAuthorize("hasAnyAuthority('location', 'location.find')")
    @GetMapping("/detail/{id}")
    public LocationDto getById(@PathVariable long id) {
        Location location = locationService.getLocationById(id);
        return DtoConverter.toDto(location, LocationMapStruct.class);
    }

    /**
     * 保存
     */
//    @PreAuthorize("hasAnyAuthority('location', 'location.add', 'location.edit')")
    @PostMapping("/save")
    public Result<Void> save(@Valid @RequestBody LocationDto locationDto) {
        locationService.saveLocation(DtoConverter.toEntity(locationDto, LocationMapStruct.class));
        return Result.build(ResultStatus.SAVE_SUCCESS);

    }

    /**
     * 删除
     */
//    @PreAuthorize("hasAnyAuthority('location', 'location.del')")
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        locationService.deleteLocation(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
