package com.wjj.campus.service;

import com.wjj.campus.entity.PersonInfo;
import com.wjj.campus.model.FileContainer;
import com.wjj.campus.model.JsonResponse;
import com.wjj.campus.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/16
 * Time: 9:14
 * Description: 用户信息服务接口
 *
 * @author jiajie.wan
 */
public interface PersonInfoService {

    /**
     * 根据 用户记录id 查询用户详细信息
     *
     * @param id 用户表记录id
     * @return 用户信息
     */
    PersonInfo getPersonInfo(int id);

    /**
     * 获取用户的头像文件
     *
     * @param userId 用户id
     * @return 用户头像文件
     */
    File getUserImage(Integer userId);

    /**
     * 删除用户头像
     *
     * @param userId 用户记录id
     */
    void deleteHeadImage(int userId);

    /**
     * 更新用户数据信息
     *
     * @param personInfo 用户信息
     * @param headImage  用户头像
     * @return 更新结果
     */
    JsonResponse updateMessage(PersonInfo personInfo, FileContainer headImage);

    /**
     * 修改用户的状态！
     *
     * @param userId 用户id
     * @param status 用户状态
     * @return 修改状态
     */
    boolean modifyUserStatus(int userId, boolean status);

    /**
     * 返回管理员页面所需要的所有用户列表
     *
     * @return 用户列表
     */
    List<User> getAdministratorUserList();
}
