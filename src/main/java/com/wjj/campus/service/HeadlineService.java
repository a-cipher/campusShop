package com.wjj.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjj.campus.entity.Headline;

import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/10
 * Time: 20:35
 * Description: 头条信息服务接口
 *
 * @author jiajie.wan
 */
public interface HeadlineService extends IService<Headline> {

    /**
     * 查询头条数据
     *
     * @param number 查询数量
     * @param status 头条状态
     * @return 头条信息列表
     */
    List<Headline> queryHeadlineList(Integer number, Integer status);

    /**
     * 根据头条信息UUID获取该头条的图片
     *
     * @param uuid 唯一标志
     * @return 文件流
     */
    File getHeadlineImage(String uuid);
}
