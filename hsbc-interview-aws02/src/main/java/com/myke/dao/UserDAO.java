package com.myke.dao;

import com.myke.model.dto.UserDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjianbin
 * @date 2021年10月09日14:31
 */
@Repository
public class UserDAO {

    public List<UserDTO> getUserInfo() {
        // 模拟用户中心服务接口调用
        List<UserDTO> users = new ArrayList<>();
        UserDTO user = new UserDTO();
        user.setId(1);
        user.setName("zhangsan");
        user.setAge(28);
        user.setProvince("ShangHai");
        users.add(user);
        return users;
    }

    public boolean insertUser(UserDTO userDTO) {
        // 模拟数据库调用添加用户操作
        return true;
    }
}