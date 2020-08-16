package com.htnova.system.tool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.htnova.system.tool.dto.LocationDto;
import com.htnova.system.tool.entity.Location;
import com.htnova.system.tool.mapper.LocationMapper;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService extends ServiceImpl<LocationMapper, Location> {
    @Resource
    private LocationMapper locationMapper;

    @Transactional(readOnly = true)
    public IPage<Location> findLocationList(LocationDto locationDto, IPage<Void> xPage) {
        return locationMapper.findPage(xPage, locationDto);
    }

    @Transactional(readOnly = true)
    public List<Location> findLocationList(LocationDto locationDto) {
        return locationMapper.findList(locationDto);
    }

    @Transactional
    public void saveLocation(Location location) {
        super.saveOrUpdate(location);
    }

    @Transactional(readOnly = true)
    public Location getLocationById(long id) {
        return locationMapper.selectById(id);
    }

    @Transactional
    public void deleteLocation(Long id) {
        super.removeById(id);
    }

    /** 传入区域code，返回拼接好的中文 */
    public String getLocationString(Integer... locationId) {
        List<Location> list = super.lambdaQuery().in(Location::getId, Arrays.asList(locationId)).list();
        return list.stream().map(Location::getName).collect(Collectors.joining());
    }
}
