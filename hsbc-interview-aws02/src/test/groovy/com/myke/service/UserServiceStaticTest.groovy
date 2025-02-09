package com.myke.service

import com.myke.BaseSpock
import com.myke.dao.UserDAO
import com.myke.model.dto.UserDTO
import com.myke.util.IDNumberUtils
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest

/**
 * 注意：如果你的单元测试代码不需要对静态方法, final方法mock, 就没必要使用power mock
 */
@PrepareForTest([IDNumberUtils.class])
class UserServiceStaticTest extends BaseSpock {

    def processor = new UserService()

    def dao = Mock(UserDAO)

    void setup() {
        processor.userDao = dao
        // mock静态类
//        PowerMockito.mockStatic(LogUtils.class)
        PowerMockito.mockStatic(IDNumberUtils.class)
    }

    def "GetUserByIdStatic"() {
        given: "设置请求参数"
        def user1 = new UserDTO(id: 1, name: "张三", province: "上海")
        def user2 = new UserDTO(id: 2, name: "李四", province: "江苏")
        def idMap = ["birthday": "1992-09-18", "sex": "男", "age": "28"]

        and: "mock掉接口返回的用户信息"
        dao.getUserInfo() >> [user1, user2]

        and: "mock静态方法返回值"
        //对getBirAgeSex()方法指定返回默认值
        PowerMockito.when(IDNumberUtils.getBirAgeSex(Mockito.any())).thenReturn(idMap)

        when: "调用获取用户信息方法"
        def response = processor.getUserByIdStatic(1)

        then: "验证返回结果是否符合预期值"
        with(response) {
            name == "张三"
            abbreviation == "沪"
            postCode == 200000
            age == 28
        }
    }
}
