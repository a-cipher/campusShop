package com.wjj.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjj.campus.entity.Area;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/12
 * Time: 3:48
 * Description: 区域信息服务类
 *
 * @author jiajie.wan
 */
public interface AreaService extends IService<Area> {
    /**
     * 获取区域信息
     *
     * @return 区域信息列表
     */
    List<Area> getAreaList();

    /**
     * 获取区域列表信息
     *
     * @return 用于注册店铺使用的区域信息
     */
    List<Area> getRegisterAreaList();

    boolean addRecord(Area a);
}
