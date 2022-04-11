package com.wjj.campus.controller;

import com.wjj.campus.entity.ProductCategory;
import com.wjj.campus.model.JsonResponse;
import com.wjj.campus.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/29
 * Time: 0:30
 * Description: 店铺商品类别控制器
 *
 * @author jiajie.wan
 */
@Controller
@RequestMapping("/productCategory")
public class ProductCategoryController {
    /**
     * 注入商品类别服务类
     */
    private ProductCategoryService productCategoryService;

    @GetMapping(value = "/getProductCategory")
    @ResponseBody
    public JsonResponse getProductCategoryByProductCategoryId(int productCategoryId) {
        ProductCategory productCategory = productCategoryService.queryProductCategoryByProductCategoryId(productCategoryId);
        return JsonResponse.ok(productCategory);
    }

    @GetMapping(value = "/getShopProductCategoryList")
    @ResponseBody
    public JsonResponse getProductCategoryListByShopId(int shopId) {
        List<ProductCategory> productCategoryList = productCategoryService.queryProductCategoryListByShopId(shopId);
        return JsonResponse.ok(productCategoryList);
    }

    @Autowired
    public void setProductCategoryService(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }
}
