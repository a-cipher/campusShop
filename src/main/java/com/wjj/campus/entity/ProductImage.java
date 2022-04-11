package com.wjj.campus.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/11
 * Time: 21:00
 * Description: 商品图片实体类
 *
 * @author jiajie.wan
 */
public class ProductImage extends Base implements Serializable {
    /**
     * 商品图片id
     */
    private Integer productImgId;

    /**
     * 商品图片路径
     */
    private String imgPath;

    /**
     * 商品图片描述
     */
    private String imgDesc;

    /**
     * 商品图片权重
     */
    private Integer priority;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 唯一标识UUID
     */
    private String uuid;

    @Override
    public String toString() {
        return "ProductImage{" +
                "productImgId=" + productImgId +
                ", imgPath='" + imgPath + '\'' +
                ", imgDesc='" + imgDesc + '\'' +
                ", priority=" + priority +
                ", uuid='" + uuid + '\'' +
                ", createTime=" + createTime +
                ", productId=" + productId +
                '}';
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getProductImgId() {
        return productImgId;
    }

    public void setProductImgId(Integer productImgId) {
        this.productImgId = productImgId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
