package com.myke.model.dto;

import com.myke.model.vo.OrderVO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author zhangjianbin
 * @date 2021年10月09日14:32
 */
//@Getter
//@Setter
public class UserDTO {
    private Integer id;
    private String name;
    private String sex;
    private int age;
    private String province;
    private String telephone;
    private String idNo;

    private String currency; // 币种
    private List<OrderVO> userOrders; // 用户订单


    // 其他
    private String password;
    private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public List<OrderVO> getUserOrders() {
		return userOrders;
	}
	public void setUserOrders(List<OrderVO> userOrders) {
		this.userOrders = userOrders;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
}