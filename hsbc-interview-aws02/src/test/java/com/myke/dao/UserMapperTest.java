package com.myke.dao;

import com.myke.model.dto.UserDTO;
import com.myke.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserMapperTest {
    @Autowired
    private UserService userService;

    /**
     * 在测试方法之前和之后都执行数据清除
     */
    @Test
    @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testAdd() {
        UserDTO u = new UserDTO();
        u.setName("test-name");
        u.setPassword("test-password");
        userService.add(u);
        assertTrue(u.getId() != null);
        UserDTO u2 = userService.query(Long.valueOf(u.getId()));
        assertTrue(u.getName().equals(u2.getName()));
        assertTrue(u.getPassword().equals(u2.getPassword()));
    }
}