package com.wjj.campus.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjj.campus.entity.Area;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/12
 * Time: 0:27
 * Description: 区域数据持久化操作
 *
 * @author jiajie.wan
 */
@Mapper
public interface AreaMapper extends BaseMapper<Area> {
    /**
     * 查询所有的区域信息
     *
     * @return 区域列表
     */
    List<Area> queryAllArea();

    /**
     * 获取注册的区域列表
     *
     * @return 区域列表
     */
    List<Area> getRegisterAreaList();

    int addRecord(Area a);
}
