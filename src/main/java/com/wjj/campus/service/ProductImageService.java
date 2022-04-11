package com.wjj.campus.service;

import com.wjj.campus.entity.ProductImage;

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
public interface ProductImageService {
    /**
     * 批量添加商品详情图片
     *
     * @param productImageList 商品详情图片列表
     * @return 影响记录条数
     */
    int addInBatchesProductImage(List<ProductImage> productImageList);

    /**
     * 获取指定UUID的图片的文件流
     *
     * @param uuid 文件UUID
     * @return 文件流
     */
    File getProductImage(String uuid);

    /**
     * 获取商品的所有图片UUID
     *
     * @param productId 商品ID
     * @return UUID列表
     */
    List<String> queryProductImageList(int productId);

    /**
     * 根据商品图片的UUID删除商品详情图片
     *
     * @param uuid uuid
     * @return 删除结果
     */
    boolean deleteProductImageByUUID(String uuid);
}
