package com.myke.service;

import com.myke.dao.MoneyDAO;
import com.myke.dao.UserDAO;
import com.myke.dao.UserMapper;
import com.myke.exception.APIException;
import com.myke.model.dto.UserDTO;
import com.myke.model.vo.OrderVO;
import com.myke.model.vo.UserVO;
import com.myke.util.IDNumberUtils;
import com.myke.util.LogUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用户服务
 */
@Service
public class UserService {

    @Autowired
    UserDAO userDao;

    @Autowired
    MoneyDAO moneyDAO;

    @Autowired
    private UserMapper userMapper;

    public UserVO getUserById(int uid) {
        List<UserDTO> users = userDao.getUserInfo();
        UserDTO userDTO = users.stream().filter(u -> u.getId() == uid).findFirst().orElse(null);
        UserVO userVO = new UserVO();
        if (null == userDTO) {
            return userVO;
        }
        userVO.setId(userDTO.getId());
        userVO.setName(userDTO.getName());
        userVO.setSex(userDTO.getSex());
        userVO.setAge(userDTO.getAge());
        // 显示邮编
        if ("上海".equals(userDTO.getProvince())) {
            userVO.setAbbreviation("沪");
            userVO.setPostCode(200000);
        }
        if ("北京".equals(userDTO.getProvince())) {
            userVO.setAbbreviation("京");
            userVO.setPostCode(100000);
        }
        // 手机号处理
        if (null != userDTO.getTelephone() && !"".equals(userDTO.getTelephone())) {
            userVO.setTelephone(userDTO.getTelephone().substring(0, 3) + "****" + userDTO.getTelephone().substring(7));
        }
        return userVO;
    }


    /**
     * 根据汇率计算金额
     * <p>
     * Void方法测试
     * <p>
     * 根据汇率计算金额
     *
     * @param userVO
     */
    public void setOrderAmountByExchange(UserVO userVO) {
        if (null == userVO.getUserOrders() || userVO.getUserOrders().size() <= 0) {
            return;
        }
        for (OrderVO orderVO : userVO.getUserOrders()) {
            BigDecimal amount = orderVO.getAmount();
            // 获取汇率(调用汇率接口)
            BigDecimal exchange = moneyDAO.getExchangeByCountry(userVO.getCountry());
            amount = amount.multiply(exchange); // 根据汇率计算金额
            orderVO.setAmount(amount);
        }
    }


    /**
     * 异常测试
     * <p>
     * 校验请求参数user是否合法
     *
     * @param user
     *
     * @throws APIException
     */
    public void validateUser(UserVO user) throws APIException {
        if (user == null) {
            throw new APIException("10001", "user is null");
        }
        if (null == user.getName() || "".equals(user.getName())) {
            throw new APIException("10002", "user name is null");
        }
        if (user.getAge() == 0) {
            throw new APIException("10003", "user age is null");
        }
        if (null == user.getTelephone() || "".equals(user.getTelephone())) {
            throw new APIException("10004", "user telephone is null");
        }
        if (null == user.getSex() || "".equals(user.getSex())) {
            throw new APIException("10005", "user sex is null");
        }
        if (null == user.getUserOrders() || user.getUserOrders().size() <= 0) {
            throw new APIException("10006", "user order is null");
        }
        for (OrderVO order : user.getUserOrders()) {
            if (null == order.getOrderNum() || "".equals(order.getOrderNum())) {
                throw new APIException("10007", "order number is null");
            }
            if (null == order.getAmount()) {
                throw new APIException("10008", "order amount is null");
            }
        }
    }


    /**
     * 静态方法mock测试场景
     *
     * @param uid
     *
     * @return
     */
    public UserVO getUserByIdStatic(int uid) {
        List<UserDTO> users = userDao.getUserInfo();
        UserDTO userDTO = users.stream().filter(u -> u.getId() == uid).findFirst().orElse(null);
        UserVO userVO = new UserVO();
        if (null == userDTO) {
            return userVO;
        }
        userVO.setId(userDTO.getId());
        userVO.setName(userDTO.getName());
        userVO.setSex(userDTO.getSex());
        if ("上海".equals(userDTO.getProvince())) {
            userVO.setAbbreviation("沪");
            userVO.setPostCode(200000);
        }
        if ("北京".equals(userDTO.getProvince())) {
            userVO.setAbbreviation("京");
            userVO.setPostCode(100000);
        }
        if (null != userDTO.getTelephone() && !"".equals(userDTO.getTelephone())) {
            userVO.setTelephone(userDTO.getTelephone().substring(0, 3) + "****" + userDTO.getTelephone().substring(7));
        }
        // 静态方法调用 身份证工具类
        Map<String, String> idMap = IDNumberUtils.getBirAgeSex(userDTO.getIdNo());
        userVO.setAge(idMap.get("age") != null ? Integer.parseInt(idMap.get("age")) : 0);
        // 静态方法调用 记录日志
        LogUtils.info("response user:", userVO.toString());
        return userVO;
    }

    public boolean addUser(UserVO userVO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userVO.getId());
        userDTO.setName(userVO.getName());
        userDTO.setSex(userVO.getSex());
        return userDao.insertUser(userDTO);
    }

    public UserDTO add(UserDTO u) {
        if (null == u || StringUtils.isBlank(u.getName())) {
            throw new RuntimeException("user cannot null");
        }
        userMapper.insert(u);
        return u;
    }

    public UserDTO query(Long id) {
        if (null == id) {
            throw new RuntimeException("id cannot null");
        }
        return userMapper.selectByPrimaryKey(id);
    }

}