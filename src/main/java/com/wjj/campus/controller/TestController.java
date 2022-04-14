package com.wjj.campus.controller;

import com.alipay.api.AlipayApiException;
import com.wjj.campus.model.JsonResponse;
import com.wjj.campus.util.MyAlipayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/2
 * Time: 3:18
 * Description:
 *
 * @author jiajie.wan
 */

@Controller
public class TestController {
    @Autowired
    MyAlipayUtils myAlipayUtils;

    /**
     * 测试支付宝支付功能
     * @param id
     * @param price
     * @param title
     * @param model
     * @return
     * @throws AlipayApiException
     */
    @PostMapping("/create")
    @ResponseBody
    public String create(String id, String price, String title, Model model) throws AlipayApiException {
        String pay = myAlipayUtils.pay(id,price,title,29,20);
        model.addAttribute("form",pay);
        return pay;
//        return "";
    }

//    @GetMapping("/refund")
//    @ResponseBody
//    public String refund(String id, String price, String title) throws AlipayApiException {
//        return myAlipayUtils.refund(id, price, title);
//    }

    /**
     * 支付成功以后跳转的页面
     * @return
     */
    @GetMapping("/return")
    @ResponseBody
    public String returnNotice(){
        return "成功支付";
    }



}
