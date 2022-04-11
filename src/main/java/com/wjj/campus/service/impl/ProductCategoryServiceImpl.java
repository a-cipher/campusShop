package com.wjj.campus.service.impl;

import com.wjj.campus.mapper.ProductCategoryMapper;
import com.wjj.campus.entity.ProductCategory;
import com.wjj.campus.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/28
 * Time: 14:48
 * Description:
 * h
 *
 * @author jiajie.wan
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    /**
     * 注入店铺商品类别持久层
     */
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategory> queryProductCategoryListByShopId(int shopId) {
        List<ProductCategory> productCategoryList = productCategoryMapper.queryProductCategoryListByShopId(shopId);
        for (ProductCategory productCategory : productCategoryList) {
            productCategory.setShopId(null);
        }
        return productCategoryList;
    }

    @Override
    public boolean addProductCategory(ProductCategory productCategory) {
        return productCategoryMapper.addProductCategory(productCategory) != 0;
    }

    @Override
    public void deleteProductCategory(int productCategoryId) {
        productCategoryMapper.deleteProductCategory(productCategoryId);
    }

    @Override
    public boolean modifyProductCategory(ProductCategory productCategory) {
        productCategory.setLastEditTime(new Date());
        return productCategoryMapper.modifyProductCategory(productCategory) != 0;
    }

    @Override
    public ProductCategory queryProductCategoryByProductCategoryId(int productCategoryId) {
        return productCategoryMapper.queryProductCategoryByProductCategoryId(productCategoryId);
    }

    @Autowired
    public void setProductCategoryMapper(ProductCategoryMapper productCategoryMapper) {
        this.productCategoryMapper = productCategoryMapper;
    }
}
