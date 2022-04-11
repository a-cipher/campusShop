package com.wjj.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjj.campus.entity.OrderForm;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/13
 * Time: 4:31
 * Description: 订单服务类
 *
 * @author jiajie.wan
 */
public interface OrderService extends IService<OrderForm> {

    /**
     * 添加一条订单记录
     *
     * @param orderForm 订单信息
     * @return 添加结果
     */
    boolean addRecord(OrderForm orderForm);

    /**
     * 根据商铺shopId查询所有订单信息
     *
     * @param shopId 商铺的shopId
     * @param flag   请求参数
     * @return 订单列表
     */
    List<OrderForm> getOrderListByShopId(int shopId, Integer flag);

    /**
     * 查询指定用户的所有订单
     * @param userId
     * @return
     */
    List<OrderForm> getOrderListByUserId(int userId ,Integer flag);

    /**
     * 确认接收订单
     *
     * @param orderId 订单id
     * @param message 订单备注
     * @return 请求结果
     */
    boolean confirmOrder(String orderId, String message);

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderId 订单编号
     * @return 订单信息
     */
    OrderForm getRecordByOrderId(String orderId);

    /**
     * 拒接订单
     *
     * @param orderId 订单id
     * @param message 订单备注
     * @return 请求结果
     */

    boolean rejectOrder(String orderId, String message);

    /**
     * 取消订单
     *
     * @param orderId 订单id
     * @param message 订单备注
     * @return 请求结果
     */

    boolean userRejectOrder(String orderId, String message);
}
