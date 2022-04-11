package com.wjj.campus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/12
 * Time: 4:11
 * Description:
 *
 * @author jiajie.wan
 */
@Controller
public class IndexController {
    /**
     * 请求跳转到首页
     *
     * @return 首页
     */
    @GetMapping(value = "/home")
    public String homePage() {
        return "homePage";
    }

    /**
     * 请求跳转到首页
     *
     * @return 首页
     */
    @GetMapping(value = "/")
    public String homePage1() {
        return "homePage";
    }

    @GetMapping(value = "/test")
    public String test() {
        return "test";
    }
}
