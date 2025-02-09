package com.myke.service;

import com.myke.dao.MoneyDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 测试抽象类方法或父类方法
 */
public class SubService extends AbstractService {

    @Autowired
    MoneyDAO moneyDAO;

    public String doSomething() {
        String parent = super.parentMethod(); // 调用抽象类或父类方法
        if ("parent1".equals(parent)) {
            // 执行parent1分支逻辑
            return "sub1";
        }
        if ("parent2".equals(parent)) {
            // 执行parent2分支逻辑
            return "sub2";
        }
        if ("parent3".equals(parent)) {
            // 执行parent3分支逻辑
            return "sub3";
        }
        return "other";
    }

    public String doSomethingAndDao() {
        // 调用抽象类或父类方法
        String parent = super.parentMethod();
        // 获取对应国家的金额
        BigDecimal money = moneyDAO.getExchangeByCountry(parent);
        if ("parent1".equals(parent)) {
            return money + " CNY";
        }
        if ("parent2".equals(parent)) {
            return money + " USD";
        }
        if ("parent3".equals(parent)) {
            return money + " EUR";
        }
        return money.toString();
    }
}
