package com.wjj.campus.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjj.campus.entity.Comment;
import com.wjj.campus.entity.Headline;
import com.wjj.campus.entity.OrderForm;
import com.wjj.campus.entity.Shop;
import com.wjj.campus.mapper.HeadlineMapper;
import com.wjj.campus.mapper.ShopMapper;
import com.wjj.campus.service.AreaService;
import com.wjj.campus.service.CommentService;
import com.wjj.campus.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HeadlineUpdateUtils {
    @Autowired
    private HeadlineMapper headlineMapper;
    
    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private AreaService areaService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommentService commentService;

    /**
     * 定时任务，每天2点自动执行
     * 头条计算：区域权重 + 订单数*3 + 评论数*2
     */
    @Scheduled(cron = "0 0 15 * * *")
    public void updateHeadline(){
        List<Shop> shops = shopMapper.selectList(null);
        Map<Shop, Integer> map = new HashMap<>();
        //获取对应商铺及其权重
        for (Shop shop : shops) {
            int areaLevel = areaService.getById(shop.getAreaId()).getPriority();
            QueryWrapper<OrderForm> orderWrapper = new QueryWrapper<>();
            orderWrapper.eq("shop_id",shop.getShopId());
            int orderLevel = (int) orderService.count(orderWrapper)*3;
            QueryWrapper<Comment> commentWrapper = new QueryWrapper<>();
            commentWrapper.eq("shop_id",shop.getShopId());
            int commentLevel = (int) commentService.count(commentWrapper)*2;
            map.put(shop,areaLevel+orderLevel+commentLevel);
        }

        //处理对应信息到数据库中
        for (Map.Entry<Shop, Integer> entry : map.entrySet()) {
            QueryWrapper<Headline> wrapper = new QueryWrapper<>();
            wrapper.eq("name",entry.getKey().getShopName());
            Headline headline = headlineMapper.selectOne(wrapper);
            //添加不存在商铺信息，更新已存在信息
            if(headline==null){
                Headline newHeadline = new Headline();
                newHeadline.setName(entry.getKey().getShopName());
                newHeadline.setLinked("/frontShop/shop?shopId="+entry.getKey().getShopId());
                newHeadline.setPicture(entry.getKey().getShopImg());
                newHeadline.setPriority(entry.getValue());
                newHeadline.setStatus(1);
                newHeadline.setCreateTime(new Date(System.currentTimeMillis()));
                newHeadline.setLastEditTime(new Date(System.currentTimeMillis()));
                newHeadline.setUuid(CommonUtils.getUUID());
                headlineMapper.insert(newHeadline);
            }else{
                headline.setPriority(entry.getValue());
                headline.setLastEditTime(new Date(System.currentTimeMillis()));
                headlineMapper.updateById(headline);
            }

        }
    }
}
