package com.wjj.campus.conf;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/13
 * Time: 16:33
 * Description: 订单配置类
 *
 * @author jiajie.wan
 */
public class OrderConfig {
    /**
     * 提交订单未处理
     */
    public static final int ORDER_UNPROCESSED_INDEX = 0;
    /**
     * 提交订单未处理
     */
    public static final String ORDER_UNPROCESSED_STRING = "提交订单未处理";
    /**
     * 商家已接单
     */
    public static final int ORDER_RECEIVED_INDEX = 1;
    /**
     * 商家已接单
     */
    public static final String ORDER_RECEIVED_STRING = "商家已接单";
    /**
     * 商家拒接订单
     */
    public static final int ORDER_REFUSE_INDEX = 2;
    /**
     * 商家拒接订单
     */
    public static final String ORDER_REFUSE_STRING = "商家拒接订单";
    /**
     * 用户取消订单
     */
    public static final int ORDER_USER_REFUSE_INDEX = 3;
    /**
     * 用户取消订单
     */
    public static final String ORDER_USER_REFUSE_STRING = "用户取消订单";

}
