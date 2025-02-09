package com.myke.util


import org.junit.runner.RunWith
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate
import org.spockframework.runtime.Sputnik
import spock.lang.Specification

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Sputnik.class)
@PrepareForTest([CalendarUtils.class])
class CalendarUtilsTest extends Specification {


    def cal = new CalendarUtils()

    def "testCalendar Jdk中的类相关测试"() {
        given:
        def calendar = Mock(Calendar)
        // 使用PowerMockito.stub和method方法代替PowerMockito.when方法
        PowerMockito.stub(PowerMockito.method(Calendar, "getInstance")).toReturn(calendar) // 使用stub替换when

        //调用 get方法时，返回 2020
        calendar.get(_) >> 2020

        when:
        def year = cal.getYear()

        then:
        year == 2020
    }
}
