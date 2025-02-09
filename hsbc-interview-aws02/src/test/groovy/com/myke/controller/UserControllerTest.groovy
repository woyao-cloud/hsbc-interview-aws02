package com.myke.controller

import com.myke.exception.APIException
import com.myke.model.vo.OrderVO
import com.myke.model.vo.UserVO
import spock.lang.Specification
import spock.lang.Unroll

class UserControllerTest extends Specification {
    def userController = new UserController()

    // 只测试异常情况
    @Unroll
    def "验证用户信息的合法性1: #expectedMessage"() {
        when: "调用校验用户方法"
        userController.validateUser(user)
        then: "捕获异常并设置需要验证的异常值"
        def exception = thrown(expectedException)
        exception.errorCode == expectedErrCode
        exception.errorMessage == expectedMessage

        //这样就既可以测试多种异常case，又可以测试成功的case
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

    //测试异常和成功时的 case
    @Unroll
    def "验证用户信息的合法性2: #expectedMessage"() {
        when: "调用校验用户方法"
        userController.validateUser(user)
        // 这里的抛异常，是在validateUser()方法全部验证通过即成功后才会走到这里，属于预期的异常
        // "throw new Exception()"代码的目的是假如我们业务代码测试成功后再抛出一个"正常"的异常（或者叫成功的异常），为了满足thrown()的语法要求
        throw new APIException("00000", "success")

        then: "捕获异常并设置需要验证的异常值"
        def exception = thrown(expectedException)
        exception.errorCode == expectedErrCode
        exception.errorMessage == expectedMessage

        //这样就既可以测试多种异常case，又可以测试成功的case
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
        getUser(00000) || APIException      | "00000"         | "success"
    }


    def getUser(errCode) {
        def user = new UserVO()
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
        def condition7 = {
            user.userOrders = [new OrderVO(orderNum: "123456", amount: 100)]
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
            case 00000:
                condition1()
                condition2()
                condition3()
                condition4()
                condition5()
                condition6()
                condition7()

        }
        return user
    }
}
