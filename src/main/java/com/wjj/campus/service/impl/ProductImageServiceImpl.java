package com.wjj.campus.service.impl;

import com.wjj.campus.mapper.ProductImageMapper;
import com.wjj.campus.entity.ProductImage;
import com.wjj.campus.service.ProductImageService;
import com.wjj.campus.util.ImageUtils;
import com.wjj.campus.util.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/30
 * Time: 2:42
 * Description:
 *
 * @author jiajie.wan
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    /**
     * 商品详情图片信息持久层
     */
    private ProductImageMapper productImageMapper;

    @Override
    public int addInBatchesProductImage(List<ProductImage> productImageList) {
        return productImageMapper.bulkInsertProductImage(productImageList);
    }

    @Override
    public File getProductImage(String uuid) {
        ProductImage productImage = productImageMapper.queryProductImageByUUID(uuid);
        String productImagePath = PathUtils.getImageBasePath() + productImage.getImgPath();
        productImagePath = PathUtils.replaceFileSeparator(productImagePath);
        assert productImagePath != null;
        return new File(productImagePath);
    }

    @Override
    public List<String> queryProductImageList(int productId) {
        return productImageMapper.queryProductImageList(productId);
    }

    @Override
    public boolean deleteProductImageByUUID(String uuid) {
        //删除图片文件
        ProductImage productImage = productImageMapper.queryProductImageByUUID(uuid);
        ImageUtils.deleteProductImage(productImage.getImgPath());
        return productImageMapper.deleteProductImageByUUId(uuid) != 0;
    }

    @Autowired
    public void setProductImageMapper(ProductImageMapper productImageMapper) {
        this.productImageMapper = productImageMapper;
    }
}
