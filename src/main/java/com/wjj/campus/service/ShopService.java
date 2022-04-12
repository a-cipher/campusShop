package com.wjj.campus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjj.campus.entity.Shop;
import com.wjj.campus.model.FileContainer;

import java.io.File;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/12
 * Time: 21:21
 * Description: 店铺服务层
 *
 * @author jiajie.wan
 */
public interface ShopService extends IService<Shop> {

    /**
     * 添加店铺信息
     *
     * @param shop      店铺信息
     * @param shopImage 店铺图片信息
     * @return 添加结果
     */
    boolean addShop(Shop shop, FileContainer shopImage);

    /**
     * 修改店铺信息
     *
     * @param shop      店铺实体信息
     * @param shopImage 店铺
     * @return 修改结果
     */
    boolean modifyShop(Shop shop, FileContainer shopImage);

    /**
     * 根据店铺id查询店铺信息，会隐藏默写属性
     *
     * @param shopId 店铺id
     * @return 店铺信息
     */
    Shop queryShopByShopId(int shopId);

    /**
     * 根据店铺id查询店铺所有信息
     *
     * @param shopId 店铺id
     * @return 店铺信息
     */
    Shop queryTotalShopByShopId(int shopId);

    /**
     * 根据店铺编号获取该店铺的门面照
     *
     * @param shopId 店铺id
     * @return 文件
     */
    File getShopImage(int shopId);

    /**
     * 获取用户id为userId 的所有店铺信息
     *
     * @param userId 用户id
     * @return 店铺信息列表
     */
    List<Shop> getShopList(int userId);

    /**
     * 根据多条件查询商铺列表
     *
     * @param shopName          商铺名称
     * @param shopCategoryId    商铺类别id
     * @param areaId            商铺区域
     * @param rowIndex          记录开始行数
     * @param pageSize          查询记录的行数
     * @param parentClassNumber 商铺父类编号
     * @return 商铺列表
     */
    List<Shop> getShopList(String shopName, Integer shopCategoryId, Integer areaId, Integer rowIndex, Integer pageSize, Integer parentClassNumber);

    /**
     * 返回管理员页面所需要的所有商铺列表
     *
     * @return 商铺列表
     */
    List<Shop> getAdministratorShopList();


    /**
     * 修改商铺的状态！
     *
     * @param shopId 商铺Id
     * @param status 商铺状态
     * @return 修改状态
     */
    boolean modifyShopStatus(int shopId, boolean status);
}
