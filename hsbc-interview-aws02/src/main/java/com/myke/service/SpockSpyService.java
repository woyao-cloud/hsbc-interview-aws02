package com.myke.service;

import org.springframework.stereotype.Service;

/**
 * @author zhangjianbin
 * @date 2021年10月12日9:53
 */
@Service
public class SpockSpyService {

    int methodA(int i) {

        int j = methodB();

        // 其他业务逻辑...
        if (j > 0) {
            return Math.max(i, j);
        } else {
            return Math.min(i, j);
        }
    }

    int methodB() {
        System.out.println("methodB call");
        return 5; // 结果假如是从查询数据库或其他接口获取
    }
}