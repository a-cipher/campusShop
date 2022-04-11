package com.wjj.campus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjj.campus.entity.OrderForm;
import com.wjj.campus.mapper.OrderFormMapper;
import com.wjj.campus.service.OrderService;
import com.wjj.campus.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/13
 * Time: 4:33
 * Description:
 *
 * @author jiajie.wan
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderFormMapper,OrderForm> implements OrderService {

    /**
     * 注入订单服务持久层
     */
    private OrderFormMapper orderFormMapper;

    @Override
    public boolean addRecord(OrderForm orderForm) {
        orderForm.setOrderId(CommonUtils.getUniversallyUniqueIdentifier());
        orderForm.setCreateTime(new Date());
        orderForm.setLastEditTime(new Date());
        // 默认设置为提交订单未处理状态
        orderForm.setOrderStatus(0);
        return orderFormMapper.addRecord(orderForm) > 0;
    }

    @Override
    public List<OrderForm> getOrderListByShopId(int shopId, Integer flag) {
        if (flag == null) {
            return orderFormMapper.getOrderListByShopId(shopId);
        } else if (flag == 1) {
            return orderFormMapper.getOrderListByShopIdCompleted(shopId);
        } else {
            return orderFormMapper.getOrderListByShopIdUnfinished(shopId);
        }
    }

    @Override
    public List<OrderForm> getOrderListByUserId(int userId,Integer flag) {
        QueryWrapper<OrderForm> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        if(flag==null){
            log.debug("查询全部");
        }else if(flag==0){
            wrapper.eq("order_status",0);
        }else if(flag==1){
            wrapper.eq("order_status",1);
        }else if(flag==2){
            wrapper.ne("order_status",0);
            wrapper.ne("order_status",1);
        }
        return orderFormMapper.selectList(wrapper);
    }

    @Override
    public boolean confirmOrder(String orderId, String message) {
        OrderForm orderForm = new OrderForm();
        orderForm.setOrderId(orderId);
        //      * 订单状态，0-提交订单未处理 ， 1-商家已接单， 2-商家拒接订单,
        orderForm.setOrderStatus(1);
        orderForm.setOrderShopRemark(message);
        orderForm.setLastEditTime(new Date());
        return orderFormMapper.modifyOrder(orderForm) > 0;
    }

    @Override
    public boolean rejectOrder(String orderId, String message) {
        OrderForm orderForm = new OrderForm();
        orderForm.setOrderId(orderId);
        //      * 订单状态，0-提交订单未处理 ， 1-商家已接单， 2-商家拒接订单, 3-用户取消
        orderForm.setOrderStatus(2);
        orderForm.setOrderShopRemark(message);
        orderForm.setLastEditTime(new Date());
        return orderFormMapper.modifyOrder(orderForm) > 0;
    }

    @Override
    public boolean userRejectOrder(String orderId, String message) {
        OrderForm orderForm = new OrderForm();
        orderForm.setOrderId(orderId);
        //      * 订单状态，0-提交订单未处理 ， 1-商家已接单， 2-商家拒接订单, 3-用户取消
        orderForm.setOrderStatus(3);
        orderForm.setOrderUserRemark(message);
        orderForm.setLastEditTime(new Date());
        return orderFormMapper.modifyOrder(orderForm) > 0;
    }

    @Override
    public OrderForm getRecordByOrderId(String orderId) {
        return orderFormMapper.getRecordByOrderId(orderId);
    }

    @Autowired
    public void setOrderFormMapper(OrderFormMapper orderFormMapper) {
        this.orderFormMapper = orderFormMapper;
    }
}
