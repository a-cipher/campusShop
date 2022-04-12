package com.wjj.campus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/1
 * Time: 18:58
 * Description: 校园商铺平台 SpringBoot版本
 *
 * @author jiajie.wan
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class CampusApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusApplication.class, args);
    }

}
