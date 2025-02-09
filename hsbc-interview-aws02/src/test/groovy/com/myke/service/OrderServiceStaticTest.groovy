package com.myke.service

import com.myke.BaseSpock
import com.myke.convert.OrderMapper
import com.myke.model.dto.OrderDTO
import com.myke.model.vo.OrderVO
import com.myke.model.vo.UserVO
import com.myke.util.HttpContextUtils
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.reflect.Whitebox
import spock.lang.Unroll

@PrepareForTest([HttpContextUtils.class])
class OrderServiceStaticTest extends BaseSpock {

    def orderService = new OrderService()

    void setup() {
        // mock静态类
        PowerMockito.mockStatic(HttpContextUtils.class)
    }

    /**
     * 测试spock的mock和power mock静态方法组合用法的场景
     */
    @Unroll
    def "当来源是#source时，订单类型为:#type"() {
        given: "mock当前上下文的请求来源"
        PowerMockito.when(HttpContextUtils.getCurrentSource()).thenReturn(source)

        and: "mock当前上下文的币种"
        PowerMockito.when(HttpContextUtils.getCurrentCurrency()).thenReturn(currency)

        when: "调用获取用户订单列表"
        def orderList = orderService.getUserOrdersBySource(new UserVO())

        then: "验证返回结果是否符合预期值"
        with(orderList) {
            it[0].type == type
        }

        // Spock使用where表格的方式让power mock具有了动态mock的功能
        // 把power mock返回的mock值作为变量放在where里使用，以达到动态mock静态方法的功能
        where: "表格方式验证订单信息的分支场景"
        source   | currency || type
        "APP"    | "CNY"    || 1
        "APP"    | "USD"    || 1
        "WAP"    | ""       || 2
        "ONLINE" | ""       || 3
    }

    /**
     * 测试spock的mock和powermock静态final变量结合的用法
     */
    @Unroll
    def "ConvertUserOrders"() {
        given: "mock掉OrderMapper的静态final变量INSTANCE,并结合spock设置动态返回值"
        // 先使用Spock的mock,将OrderMapper类mock为一个模拟对象orderMapper
        def orderMapper = Mock(OrderMapper.class)

        // 将第一步mock的对象orderMapper 通过PowerMock把Mock对象orderMapper赋值给静态常量INSTANCE
        Whitebox.setInternalState(OrderMapper.class, "INSTANCE", orderMapper)

        // 结合where模拟不同的返回值,调用 convert 方法时,返回 order
        orderMapper.convert(_) >> order

        when: "调用用户订单转换方法"
        def userOrders = orderService.convertUserOrders([new OrderDTO()])

        then: "验证返回结果是否符合预期值"
        with(userOrders) {
            it[0].orderDesc == desc
        }

        where: "表格方式验证订单属性转换结果"
        order                || desc
        new OrderVO(type: 1) || "App端订单"
        new OrderVO(type: 2) || "H5端订单"
        new OrderVO(type: 3) || "PC端订单"
    }
}