package com.myke.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author zhangjianbin
 * @date 2021年10月09日15:56
 */
//@Setter
//@Getter
public class OrderVO {
    private String orderNum;
    private BigDecimal amount;
    private String createTime;
    private String orderDesc;

    /**
     * 1:APP, 2:H5, 3:PC
     */
    private int type;

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
    
    
}