package com.wjj.campus.service.impl;

import com.wjj.campus.mapper.AreaMapper;
import com.wjj.campus.entity.Area;
import com.wjj.campus.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/12
 * Time: 3:49
 * Description:
 *
 * @author jiajie.wan
 */
@Service
public class AreaServiceImpl implements AreaService {

    /**
     * 注入区域信息持久化单元
     */
    private AreaMapper areaMapper;

    @Override
    public List<Area> getAreaList() {
        List<Area> areas = areaMapper.queryAllArea();
        for (Area area : areas) {
            area.setCreateTime(null);
            area.setLastEditTime(null);
        }
        return areas;
    }

    @Override
    public List<Area> getRegisterAreaList() {
        return areaMapper.getRegisterAreaList();
    }

    @Override
    public boolean addRecord(Area a) {
        return areaMapper.addRecord(a) > 0;
    }

    @Autowired
    public void setAreaMapper(AreaMapper areaMapper) {
        this.areaMapper = areaMapper;
    }
}
