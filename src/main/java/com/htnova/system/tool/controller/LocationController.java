package com.htnova.system.tool.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.converter.DtoConverter;
import com.htnova.common.converter.TreeConverter;
import com.htnova.common.dto.Result;
import com.htnova.common.dto.XPage;
import com.htnova.system.tool.dto.LocationDto;
import com.htnova.system.tool.entity.Location;
import com.htnova.system.tool.mapstruct.LocationMapStruct;
import com.htnova.system.tool.service.LocationService;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** @menu 省市区 */
@RestController
@RequestMapping("/location")
public class LocationController {
    @Resource
    private LocationService locationService;

    /** 分页查询 */
    @PreAuthorize("hasAnyAuthority('location.find')")
    @GetMapping("/list/page")
    public XPage<LocationDto> findListByPage(LocationDto locationDto, XPage<Void> xPage) {
        IPage<Location> locationPage = locationService.findLocationList(locationDto, XPage.toIPage(xPage));
        return DtoConverter.toDto(locationPage, LocationMapStruct.INSTANCE);
    }

    /** 查询 */
    @PreAuthorize("hasAnyAuthority('location.find')")
    @GetMapping("/list/all")
    public List<LocationDto> findList(LocationDto locationDto) {
        List<Location> locationList = locationService.findLocationList(locationDto);
        return DtoConverter.toDto(locationList, LocationMapStruct.INSTANCE);
    }

    /** 获取省市区的tree */
    @PreAuthorize("hasAnyAuthority('location.find')")
    @GetMapping("/tree")
    public List<LocationDto> getLocationTree(LocationDto locationDto) {
        List<Location> locationList = locationService.findLocationList(locationDto);
        List<Location> treeList = TreeConverter.listToTree(locationList);
        return DtoConverter.toDto(treeList, LocationMapStruct.INSTANCE);
    }

    /** 详情 */
    @PreAuthorize("hasAnyAuthority('location.find')")
    @GetMapping("/detail/{id}")
    public LocationDto getById(@PathVariable long id) {
        Location location = locationService.getLocationById(id);
        return DtoConverter.toDto(location, LocationMapStruct.INSTANCE);
    }

    /** 保存 */
    @PreAuthorize("hasAnyAuthority('location.add', 'location.edit')")
    @PostMapping("/save")
    public Result<Void> save(@Valid @RequestBody LocationDto locationDto) {
        locationService.saveLocation(DtoConverter.toEntity(locationDto, LocationMapStruct.INSTANCE));
        return Result.build(ResultStatus.SAVE_SUCCESS);
    }

    /** 删除 */
    @PreAuthorize("hasAnyAuthority('location.del')")
    @DeleteMapping("/del/{id}")
    public Result<Void> delete(@PathVariable long id) {
        locationService.deleteLocation(id);
        return Result.build(ResultStatus.DELETE_SUCCESS);
    }
}
