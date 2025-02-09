package com.myke.a;

/**
 * 抽象 Service
 */
public abstract class AbstractService {
    public String parentMethod() {
        // 发起接口调用或数据库操作
        return "parentMethod value";
    }
}