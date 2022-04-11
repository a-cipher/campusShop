package com.wjj.campus.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/9
 * Time: 0:52
 * Description: 用户信息实体类
 *
 * @author jiajie.wan
 */
public class PersonInfo extends Base implements Serializable {

    private static final long serialVersionUID = 5567249435155066895L;
    /**
     * id
     */
    private Integer id;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 头像
     */
    private String headPortrait;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String gender;

    /**
     * 用户状态 0 禁止使用本商城 1允许使用本商城
     */
    private Integer enableStatus;

    /**
     * 用户身份标志
     * 1 顾客 2 店家 3 超级管理员
     */
    private Integer userType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后编辑时间
     */
    private Date lastEditTime;

    @Override
    public String toString() {
        return "PersonInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", headPortrait='" + headPortrait + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", enableStatus=" + enableStatus +
                ", userType=" + userType +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }
}
