package com.wjj.campus.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjj.campus.conf.OrderConfig;
import com.wjj.campus.entity.LocalAccount;
import com.wjj.campus.entity.OrderForm;
import com.wjj.campus.entity.Product;
import com.wjj.campus.model.JsonResponse;
import com.wjj.campus.model.OrderFormModel;
import com.wjj.campus.service.OrderService;
import com.wjj.campus.service.ProductService;
import com.alibaba.fastjson.JSON;
import com.sun.jdi.IntegerType;
import com.wjj.campus.util.CommonUtils;
import com.wjj.campus.util.MyAlipayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/13
 * Time: 3:33
 * Description: 订单控制器
 *
 * @author jiajie.wan
 */
@RequestMapping(value = "/order")
@Controller
public class OrderController {

    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * 注入订单服务
     */
    @Autowired
    private OrderService orderService;

    /**
     * 注入商品服务
     */
    private ProductService productService;

    @Autowired
    private MyAlipayUtils myAlipayUtils;

    /**
     * 请求到商家的订单页面
     *
     * @return 页面跳转
     */
    @GetMapping(value = "/listShopOrder")
    public ModelAndView listShopOrder(@RequestParam("shopId") int shopId,
                                      @RequestParam(value = "flag", required = false) Integer flag) {
        ModelAndView view = new ModelAndView("order/listOrder");
        logger.debug("请求订单列表的店铺ID = " + shopId);
        // 查询该店铺所有的订单
        List<OrderForm> orderFormList = null;
        if (flag == null) {
            orderFormList = orderService.getOrderListByShopId(shopId, null);
        } else if (flag == 1) {
            // 已完成订单
            orderFormList = orderService.getOrderListByShopId(shopId, 1);
        } else {
            // 未完成订单
            orderFormList = orderService.getOrderListByShopId(shopId, 0);
        }
        if (orderFormList == null || orderFormList.size() == 0) {
            return view;
        }
        List<OrderFormModel> list = orderFormList.stream().map(order -> {
            Product p = productService.getProductByProductId(order.getProductId());
            OrderFormModel t = new OrderFormModel(order);
            t.setProductName(p.getProductName());
            if (p.getPromotionPrice() != null) {
                t.setPrice(p.getPromotionPrice().toString());
            } else {
                t.setPrice(p.getNormalPrice().toString());
            }
            return t;
        }).collect(Collectors.toList());
        view.addObject("orderFormList", list);
        return view;
    }

    /**
     * 用户的所有订单
     *
     * @return 页面跳转
     */
    @GetMapping(value = "/listUserOrder")
    public ModelAndView listUserOrder(@RequestParam(value = "flag", required = false) Integer flag,
                                      HttpServletRequest request) {
        ModelAndView view = new ModelAndView("order/userOrderList");
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        logger.debug("用户ID = " + user.getUserId());
        int userId=user.getUserId();
        // 查询该用户所有的订单
        List<OrderForm> orderFormList = null;
        orderFormList=orderService.getOrderListByUserId(userId,flag);
        if (orderFormList == null || orderFormList.size() == 0) {
            return view;
        }
        List<OrderFormModel> list = orderFormList.stream().map(order -> {
            Product p = productService.getProductByProductId(order.getProductId());
            OrderFormModel t = new OrderFormModel(order);
            t.setProductName(p.getProductName());
            if (p.getPromotionPrice() != null) {
                t.setPrice(p.getPromotionPrice().toString());
            } else {
                t.setPrice(p.getNormalPrice().toString());
            }
            return t;
        }).collect(Collectors.toList());
        view.addObject("orderFormList", list);
        return view;
    }

    /**
     * 提交订单到服务器，并完成支付
     *
     * @param request          请求会话
     * @param orderInformation 订单信息
     * @return 请求结果
     */
    @PostMapping(value = "/submitOrder")
    @ResponseBody
    public JsonResponse submitOrder(HttpServletRequest request, Model model,
                                    @RequestParam("orderInformation") String orderInformation,
                                    @RequestParam("price") String price,
                                    @RequestParam("productName") String productName) throws AlipayApiException {
        logger.debug("订单信息：" + orderInformation);
        if (StringUtils.isBlank(orderInformation)) {
            return JsonResponse.errorMsg("提交信息不能为空！");
        }
        LocalAccount user = (LocalAccount) request.getSession().getAttribute("user");
        OrderForm orderForm = JSON.parseObject(orderInformation, OrderForm.class);
        if (user != null) {
            orderForm.setUserId(user.getUserId());
        } else {
            return JsonResponse.errorMsg("未登录，获取用户信息出错！");
        }
        logger.debug("转换的json对对象！" + orderForm.toString());
        //订单id  商品id  商铺id
        String orderId = CommonUtils.getUniversallyUniqueIdentifier();
        int productId = orderForm.getProductId();
        int shopId = orderForm.getShopId();
        orderForm.setOrderId(orderId);
        if (orderService.addRecord(orderForm)) {
            String pay = myAlipayUtils.pay(orderId,price,productName,productId,shopId);
            model.addAttribute("form",pay);
            return JsonResponse.ok("即将跳转...",pay);
        } else {
            return JsonResponse.errorMsg("添加失败！");
        }
    }

    /**
     * 商家确认接收订单
     *
     * @param orderId 订单id
     * @param message 商家备注信息
     * @return 请求结果
     */
    @PostMapping(value = "/confirmOrder")
    @ResponseBody
    public JsonResponse confirmOrder(@RequestParam("orderId") String orderId,
                                     @RequestParam("orderStatus") int orderStatus,
                                     @RequestParam("message") String message) {
        //商家只能接单状态为0的订单
        if (orderStatus != 0){
            return JsonResponse.errorMsg("该订单状态无法接单");
        }
        if (orderService.confirmOrder(orderId, message)) {
            Map<String, Object> map = new HashMap<>(2);
            OrderForm orderForm = orderService.getRecordByOrderId(orderId);
            if (orderForm.getOrderStatus() == OrderConfig.ORDER_RECEIVED_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_RECEIVED_STRING);
            } else if (orderForm.getOrderStatus() == OrderConfig.ORDER_UNPROCESSED_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_UNPROCESSED_INDEX);
            } else {
                map.put("orderStatus", OrderConfig.ORDER_REFUSE_STRING);
            }
            map.put("shopRemark", orderForm.getOrderShopRemark());
            return JsonResponse.ok(map);
        }
        return JsonResponse.errorMsg("订单处理失败！");
    }

    /**
     * 确认拒绝订单，并在用户付款的情况下需要退款
     *
     * @param orderId 订单id
     * @param message 订单备注
     * @return 请求结果
     */
    @PostMapping(value = "/rejectOrder")
    @ResponseBody
    public JsonResponse rejectOrder(@RequestParam("orderId") String orderId,
                                    @RequestParam("orderStatus") int orderStatus,
                                    @RequestParam("price") String price,
                                    @RequestParam("message") String message) throws AlipayApiException {
        //拒单操作只针对0，1
        if (orderStatus!=0 && orderStatus!=1){
            return JsonResponse.errorMsg("该订单状态无法拒单");
        }
        if (orderService.rejectOrder(orderId, message)) {
            Map<String, Object> map = new HashMap<>(2);
            OrderForm orderForm = orderService.getRecordByOrderId(orderId);
            AlipayTradeRefundResponse refund = myAlipayUtils.refund(orderId, price, message);
            //退款失败，原因一般为订单号不存在于支付宝，直接删除该订单
            if(!refund.isSuccess()){
//                orderService.removeById(orderId);
                return JsonResponse.errorMsg("异常订单号，无法处理");
            }
            if (orderForm.getOrderStatus() == OrderConfig.ORDER_RECEIVED_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_RECEIVED_STRING);
            } else if (orderForm.getOrderStatus() == OrderConfig.ORDER_UNPROCESSED_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_UNPROCESSED_INDEX);
            } else {
                map.put("orderStatus", OrderConfig.ORDER_REFUSE_STRING);
            }
            map.put("shopRemark", orderForm.getOrderShopRemark());
            return JsonResponse.ok(map);
        }
        return JsonResponse.errorMsg("订单处理失败！");
    }

    /**
     * 用户取消订单，直接退款
     * @param orderId
     * @param message
     * @return
     */
    @PostMapping(value = "/userRejectOrder")
    @ResponseBody
    public JsonResponse userRejectOrder(@RequestParam("orderId") String orderId,
                                        @RequestParam("orderStatus") int orderStatus,
                                        @RequestParam("price") String price,
                                        @RequestParam("message") String message) throws AlipayApiException {
        //取消订单操作只针对0，1
        if (orderStatus!=0 && orderStatus!=1){
            return JsonResponse.errorMsg("该订单状态无法取消订单");
        }
        if (orderService.userRejectOrder(orderId, message)) {
            Map<String, Object> map = new HashMap<>(2);
            OrderForm orderForm = orderService.getRecordByOrderId(orderId);
            AlipayTradeRefundResponse refund = myAlipayUtils.refund(orderId, price, message);
            //退款失败，原因一般为订单号不存在于支付宝，直接删除该订单
            if(!refund.isSuccess()){
//                orderService.removeById(orderId);
                return JsonResponse.errorMsg("异常订单号，无法处理");
            }
            if (orderForm.getOrderStatus() == OrderConfig.ORDER_RECEIVED_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_RECEIVED_STRING);
            } else if (orderForm.getOrderStatus() == OrderConfig.ORDER_UNPROCESSED_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_UNPROCESSED_INDEX);
            } else if (orderForm.getOrderStatus() == OrderConfig.ORDER_REFUSE_INDEX){
                map.put("orderStatus", OrderConfig.ORDER_REFUSE_STRING);
            } else if (orderForm.getOrderStatus() == OrderConfig.ORDER_USER_REFUSE_INDEX){
                map.put("orderStatus", OrderConfig.ORDER_USER_REFUSE_STRING);
            }
            map.put("shopRemark", orderForm.getOrderShopRemark());
            return JsonResponse.ok(map);
        }
        return JsonResponse.errorMsg("订单处理失败！");
    }

    /**
     * 用户确认收货
     * @param orderId
     * @param message
     * @return
     */
    @PostMapping(value = "/userConfirmOrder")
    @ResponseBody
    public JsonResponse userConfirmOrder(@RequestParam("orderId") String orderId,
                                        @RequestParam("orderStatus") int orderStatus,
                                        @RequestParam("price") String price,
                                        @RequestParam("message") String message) throws AlipayApiException {
        //取消订单操作只针对0，1
        if (orderStatus != 1) {
            return JsonResponse.errorMsg("该订单状态无法确认收货");
        }
        if (orderService.userConfirmOrder(orderId, message)) {
            Map<String, Object> map = new HashMap<>(2);
            OrderForm orderForm = orderService.getRecordByOrderId(orderId);
            if (orderForm.getOrderStatus() == OrderConfig.ORDER_RECEIVED_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_RECEIVED_STRING);
            } else if (orderForm.getOrderStatus() == OrderConfig.ORDER_UNPROCESSED_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_UNPROCESSED_INDEX);
            } else if (orderForm.getOrderStatus() == OrderConfig.ORDER_REFUSE_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_REFUSE_STRING);
            } else if (orderForm.getOrderStatus() == OrderConfig.ORDER_USER_REFUSE_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_USER_REFUSE_STRING);
            } else if (orderForm.getOrderStatus() == OrderConfig.ORDER_USER_CONFIRM_INDEX) {
                map.put("orderStatus", OrderConfig.ORDER_USER_CONFIRM_STRING);
            }
            map.put("shopRemark", orderForm.getOrderShopRemark());
            return JsonResponse.ok(map);
        }
        return JsonResponse.errorMsg("订单处理失败！");
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
