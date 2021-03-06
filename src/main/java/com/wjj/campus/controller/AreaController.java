package com.wjj.campus.controller;

import com.wjj.campus.entity.Area;
import com.wjj.campus.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/12
 * Time: 3:56
 * Description:
 *
 * @author jiajie.wan
 */
@Controller
@RequestMapping(value = "/area")
public class AreaController {

    /**
     * 注入区域信息服务
     */
    private AreaService areaService;

    @RequestMapping(value = "/listArea", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listArea() {
        Map<String, Object> modelMap = new HashMap<>(2);
        List<Area> areaList = areaService.getAreaList();
        modelMap.put("rows", areaList);
        modelMap.put("total", areaList.size());
        return modelMap;
    }

    @Autowired
    public void setAreaService(AreaService areaService) {
        this.areaService = areaService;
    }
}
