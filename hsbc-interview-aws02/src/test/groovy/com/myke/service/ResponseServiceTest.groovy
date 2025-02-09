package com.myke.service

import com.myke.BaseSpock
import com.myke.exception.SOAException
import spock.lang.Unroll

class ResponseServiceTest extends BaseSpock {

    def soaClient = Mock(ResponseService.SoaClient)
    def handler = new ResponseService()

    @Override
    void setup() {
        handler.soaClient = soaClient
    }


    @Unroll
    def process() {
        given: "设置mock场景"
        soaClient.invoke(_) >> { soaResponse() } // { soaResponse.call() } 的简化写法

        when: "调用被测方法"
        def response = handler.process(new ResponseService.Request())

        then: "设置需要验证的属性"
        // ?语法类似于.Net或kotlin的空指针安全，为null则不调用resultCode属性
        response?.resultCode == status

        where: "验证不同业务场景"
        // where块里既验证正常的业务逻辑又验证异常的逻辑
        soaResponse        | status
        getSoaResponse(0)  | 0
        getSoaResponse(1)  | -1
        getSoaResponse(-1) | null // 这个case就是测试接口抛异常是否影响后续流程
    }

    /**
     *
     *
     *
     * </p>
     * 多个参数用逗号分割，用->隔开参数与方法体，没有参数可以不写->
     * @return
     */

    /**
     * Groovy中的闭包是一个开放，匿名的代码块，可以接受参数，返回值并分配给变量
     *{ [closureParameters -> ] statements } 其中[closureParameters->]代表参数，
     *  </p>
     * 多个参数用逗号分割，用->隔开参数与方法体，没有参数可以不写->
     *
     * @param index
     * @return
     */
    def getSoaResponse(int index) {
        def soaResponse = {} // 声明一个闭包
        switch (index) {
            case 0:
                soaResponse = { new ResponseService.SOAResponse("success": true) }
                break
            case 1:
                soaResponse = { new ResponseService.SOAResponse("success": false) }
                break
            case -1:
                // 如果不使用闭包的话，Spock编译到这里会直接抛出异常
                soaResponse = { throw new SOAException("接口异常") }
                break
        }
        return soaResponse
    }
}
