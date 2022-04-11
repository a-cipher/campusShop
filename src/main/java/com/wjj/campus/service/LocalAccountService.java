package com.wjj.campus.service;

import com.wjj.campus.entity.LocalAccount;
import com.wjj.campus.model.JsonResponse;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/16
 * Time: 7:33
 * Description: 本地账号服务接口
 *
 * @author jiajie.wan
 */
public interface LocalAccountService {

    /**
     * 用户登录
     *
     * @param localAccount 用户信息
     * @return 登录结果：1 - 登录成功
     * 2 - 登录失败
     * 3 - 账号未启用
     */
    int login(LocalAccount localAccount);

    /**
     * 用户注册
     *
     * @param localAccount 用户信息
     * @return 注册结果
     */
    JsonResponse register(LocalAccount localAccount);

    /**
     * 修改用户登录面
     *
     * @param originalPassword 原始密码
     * @param newPassword      新密码
     * @param user             用户信息
     * @return 修改结果
     */
    boolean modifyPassword(String originalPassword, String newPassword, LocalAccount user);

    /**
     * 通过用户名查询用户本地账号
     * @param username 用户名
     * @return 用户本地信息
     */
    LocalAccount getLocalAccount(String username);
}
