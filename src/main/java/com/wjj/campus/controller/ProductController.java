package com.wjj.campus.controller;

import com.alibaba.fastjson.JSON;
import com.wjj.campus.entity.Product;
import com.wjj.campus.entity.ProductCategory;
import com.wjj.campus.model.FileContainer;
import com.wjj.campus.model.JsonResponse;
import com.wjj.campus.service.ProductCategoryService;
import com.wjj.campus.service.ProductImageService;
import com.wjj.campus.service.ProductService;
import com.wjj.campus.util.ChenHttpUtils;
import com.wjj.campus.util.CommonStrings;
import com.wjj.campus.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/30
 * Time: 1:55
 * Description: 商品服务控制器
 *
 * @author jiajie.wan
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    /**
     * 日志记录
     */
    private static Logger logger = LoggerFactory.getLogger(ProductController.class);

    /**
     * 注入商品服务层
     */
    private ProductService productService;

    /**
     * 注入商品图片服务层
     */
    private ProductImageService productImageService;

    /**
     * 注入商品类别服务类
     */
    private ProductCategoryService productCategoryService;

    @GetMapping(value = "/addProduct")
    public String addProduct(Integer shopId, HttpServletRequest request) {
        request.getSession().setAttribute(CommonStrings.SHOP_ID, shopId);
        return "product/addProduct";
    }

    /**
     * 添加商品信息
     *
     * @param request               请求会话
     * @param product               商品信息
     * @param verifyCode            验证码
     * @param thumbnail             商品缩略图
     * @param productDetailsPicture 商品详情图
     * @return 添加结果
     * @throws UnsupportedEncodingException 不支持的编码类型
     */
    @PostMapping(value = "/addProduct")
    @ResponseBody
    public JsonResponse addProduct(HttpServletRequest request,
                                   @RequestParam("product") String product,
                                   @RequestParam("verifyCode") String verifyCode,
                                   @RequestParam(value = "thumbnail", required = false) MultipartFile thumbnail,
                                   @RequestParam(value = "productDetailsPicture", required = false) MultipartFile[] productDetailsPicture) throws UnsupportedEncodingException {
        logger.debug("上传的商品详情图片数量 = " + productDetailsPicture.length);
        // 图片验证码为空
        if (StringUtils.isBlank(product)) {
            return JsonResponse.errorMsg("提交的商品信息为空!");
        }
        // 图片验证码为空
        if (StringUtils.isBlank(verifyCode)) {
            return JsonResponse.errorMsg("验证码不能为空!");
        } else {
            String cacheCode = (String) request.getSession().getAttribute(CommonStrings.VALIDATE_CODE);
            // 验证码错误
            if (!(cacheCode.toLowerCase().equals(verifyCode.toLowerCase()))) {
                return JsonResponse.errorMsg("验证码错误！");
            }
        }
        // 商品信息
        Product p = JSON.parseObject(product, Product.class);
        // 缩略图
        FileContainer productThumbnail = null;
        if (thumbnail != null) {
            productThumbnail = new FileContainer();
            try {
                productThumbnail.setFileInputStream(thumbnail.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            productThumbnail.setFileName(thumbnail.getOriginalFilename());
        }
        // 处理详情图片
        List<FileContainer> fileContainerList = null;
        fileContainerList = new ArrayList<>(productDetailsPicture.length);
        for (MultipartFile temp : productDetailsPicture) {
            FileContainer t = new FileContainer();
            try {
                t.setFileInputStream(temp.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            t.setFileName(temp.getOriginalFilename());
            fileContainerList.add(t);
        }
        Integer shopId = (Integer) request.getSession().getAttribute(CommonStrings.SHOP_ID);
        if (shopId == null && p.getShopId() == null) {
            return JsonResponse.errorMsg("没有指明店铺！");
        }
        if (shopId != null) {
            p.setShopId(shopId);
        }
        logger.debug("开始进行添加产品信息...");
        return productService.addProduct(p, productThumbnail, fileContainerList);
    }

    /**
     * 请求跳转到编辑商品的页面
     *
     * @param productId 商品id
     * @return 商品变价页面
     */
    @GetMapping(value = "/modifyProduct")
    public String modifyProduct(int productId, HttpServletRequest request) {
        request.getSession().setAttribute(CommonStrings.PRODUCT_ID, productId);
        return "product/modifyProduct";
    }

    @PostMapping(value = "/modifyProduct")
    @ResponseBody
    public JsonResponse modifyProduct(HttpServletRequest request) throws UnsupportedEncodingException {
        HashMap<String, String> paramMap = new HashMap<>(4);
        paramMap.put("product", "修改商品信息");
        paramMap.put("verifyCode", "验证码");
        Map<String, Object> uploadMessage = ChenHttpUtils.doUploadMessage(request, paramMap);
        String verifyCode = (String) uploadMessage.get("verifyCode");
        if (uploadMessage.get(CommonStrings.ERROR) != null) {
            return (JsonResponse) uploadMessage.get(CommonStrings.ERROR);
        }
        List<Object> list = CommonUtils.validateCode(verifyCode, request);
        if (list.size() > 0) {
            return (JsonResponse) list.get(0);
        }
        // 商品信息
        String productMessage = (String) uploadMessage.get("product");
        Product product = JSON.parseObject(productMessage, Product.class);
        // 处理详情图片
        List<FileContainer> fileContainerList = null;
        if (uploadMessage.get(CommonStrings.FILE_LIST) instanceof List) {
            fileContainerList = myCast(uploadMessage.get(CommonStrings.FILE_LIST));
        }
        assert fileContainerList != null;
        FileContainer thumbnail = null;
        for (FileContainer fileContainer : fileContainerList) {
            if (StringUtils.contains(fileContainer.getFileName(), "thumbnail")) {
                thumbnail = new FileContainer();
                thumbnail.setFileName(fileContainer.getFileName());
                thumbnail.setFileInputStream(fileContainer.getFileInputStream());
            }
        }
        Integer productId = (Integer) request.getSession().getAttribute(CommonStrings.PRODUCT_ID);
        if (productId == null && product.getProductId() == null) {
            return JsonResponse.errorMsg("没有指明商品！");
        }
        if (productId != null) {
            product.setProductId(productId);
        }
        return productService.modifyProduct(product, thumbnail, fileContainerList);
    }

    /**
     * 获取修改商品所需要的数据
     *
     * @param id 商品id
     * @return 数据
     */
    @GetMapping("/modifyInfo")
    @ResponseBody
    public JsonResponse getModifyInformation(int id, HttpServletRequest request) {
        // 商品信息
        Product product = productService.getProductByProductId(id);
        // 商品的详情图片UUID
        List<String> list = productImageService.queryProductImageList(product.getProductId());
        // 从Session中取出当前在修改那个商店的商品
        /*  int shopId = (int) request.getSession().getAttribute(CommonStrings.SHOP_ID);*/
        int shopId = 13;
        List<ProductCategory> productCategoryList = productCategoryService.queryProductCategoryListByShopId(shopId);
        // 商品类别类别
        Map<String, Object> map = new HashMap<>(3);
        map.put("product", product);
        map.put("list", list);
        map.put("productCategoryList", productCategoryList);
        return JsonResponse.ok(map);
    }

    /**
     * 根据商品id 获取商品缩略图
     *
     * @param productId 商品id
     * @param response  响应
     */
    @GetMapping(value = "/getProductThumbnail")
    public void getProductThumbnail(int productId, HttpServletResponse response) {
        File productThumbnail = productService.getProductThumbnail(productId);
        try {
            CommonUtils.exportPictureData(response, productThumbnail);
        } catch (IOException e) {
            logger.error("输出图片文件失败！失败原因：" + e.getMessage());
        }
    }

    /**
     * 根据商品id 删除商品缩略图
     *
     * @param productId 商品id
     * @return 删除缩略图结果
     */
    @GetMapping(value = "/deleteProductThumbnail")
    @ResponseBody
    public JsonResponse deleteProductThumbnail(int productId) {
        return productService.deleteProductThumbnail(productId);
    }

    /**
     * 获取商铺所有的商品
     *
     * @param shopId 商店id
     * @return 商品列表
     */
    @GetMapping("/getProductList")
    @ResponseBody
    public JsonResponse getProductList(int shopId) {
        List<Product> productList = productService.getProductByShopId(shopId);
        return JsonResponse.ok(productList);
    }

    /**
     * 改变商品的上下架情况
     *
     * @param productId 商品情况
     */
    @GetMapping(value = "/modifyProductStatus")
    @ResponseBody
    public JsonResponse modifyProductStatus(Integer productId) {
        productService.modifyProductStatus(productId);
        return JsonResponse.ok();
    }

    @GetMapping("/productDetail")
    public String showProductDetail() {
        return "product/productDetails";
    }


    @SuppressWarnings("unchecked")
    private static <T> T myCast(Object obj) {
        return (T) obj;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setProductImageService(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @Autowired
    public void setProductCategoryService(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }
}
