package com.wjj.campus.controller.front;

import com.wjj.campus.entity.Shop;
import com.wjj.campus.model.JsonResponse;
import com.wjj.campus.service.AreaService;
import com.wjj.campus.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 获取商家信息
     * @param shopId
     * @return
     */
    @GetMapping(value = "/getShop")
    @ResponseBody
    public JsonResponse getShop(int shopId) {
        Shop shop = shopService.queryShopByShopId(shopId);
//        ShopAddressMap addressMap = new ShopAddressMap();
//        addressMap.setShopName(shop.getShopName());
//        String areaName = areaService.getById(shop.getAreaId()).getAreaName();
//        switch (areaName) {
//            case "东苑":
//                addressMap.setPosition("113.112455,27.818795");
//                break;
//            case "南苑":
//                addressMap.setPosition("113.108001,27.814485");
//                break;
//            case "西苑":
//                addressMap.setPosition("113.100267,27.814244");
//                break;
//            case "北苑":
//                addressMap.setPosition("113.106621,27.824477");
//                break;
//        }
//        Map<String, Object> map = new HashMap<>(2);
//        map.put("shop",shop);
//        map.put("addressMap",addressMap);
//        return JsonResponse.ok(map);
        return JsonResponse.ok(shop);
    }

    /**
     * 商铺地图位置
     * @param shopId
     * @return
     */
    @GetMapping("/shopMap")
    public ModelAndView shopMap(int shopId,HttpServletRequest request) throws UnsupportedEncodingException {
        String urlMap="https://uri.amap.com/marker?src=campusShop";
        Shop shop = shopService.queryShopByShopId(shopId);
        //拼接商家名称
        String shopName=shop.getShopName();
        //查询拼接经纬度，由于商家信息虚拟，此处仅大概位置
        String areaName = areaService.getById(shop.getAreaId()).getAreaName();
        switch (areaName) {
            case "东苑":
                urlMap+=("&position=113.112455,27.818795");
                break;
            case "南苑":
                urlMap+=("&position=113.108001,27.814485");
                break;
            case "西苑":
                urlMap+=("&position=113.100267,27.814244");
                break;
            case "北苑":
                urlMap+=("&position=113.106621,27.824477");
                break;
        }
        return new ModelAndView(new RedirectView(urlMap+URLEncoder.encode("&name="+areaName+" "+shopName,"UTF-8")));
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

    @Autowired
    private AreaService areaService;
}
