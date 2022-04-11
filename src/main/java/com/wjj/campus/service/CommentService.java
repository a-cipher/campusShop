package com.wjj.campus.service;

import com.wjj.campus.entity.Comment;
import com.wjj.campus.model.CommentDetail;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/10
 * Time: 17:22
 * Description: 评论模块服务层
 *
 * @author jiajie.wan
 */
public interface CommentService {
    List<CommentDetail> getAllComment();

    int deleteComment(int id);

    /**
     * 获取编号为 shopId 的商铺的所有的评论信息
     *
     * @param shopId 商铺Id
     * @return 评论列表
     */
    List<CommentDetail> getCommentList(int shopId);

    /**
     * 提交一条评论，并持久化到数据库
     *
     * @param comment 评论记录
     * @return 添加记录
     */
    boolean submitReviewData(Comment comment);

    /**
     * 通过评论id查询评论内容
     *
     * @param id 评论id
     * @return 评论详情
     */
    CommentDetail getCommentById(int id);

    /**
     * 校验是否具有权限进行评论
     *
     * @param shopId 店铺id
     * @param userId 用户id
     * @return 校验结果
     */
    boolean checkCompetence(int shopId, Integer userId);
}
