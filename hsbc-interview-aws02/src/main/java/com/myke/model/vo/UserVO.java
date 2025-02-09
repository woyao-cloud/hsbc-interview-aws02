package com.myke.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zhangjianbin
 * @date 2021年10月09日14:33
 */
//@Getter
//@Setter
public class UserVO {
    private Integer id;
    private String name;
    private Integer age=0;
    private String sex;
    private Integer postCode; // 邮编
    private String abbreviation; // 省份简称
    private String country; // 国家
    private String telephone;
    private List<OrderVO> userOrders; // 用户订单
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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getPostCode() {
		return postCode;
	}
	public void setPostCode(Integer postCode) {
		this.postCode = postCode;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public List<OrderVO> getUserOrders() {
		return userOrders;
	}
	public void setUserOrders(List<OrderVO> userOrders) {
		this.userOrders = userOrders;
	}
    
    
}