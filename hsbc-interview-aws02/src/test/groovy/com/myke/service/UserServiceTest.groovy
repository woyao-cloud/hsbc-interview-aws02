package com.myke.service

import com.myke.dao.MoneyDAO
import com.myke.dao.UserDAO
import com.myke.exception.APIException
import com.myke.model.dto.UserDTO
import com.myke.model.vo.OrderVO
import com.myke.model.vo.UserVO
import spock.lang.Specification
import spock.lang.Unroll

class UserServiceTest extends Specification {

    def userService = new UserService()
    def userDao = Mock(UserDAO)
    //Spock的Mock方法不仅可以模拟方法返回结果，还可以模拟方法行为(例如：验证某个方法的调用次数)
    def moneyDao = Mock(MoneyDAO);

    void setup() {
        userService.userDao = userDao
        userService.moneyDAO = moneyDao
    }

    def "test getUserById"() {
        // given 定义数据
        given: "设置请求参数"
        def user1 = new UserDTO(id: 1, name: "张三", province: "上海")
        def user2 = new UserDTO(id: 2, name: "李四", province: "江苏")
        // and 准备数据
        and: "mock掉接口返回的用户信息"
        // 两个右箭头">>"表示即模拟getUserInfo接口的返回结果,直接使用"[]"中括号表示返回的是List类型
        userDao.getUserInfo() >> [user1, user2]
        // when 待测试的函数
        when: "调用获取用户信息方法"
        def response = userService.getUserById(1)
        // then 判断是否符合预期
        then: "验证返回结果是否符合预期值"
        with(response) {
            name == "张三"
            abbreviation == "沪"
            postCode == 200000
        }
    }

    @Unroll
    def "input 学生id:#id, 返回的邮编:#postCodeResult, 返回的省份简称:#abbreviationResult"() {
        given: "Mock返回的学生信息"
        userDao.getUserInfo() >> students

        when: "获取学生信息"
        def response = userService.getUserById(id)

        then: "验证返回结果"
        with(response) {
            postCode == postCodeResult
            abbreviation == abbreviationResult
        }
        where: "经典之处：表格方式验证学生信息的分支场景"
        id | students                  || postCodeResult | abbreviationResult
        1  | getStudent(1, "张三", "北京") || 100000         | "京"
        2  | getStudent(2, "李四", "上海") || 200000         | "沪"
    }

    def getStudent(def id, def name, def province) {
        return [new UserDTO(id: id, name: name, province: province)]
    }

    def "测试void方法1"() {
        given: "设置请求参数"
        def userVO = new UserVO(name: "James", country: "美国")
        userVO.userOrders = [new OrderVO(orderNum: "1", amount: 10000), new OrderVO(orderNum: "2", amount: 1000)]

        when: "调用设置订单金额的方法"
        userService.setOrderAmountByExchange(userVO)

        then: "验证调用获取最新汇率接口的行为是否符合预期"
        // getExchangeByCountry()方法会被执行2次,第一次输出的汇率是0.1413, 第二次是0.1421
        // "2 * " 表示方法实际执行的次数， 如果不是2次则不符合预期，单元测试会失败
        // 验证我们调用汇率接口的方法是否执行了，以及执行次数
        2 * moneyDao.getExchangeByCountry(_) >> 0.1413 >> 0.1421
        // 执行几次就写几次，没有执行过就是"0 * "

        and: "验证根据汇率计算后的金额结果是否正确"
        // 对入参userVO里的订单金额amount进行校验
        with(userVO) {
            userOrders[0].amount == 1413
            userOrders[1].amount == 142.1
        }
    }

    //异常测试
    @Unroll
    def "验证用户信息的合法性: #expectedMessage"() {
        when: "调用校验用户方法"
        userService.validateUser(user)

        then: "捕获异常并设置需要验证的异常值"
        def exception = thrown(expectedException)
        exception.errorCode == expectedErrCode
        exception.errorMessage == expectedMessage

        where: "表格方式验证用户信息的合法性"
        user           || expectedException | expectedErrCode | expectedMessage
        getUser(10001) || APIException      | "10001"         | "user is null"
        getUser(10002) || APIException      | "10002"         | "user name is null"
        getUser(10003) || APIException      | "10003"         | "user age is null"
        getUser(10004) || APIException      | "10004"         | "user telephone is null"
        getUser(10005) || APIException      | "10005"         | "user sex is null"
        getUser(10006) || APIException      | "10006"         | "user order is null"
        getUser(10007) || APIException      | "10007"         | "order number is null"
        getUser(10008) || APIException      | "10008"         | "order amount is null"
    }

    def getUser(errCode) {
        def user = new UserVO()

        //闭包
        def condition1 = {
            user.name = "杜兰特"
        }
        def condition2 = {
            user.age = 20
        }
        def condition3 = {
            user.telephone = "15801833812"
        }
        def condition4 = {
            user.sex = "男"
        }
        def condition5 = {
            user.userOrders = [new OrderVO()]
        }
        def condition6 = {
            user.userOrders = [new OrderVO(orderNum: "123456")]
        }

        switch (errCode) {
            case 10001:
                user = null
                break
            case 10002:
                user = new UserVO()
                break
            case 10003:
                condition1()
                break
            case 10004:
                condition1()
                condition2()
                break
            case 10005:
                condition1()
                condition2()
                condition3()
                break
            case 10006:
                condition1()
                condition2()
                condition3()
                condition4()
                break
            case 10007:
                condition1()
                condition2()
                condition3()
                condition4()
                condition5()
                break
            case 10008:
                condition1()
                condition2()
                condition3()
                condition4()
                condition5()
                condition6()
                break
        }
        return user
    }

}
