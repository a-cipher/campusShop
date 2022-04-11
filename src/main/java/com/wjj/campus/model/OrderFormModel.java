package com.wjj.campus.model;

import com.wjj.campus.entity.OrderForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/13
 * Time: 14:36
 * Description:
 *
 * @author jiajie.wan
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderFormModel extends OrderForm {

    private static final long serialVersionUID = 2152528193412650134L;

    /**
     * 商品价格
     */
    private String price;
    /**
     * 商品名称
     */
    private String productName;

    public OrderFormModel() {
    }

    public OrderFormModel(OrderForm order) {
        this.setOrderId(order.getOrderId());
        this.setUserId(order.getUserId());
        this.setProductId(order.getProductId());
        this.setShopId(order.getShopId());
        this.setOrderUsername(order.getOrderUsername());
        this.setOrderPhone(order.getOrderPhone());
        this.setOrderAddress(order.getOrderAddress());
        this.setOrderUserRemark(order.getOrderUserRemark());
        this.setOrderShopRemark(order.getOrderShopRemark());
        this.setOrderStatus(order.getOrderStatus());
        this.setCreateTime(order.getCreateTime());
        this.setLastEditTime(order.getLastEditTime());
    }
}
