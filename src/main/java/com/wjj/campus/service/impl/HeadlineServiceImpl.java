package com.wjj.campus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjj.campus.mapper.HeadlineMapper;
import com.wjj.campus.entity.Headline;
import com.wjj.campus.service.HeadlineService;
import com.wjj.campus.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/10
 * Time: 20:39
 * Description: 头条信息服务层实现
 *
 * @author jiajie.wan
 */
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper,Headline> implements HeadlineService {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(HeadlineService.class);

    /**
     * 注入头条信息服务持久层
     */
    private HeadlineMapper headlineMapper;

    @Override
    public List<Headline> queryHeadlineList(Integer number, Integer status) {
        return headlineMapper.queryHeadlineList(number, status);
    }

    @Override
    public File getHeadlineImage(String uuid) {
        Headline headline = headlineMapper.queryHeadlineImageByUUID(uuid);
        if (headline == null) {
            logger.error("获取文件出错！uuid = " + uuid);
            return null;
        }
        String headlineImagePath = PathUtils.getImageBasePath() + headline.getPicture();
        headlineImagePath = PathUtils.replaceFileSeparator(headlineImagePath);
        logger.info("图片路径"+headlineImagePath);
        assert headlineImagePath != null;
        return new File(headlineImagePath);
    }

    @Autowired
    public void setHeadlineMapper(HeadlineMapper headlineMapper) {
        this.headlineMapper = headlineMapper;
    }
}
