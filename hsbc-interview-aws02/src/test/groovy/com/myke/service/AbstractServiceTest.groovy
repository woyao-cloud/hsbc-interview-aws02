package com.myke.service

import com.myke.BaseSpock
import com.myke.dao.MoneyDAO
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.reflect.Whitebox
import spock.lang.Unroll

/**
 * Spock如何模拟抽象类方法
 */
@PrepareForTest([SubService.class])
class AbstractServiceTest extends BaseSpock {

    @Unroll
    def "测试抽象方法"() {
        given: "mock抽象类方法"

        // SubService是继承AbstractService 的子类
        def sub = PowerMockito.mock(SubService)

        // 使用power mock模拟掉抽象类的方法，返回一个变量parentValue
        // 变量parentValue放在Spock的where标签里，即可实现动态mock的效果

        // mock掉抽象类的 parentMethod, 返回动态mock值: parentValue
        PowerMockito.when(sub.parentMethod()).thenReturn(parentValue)
        PowerMockito.when(sub.doSomething()).thenCallRealMethod()

        expect: "调用doSomething方法"
        sub.doSomething() == result

        where: "验证分支场景"
        parentValue | result
        "parent1"   | "sub1"
        "parent2"   | "sub2"
        "parent3"   | "sub3"
        "parent4"   | "other"
    }

    @Unroll
    def "测试抽象方法和实例方法"() {
        given: "mock抽象类方法"
        def sub = PowerMockito.mock(SubService)

        // mock掉抽象类的 parentMethod, 返回动态mock值: parentValue
        PowerMockito.when(sub.parentMethod()).thenReturn(parentValue)
        PowerMockito.when(sub.doSomethingAndDao()).thenCallRealMethod()

        def moneyDAO = Mock(MoneyDAO)
        //将Spock mock的对象moneyDAO使用power mock赋值给 SubService 的引用 moneyDAO
        Whitebox.setInternalState(sub, "moneyDAO", moneyDAO)

        moneyDAO.getExchangeByCountry(_) >> money // 这样就可以使用spock的动态mock

        expect: "调用doSomething方法"
        sub.doSomethingAndDao() == result

        where: "验证分支场景"
        parentValue | money || result
        "parent1"   | 100   || "100 CNY"
        "parent2"   | 200   || "200 USD"
        "parent3"   | 300   || "300 EUR"
        "parent4"   | 400   || "400"
    }
}
