package com.wjj.campus.entity;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/9
 * Time: 0:23
 * Description: 区域实体类
 *
 * @author jiajie.wan
 */
public class Area extends Base implements Serializable {

    /**
     * id
     */
    @TableId
    private Integer areaId;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 描述
     */
    private String areaDescription;

    /**
     * 权重
     */
    private Integer priority;

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
        return "Area{" +
                "areaId=" + areaId +
                ", areaName='" + areaName + '\'' +
                ", areaDescription='" + areaDescription + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                '}';
    }

    public String getAreaDescription() {
        return areaDescription;
    }

    public void setAreaDescription(String areaDescription) {
        this.areaDescription = areaDescription;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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
