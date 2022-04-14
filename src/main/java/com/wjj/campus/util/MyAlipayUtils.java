package com.wjj.campus.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 支付宝支付
 */
@Component
public class MyAlipayUtils {

    //配置文件值绑定，不能static
    @Value("${alipay.url}")
    private  String url;
    @Value("${alipay.appid}")
    private  String appid;
    @Value("${alipay.privateKey}")
    private  String privateKey;
    @Value("${alipay.publicKey}")
    private  String publicKey;
    @Value("${alipay.notifyUrl}")
    private  String notifyUrl;
    @Value("${alipay.returnUrl}")
    private  String returnUrl;

    /**
     * 下单
     * @param id 商家订单号必须唯一
     * @param price 金额
     * @param title 标题
     * @return
     * @throws AlipayApiException
     */
    public  String pay(String id, String price, String title,int productId,int shopId) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(url,appid,privateKey,"json","UTF-8",publicKey,"RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        System.out.println("正在进行支付...金额:"+price+"标题:"+title);
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl+"productId="+productId+"&shopId="+shopId);
        JSONObject bizContent = new JSONObject();
        //订单号
        bizContent.put("out_trade_no", id);
        //订单价格
        bizContent.put("total_amount", price);
        //订单标题
        bizContent.put("subject", title);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
//        bizContent.put("time_expire", "2022-08-01 22:00:00");

        //// 商品明细信息，按需传入
        //JSONArray goodsDetail = new JSONArray();
        //JSONObject goods1 = new JSONObject();
        //goods1.put("goods_id", "goodsNo1");
        //goods1.put("goods_name", "子商品1");
        //goods1.put("quantity", 1);
        //goods1.put("price", 0.01);
        //goodsDetail.add(goods1);
        //bizContent.put("goods_detail", goodsDetail);

        //// 扩展信息，按需传入
        //JSONObject extendParams = new JSONObject();
        //extendParams.put("sys_service_provider_id", "2088511833207846");
        //bizContent.put("extend_params", extendParams);

        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        //        if(response.isSuccess()){
////            System.out.println("调用成功");
//            return JsonResponse.ok("调用成功");
//        }
//        return JsonResponse.errorMsg("调用失败");
        return response.getBody();
    }

    /**
     *
     * @param id 商家订单号
     * @param price 退款金额
     * @param reason 退款理由
     * @return
     * @throws AlipayApiException
     */
    public AlipayTradeRefundResponse refund(String id, String price, String reason) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(url,appid,privateKey,"json","UTF-8",publicKey,"RSA2");
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", id);
        bizContent.put("refund_amount", price);
        bizContent.put("refund_reason", reason);
        System.out.println("退款金额"+price);
        System.out.println("订单号"+id);
        //// 返回参数选项，按需传入
        //JSONArray queryOptions = new JSONArray();
        //queryOptions.add("refund_detail_item_list");
        //bizContent.put("query_options", queryOptions);

        request.setBizContent(bizContent.toString());
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("退款成功");
        } else {
            System.out.println("退款失败");
        }
        return response;
    }
}
