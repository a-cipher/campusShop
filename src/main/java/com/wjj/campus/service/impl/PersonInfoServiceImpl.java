package com.wjj.campus.service.impl;

import com.wjj.campus.entity.LocalAccount;
import com.wjj.campus.entity.Shop;
import com.wjj.campus.mapper.LocalAccountMapper;
import com.wjj.campus.mapper.PersonInfoMapper;
import com.wjj.campus.entity.PersonInfo;
import com.wjj.campus.model.FileContainer;
import com.wjj.campus.model.JsonResponse;
import com.wjj.campus.model.User;
import com.wjj.campus.service.PersonInfoService;
import com.wjj.campus.util.ImageUtils;
import com.wjj.campus.util.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/16
 * Time: 9:17
 * Description: 用户信息服务实现类
 *
 * @author jiajie.wan
 */
@Service
public class PersonInfoServiceImpl implements PersonInfoService {

    private static final Logger logger = LoggerFactory.getLogger(PersonInfoServiceImpl.class);

    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 注入用户信息持久层
     */
    private PersonInfoMapper personInfoMapper;
    private LocalAccountMapper localAccountMapper;

    @Override
    public PersonInfo getPersonInfo(int id) {
        return personInfoMapper.queryById(id);
    }

    @Override
    public File getUserImage(Integer userId) {
        PersonInfo personInfo = personInfoMapper.queryById(userId);
        String s = PathUtils.getImageBasePath() + personInfo.getHeadPortrait();
        s = PathUtils.replaceFileSeparator(s);
        assert s != null;
        return new File(s);
    }

    @Override
    public void deleteHeadImage(int userId) {
        // 查询信息
        PersonInfo personInfo = personInfoMapper.queryById(userId);
        // 删除数据库的信息
        personInfoMapper.deletePersonHeadImage(userId);
        // 删除文件 图片路径根路径 + /upload/user/1111111.jpg
        String picturePath = PathUtils.getImageBasePath() + personInfo.getHeadPortrait();
        File f = new File(picturePath);
        if (f.exists()) {
            boolean delete = f.delete();
        }
    }

    @Override
    @Transactional
    public JsonResponse updateMessage(PersonInfo personInfo, FileContainer headImage) {
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        defaultTransactionDefinition.setName("updateMessage");
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
        JsonResponse jsonResponse = null;
        boolean flag = true;
        try {
            // 判断用户信息是否是空值
            if (personInfo == null || personInfo.getId() == null) {
                throw new RuntimeException("用户信息有误！");
            }

            // 修改用户信息
            int number = personInfoMapper.updateUserMessage(personInfo);
            if (number <= 0) {
                throw new RuntimeException("修改用户信息失败！");
            }
            // 用户头像不为空则修改用户头像图
            if (headImage != null) {
                saveHeadImage(personInfo, headImage);
                // 更新用户的头像信息
                PersonInfo p = new PersonInfo();
                p.setId(personInfo.getId());
                p.setHeadPortrait(personInfo.getHeadPortrait());
                number = personInfoMapper.updateUserMessage(p);
                if (number <= 0) {
                    logger.error("处理商品缩略图信息出错！用户id：" + personInfo.getId());
                    throw new RuntimeException("添加用户头像！");
                }
            }
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(status);
            flag = false;
            jsonResponse = JsonResponse.errorMsg(e.getMessage());
        }
        if (flag) {
            jsonResponse = JsonResponse.ok("修改用户信息成功！");
        }
        return jsonResponse;
    }

    /**
     * 保存用户的头像数据
     *
     * @param personInfo 用户信息
     * @param headImage  用户头像数据
     * @throws IOException 写入磁盘IO异常
     */
    private void saveHeadImage(PersonInfo personInfo, FileContainer headImage) throws IOException {
        // 获取用户图片目录的相对路径
        String dest = PathUtils.getUserImagePath(personInfo.getId());
        // 获取图片文件的路径
        String shopImageAddress = ImageUtils.savePicture(headImage, dest);
        // 设置用户头像图片路径
        personInfo.setHeadPortrait(shopImageAddress);
    }

    @Override
    public List<User> getAdministratorUserList() {
        List<PersonInfo> personInfoList = personInfoMapper.queryAdministratorUserList();
        Map<Integer, LocalAccount> accountMap = localAccountMapper.queryAdministratorLocalAccountList();
        return personInfoList.stream().map(personInfo -> {
            User user = new User(personInfo);
            user.setUsername(accountMap.get(user.getId()).getUsername());
            return user;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean modifyUserStatus(int userId, boolean status) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setId(userId);
        // 修改用户的可用状态 true - 为可用 ， false - 为不可用  || 0 禁止使用本商城 1允许使用本商城
        if (status) {
            personInfo.setEnableStatus(1);
        } else {
            personInfo.setEnableStatus(0);
        }
        personInfo.setLastEditTime(new Date());
        return personInfoMapper.updateUserMessage(personInfo) > 0;
    }

    @Autowired
    public void setPersonInfoMapper(PersonInfoMapper personInfoMapper) {
        this.personInfoMapper = personInfoMapper;
    }

    @Autowired
    public void setDataSourceTransactionManager(DataSourceTransactionManager dataSourceTransactionManager) {
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    @Autowired
    public void setLocalAccountMapper(LocalAccountMapper localAccountMapper) {
        this.localAccountMapper = localAccountMapper;
    }
}
