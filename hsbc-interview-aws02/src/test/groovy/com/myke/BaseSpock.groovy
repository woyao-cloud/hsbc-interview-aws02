package com.myke


import com.myke.util.LogUtils
import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

/**
 * BaseSpock是我们封装的spock基类
 *
 * BaseSpock可以放在一个公共的项目中或作为jar的方式引用
 *
 * Spock单元测试代码的运行顺序是:
 * setupSpec() → setup() → cleanup() → cleanupSpec()
 *
 * setupSpec 类似于Junit的 @beforeClass
 * setup 类似于Junit的 @before
 * cleanup 类似于Junit的 @after
 * cleanupSpec 类似于Junit的 @afterClass
 */
@RunWith(PowerMockRunner.class)
//@PowerMockRunnerDelegate注解可以指定Spock的父类Sputnik去代理运行power mock
@PowerMockRunnerDelegate(Sputnik.class)
@PrepareForTest(value = [LogUtils.class])
//@SuppressStaticInitializationFor 可以使用power mock禁止初始化
@SuppressStaticInitializationFor(["com.myke.util.LogUtils"])
class BaseSpock extends Specification {

    void setup() {
        println "Spock setup"
        //mock掉一些项目中常用的类,比如日志记录

        // mock静态类
        PowerMockito.mockStatic(LogUtils.class)
    }
}