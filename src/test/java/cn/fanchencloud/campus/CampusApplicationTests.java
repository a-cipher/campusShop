package com.wjj.campus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjj.campus.entity.OrderForm;
import com.wjj.campus.mapper.OrderFormMapper;
import com.wjj.campus.mapper.ShopMapper;
import com.wjj.campus.service.CommentService;
import com.wjj.campus.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CampusApplicationTests {

    @Test
    void contextLoads() {
        
        
    }

    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderFormMapper orderFormMapper;

    @Test
    public void shopTest(){
//        System.out.println(shopMapper.selectById(29).getShopName());
//        System.out.println(commentService.deleteComment(2));
        Integer flag=null;
        int userId=13;
        //orderService.getOrderListByUserId(13,null);
        QueryWrapper<OrderForm> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        if(flag==null){
            System.out.println("查询全部");
        }else if(flag==0){
            wrapper.eq("order_status",0);
        }else if(flag==1){
            wrapper.eq("order_status",1);
        }
        orderFormMapper.selectList(wrapper);
    }

}
