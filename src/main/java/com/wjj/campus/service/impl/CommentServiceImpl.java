package com.wjj.campus.service.impl;

import com.wjj.campus.entity.Comment;
import com.wjj.campus.entity.LocalAccount;
import com.wjj.campus.entity.PersonInfo;
import com.wjj.campus.entity.Shop;
import com.wjj.campus.mapper.CommentMapper;
import com.wjj.campus.mapper.LocalAccountMapper;
import com.wjj.campus.mapper.PersonInfoMapper;
import com.wjj.campus.mapper.ShopMapper;
import com.wjj.campus.model.CommentDetail;
import com.wjj.campus.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/10
 * Time: 17:25
 * Description: 评论模块服务层实现类
 *
 * @author jiajie.wan
 */
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    /**
     * 注入用户信息持久化
     */
    private PersonInfoMapper personInfoMapper;

    /**
     * 注入评论信息数据持久化
     */
    private CommentMapper commentMapper;

//    @Autowired
    private ShopMapper shopMapper;

    //查询所有评论
    @Override
    public List<CommentDetail> getAllComment() {
        List<Comment> commentList = commentMapper.selectList(null);
        List<Integer> userIdList = commentList.stream().map(Comment::getUserId).distinct().collect(Collectors.toList());
        List<Integer> shopIdList = commentList.stream().map(Comment::getShopId).distinct().collect(Collectors.toList());
        if(userIdList.size()==0 || shopIdList.size()==0){
            return new ArrayList<CommentDetail>();
        }
//        shopMapper.selectById(1)
        Map<Integer, PersonInfo> personInfoMap = personInfoMapper.getRecordsByUserIds(userIdList);
        return commentList.stream().map(comment -> {
            CommentDetail temp = new CommentDetail();
            temp.setData(comment);
            temp.setUsername(personInfoMap.get(comment.getUserId()).getName());
            temp.setShopName(shopMapper.selectById(comment.getShopId()).getShopName());
            return temp;
        }).collect(Collectors.toList());
    }

    @Override
    public int deleteComment(int id) {
        return commentMapper.deleteById(id);
    }

    //查询所有shop下的评论
    @Override
    public List<CommentDetail> getCommentList(int shopId) {
        // 查询编号为shopId所有的评论
        List<Comment> commentList = commentMapper.queryCommentByShopId(shopId);
        // 提取评论列表内的用户id
        List<Integer> userIdList = commentList.stream().map(Comment::getUserId).distinct().collect(Collectors.toList());
        if (userIdList.size() == 0) {
            return new ArrayList<CommentDetail>();
        }
        Map<Integer, PersonInfo> personInfoMap = personInfoMapper.getRecordsByUserIds(userIdList);
        return commentList.stream().map(comment -> {
            CommentDetail temp = new CommentDetail();
            temp.setData(comment);
            temp.setUsername(personInfoMap.get(comment.getUserId()).getName());
            return temp;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean submitReviewData(Comment comment) {
        comment.setCreateTime(new Date());
        return commentMapper.addRecord(comment) > 0;
    }

    @Override
    public CommentDetail getCommentById(int id) {
        Comment comment = commentMapper.getCommentById(id);
        PersonInfo personInfo = personInfoMapper.queryById(comment.getUserId());
        CommentDetail commentDetail = new CommentDetail();
        commentDetail.setData(comment);
        commentDetail.setUsername(personInfo.getName());
        return commentDetail;
    }

    @Override
    public boolean checkCompetence(int shopId, Integer userId) {
        return commentMapper.getRecord(shopId,userId) > 0;
    }

    @Autowired
    public void setPersonInfoMapper(PersonInfoMapper personInfoMapper) {
        this.personInfoMapper = personInfoMapper;
    }

    @Autowired
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Autowired
    public void setShopMapper(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }
}
