package com.wjj.campus.model;

import com.wjj.campus.entity.Comment;
import lombok.Data;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/10
 * Time: 17:17
 * Description: 评论展示需要的模型
 *
 * @author jiajie.wan
 */
@Data
public class CommentDetail {
    /**
     * 评论Id
     */
    private Integer id;
    /**
     * 评论者ID
     */
    private Integer userId;
    /**
     * 评论商铺ID
     */
    private Integer shopId;

    /**
     * 评论内容
     */
    private String detail;

    /**
     * 评论时间
     */
    private Long createTime;

    /**
     * 评论用户昵称
     */
    private String username;

    /**
     * 商家名称
     */
    private String shopName;

    public void setData(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUserId();
        this.shopId = comment.getShopId();
        this.detail = comment.getDetail();
        this.createTime = comment.getCreateTime().getTime();
    }
}
