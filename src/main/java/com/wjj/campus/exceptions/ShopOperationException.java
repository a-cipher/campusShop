package com.wjj.campus.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/12
 * Time: 23:31
 * Description: 店铺操作异常类
 *
 * @author jiajie.wan
 */
public class ShopOperationException extends RuntimeException{

    private static final long serialVersionUID = -507979058305715930L;

    public ShopOperationException(String message) {
        super(message);
    }
}
