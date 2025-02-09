package com.myke.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderDTO {
    private String orderNum;
    private BigDecimal amount;
    private String createTime;
    private int type;
}