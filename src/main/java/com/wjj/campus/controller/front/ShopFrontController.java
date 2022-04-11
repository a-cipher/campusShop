package com.wjj.campus.controller.front;

import com.wjj.campus.entity.Shop;
import com.wjj.campus.model.JsonResponse;
import com.wjj.campus.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/12
 * Time: 22:43
 * Description: 前端商铺控制器
 *
 * @author jiajie.wan
 */
@Controller
@RequestMapping(value = "/frontShop")
public class ShopFrontController {
    /**
     * 日志记录器
     */
    private static final Logger logger = LoggerFactory.getLogger(ShopFrontController.class);

    /**
     * 注入商铺服务层
     */
    private ShopService shopService;

    /**
     * 请求跳转到商铺列表展示页面（前端展示）
     *
     * @return 页面跳转
     */
    @GetMapping(value = "/list")
    public String shopList() {
        return "shop/shopList";
    }

    @GetMapping(value = "/getShop")
    @ResponseBody
    public JsonResponse getShop(int shopId) {
        Shop shop = shopService.queryShopByShopId(shopId);
        return JsonResponse.ok(shop);
    }

    @GetMapping(value = "/shop")
    public String enterShop(int shopId){
        return "shop/shopDetail";
    }

    /**
     * 初始化店铺展示列表页数据
     *
     * @return 数据集合
     */
    public JsonResponse initPage() {
        return null;
    }

    @Autowired
    public void setShopService(ShopService shopService) {
        this.shopService = shopService;
    }
}
