package com.wjj.campus.service.impl;

import com.wjj.campus.mapper.ShopMapper;
import com.wjj.campus.entity.Shop;
import com.wjj.campus.entity.ShopCategory;
import com.wjj.campus.exceptions.ShopOperationException;
import com.wjj.campus.model.FileContainer;
import com.wjj.campus.service.ShopCategoryService;
import com.wjj.campus.service.ShopService;
import com.wjj.campus.util.ImageUtils;
import com.wjj.campus.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/12
 * Time: 21:23
 * Description:
 *
 * @author jiajie.wan
 */
@Service
public class ShopServiceImpl implements ShopService {

    /**
     * 日志记录
     */
    private static Logger logger = LoggerFactory.getLogger(ShopService.class);
    /**
     * 注入店铺持久层
     */
    private ShopMapper shopMapper;

    /**
     * 注入商铺类别服务
     */
    private ShopCategoryService shopCategoryService;

    /**
     * 使用注解控制事务方法的优点：
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格
     * 2.保证事务方法的执行时间尽可能短，不要穿插其他网络操作，RPC/HTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制
     */
    @Override
    @Transactional(rollbackFor = ShopOperationException.class)
    public boolean addShop(Shop shop, FileContainer fileContainer) {
        // 空值判断
        if (shop == null) {
            // 店铺信息为空
            throw new ShopOperationException("店铺信息不能为空");
        }
        // 1、向数据库中添加店铺信息，在添加之前，将店铺信息的状态设置为 审核中
        shop.setEnableStatus(0);
        //     设置店铺创建时间，设置最后修改时间
        shop.setCreateTime(new Date());
        shop.setLastEditTime(shop.getCreateTime());
        InputStream shopImage = fileContainer.getFileInputStream();
        String fileName = fileContainer.getFileName();
        // 2、 添加店铺信息
        int add = shopMapper.addShop(shop);
        if (add <= 0) {
            throw new ShopOperationException("店铺创建失败！");
        }
        if (shopImage == null) {
            throw new ShopOperationException("图片信息为空！");
        }
        // 存储图片
        try {
            addShopImage(shop, shopImage, fileName);
        } catch (Exception e) {
            throw new ShopOperationException("图片存储失败！");
        }
        // 更新店铺的图片地址
        Shop temp = new Shop();
        temp.setShopImg(shop.getShopImg());
        temp.setShopId(shop.getShopId());
        int updateShop = shopMapper.updateShop(temp);
        if (updateShop <= 0) {
            // 修改店铺图片失败！
            throw new ShopOperationException("修改店铺图片失败");
        }
        return true;
    }

    /**
     * 修改店铺信息流程：
     * 1、确保店铺信息无误
     * 2、检查是否有店铺图片上传
     *
     * @param shop          店铺实体信息
     * @param fileContainer 文件信息
     * @return 修改信息结果
     */
    @Override
    @Transactional(rollbackFor = ShopOperationException.class)
    public boolean modifyShop(Shop shop, FileContainer fileContainer) {
        if (shop == null || shop.getShopId() == null) {
            throw new ShopOperationException("店铺信息不能为空");
        }
        InputStream shopImage = fileContainer.getFileInputStream();
        String fileName = fileContainer.getFileName();
        // 检查是否有图片上传
        if (shopImage != null && fileName != null && !"".equals(fileName)) {
            // 删除原来的图片并将新上传的保存
            Shop temp = shopMapper.queryShopByShopId(shop.getShopId());
            if (temp.getShopImg() != null && !"".equals(temp.getShopImg())) {
                // 删除原来的图片
                ImageUtils.deleteShopImage(temp.getShopImg());
            }
            // 保存新的图片
            try {
                addShopImage(shop, shopImage, fileName);
            } catch (IOException e) {
                logger.error("修改店铺图片失败，" + e.getMessage());
                throw new ShopOperationException("修改店铺信息失败！");
            }
        }
        // 更新最后修改时间
        shop.setLastEditTime(new Date());
        // 排除不可修改的属性
        shop.setOwnerId(null);
        shop.setShopCategoryId(null);
        shop.setPriority(null);
        shop.setEnableStatus(null);
        // 更新店铺信息
        int updateShop = shopMapper.updateShop(shop);
        if (updateShop < 1) {
            throw new ShopOperationException("更新店铺信息失败！");
        }
        return true;
    }

    @Override
    public List<Shop> getShopList(int userId) {
        Shop s = new Shop();
        s.setOwnerId(userId);
        int number = shopMapper.queryShopNumber(s);
        return shopMapper.queryShopList(s, 0, number);
    }

    @Override
    public List<Shop> getShopList(String shopName, Integer shopCategoryId, Integer areaId, Integer rowIndex, Integer pageSize, Integer parentClassNumber) {
        Shop shop = new Shop();
        shop.setEnableStatus(1);
        if (shopName != null || shopCategoryId != null || areaId != null) {
            shop.setShopName(shopName);
            shop.setShopCategoryId(shopCategoryId);
            shop.setAreaId(areaId);
        }
        // 设置默认起始记录行数和默认分页大小
        if (rowIndex == null) {
            rowIndex = 0;
        }
        if (pageSize == null) {
            pageSize = 0;
        }
        if (parentClassNumber != null) {
            // 查询该父类编号下面的子类编号
            List<ShopCategory> shopCategories = shopCategoryService.getRegisterShopCategoryByParentId(parentClassNumber);
            List<Shop> shopList = new LinkedList<>();
            for (ShopCategory shopCategory : shopCategories) {
                Integer id = shopCategory.getShopCategoryId();
                shop.setShopCategoryId(id);
                shop.setEnableStatus(1);
                List<Shop> list = shopMapper.queryShopList(shop, rowIndex, pageSize);
                shopList.addAll(list);
            }
            shop.setShopCategoryId(parentClassNumber);
            shop.setEnableStatus(1);
            List<Shop> list = shopMapper.queryShopList(shop, rowIndex, pageSize);
            shopList.addAll(list);
            return shopList;
        }
        return shopMapper.queryShopList(shop, rowIndex, pageSize);
    }

    @Override
    public Shop queryShopByShopId(int shopId) {
        Shop shop = queryTotalShopByShopId(shopId);
        shop.setOwnerId(null);
        shop.setPriority(null);
        shop.setEnableStatus(null);
        return shop;
    }

    @Override
    public Shop queryTotalShopByShopId(int shopId) {
        return shopMapper.queryShopByShopId(shopId);
    }

    @Override
    public File getShopImage(int shopId) {
        Shop shop = shopMapper.queryShopImageByShopId(shopId);
        String shopImagePath = PathUtils.getImageBasePath() + shop.getShopImg();
        shopImagePath = PathUtils.replaceFileSeparator(shopImagePath);
        assert shopImagePath != null;
        return new File(shopImagePath);
    }

    private void addShopImage(Shop shop, InputStream shopImage, String fileName) throws IOException {
        // 获取shop图片目录的相对路径
        String dest = PathUtils.getShopImagePath(shop.getShopId());
        // 获取图片文件的路径
        String shopImageAddress = ImageUtils.generateThumbnail(shopImage, dest, fileName);
        // 设置店铺的图片路径
        shop.setShopImg(shopImageAddress);
    }

    @Override
    public List<Shop> getAdministratorShopList() {
        return shopMapper.queryAdministratorShopList();
    }

    @Override
    public boolean modifyShopStatus(int shopId, boolean status) {
        Shop shop = new Shop();
        shop.setShopId(shopId);
        // 修改商铺的可用状态 true - 为可用 ， false - 为不可用
        if (status) {
            shop.setEnableStatus(1);
        } else {
            shop.setEnableStatus(-1);
        }
        shop.setLastEditTime(new Date());
        return shopMapper.updateShop(shop) > 0;
    }

    @Autowired
    public void setShopMapper(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    @Autowired
    public void setShopCategoryService(ShopCategoryService shopCategoryService) {
        this.shopCategoryService = shopCategoryService;
    }
}