package com.wjj.campus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjj.campus.entity.Comment;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/10
 * Time: 17:27
 * Description: 评论模块持久化层
 *
 * @author jiajie.wan
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 查询编号为 shopId 的评论
     *
     * @param shopId 商店编号
     * @return 评论信息
     */
    List<Comment> queryCommentByShopId(int shopId);

    /**
     * 将一条评论持久化到数据库中
     *
     * @param comment 评论详情
     * @return 持久化结果
     */
    int addRecord(Comment comment);

    /**
     * 通过评论id查询评论内容
     *
     * @param id 评论id
     * @return 评论详情
     */
    Comment getCommentById(int id);

    /**
     * 查询符合的记录条数
     *
     * @param shopId 商铺id
     * @param userId 用户id
     * @return 记录条数
     */
    int getRecord(int shopId, Integer userId);
}
