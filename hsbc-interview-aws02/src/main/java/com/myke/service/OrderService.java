package com.myke.service;

import com.myke.convert.OrderMapper;
import com.myke.model.dto.OrderDTO;
import com.myke.model.vo.OrderVO;
import com.myke.model.vo.UserVO;
import com.myke.util.HttpContextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjianbin
 * @date 2021年10月11日13:12
 */
public class OrderService {

    /**
     * 静态方法多分支场景
     *
     * @param userVO
     *
     * @return
     */
    public List<OrderVO> getUserOrdersBySource(UserVO userVO) {
        List<OrderVO> orderList = new ArrayList<>();
        OrderVO order = new OrderVO();
        if ("APP".equals(HttpContextUtils.getCurrentSource())) { // 手机来源
            if ("CNY".equals(HttpContextUtils.getCurrentCurrency())) { // 人民币
                // TODO 针对App端的订单，并且请求币种为人民币的业务逻辑...
                System.out.println("source -> APP, currency -> CNY");
            } else {
                System.out.println("source -> APP, currency -> !CNY");
            }
            order.setType(1);
        } else if ("WAP".equals(HttpContextUtils.getCurrentSource())) { // H5来源
            // TODO 针对H5端的业务逻辑...
            System.out.println("source -> WAP");
            order.setType(2);
        } else if ("ONLINE".equals(HttpContextUtils.getCurrentSource())) { // PC来源
            // TODO 针对PC端的业务逻辑...
            System.out.println("source -> ONLINE");
            order.setType(3);
        }
        orderList.add(order);
        return orderList;
    }

    /**
     * 静态final变量场景
     *
     * @param orders
     *
     * @return
     */
    public List<OrderVO> convertUserOrders(List<OrderDTO> orders) {
        List<OrderVO> orderList = new ArrayList<>();
        for (OrderDTO orderDTO : orders) {
            //将orderDTO转换为orderVO，然后根据type的值走不同的分支
            OrderVO orderVO = OrderMapper.INSTANCE.convert(orderDTO);
            if (1 == orderVO.getType()) {
                orderVO.setOrderDesc("App端订单");
            } else if (2 == orderVO.getType()) {
                orderVO.setOrderDesc("H5端订单");
            } else if (3 == orderVO.getType()) {
                orderVO.setOrderDesc("PC端订单");
            }
            orderList.add(orderVO);
        }
        return orderList;
    }
}