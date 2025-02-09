package com.myke.service

import spock.lang.Specification

/**
 * Spock的Mock方法不仅可以模拟方法返回结果，还可以模拟方法行为
 * </p>
 * Stub()存根方法也是一个虚拟类，比Mock()方法更简单一些，只返回事先准备好的假数据，而不提供交互验证（即该方法是否被调用以及将被调用多少次）。
 * </br>
 * 使用存根Stub只能验证状态（例如测试方法返回的结果数据是否正确，list大小等，是否符合断言）。
 * </br>
 * 所以Mock比Stub的功能更多一些，但如果我们只是验证结果使用Stub就足够了，用法和Mock一样，而且更轻量一些。
 * </p>
 * Spy()间谍或叫刺探方法，他会包装一个真实的对象，默认情况下将调用真实的方法，在Spock中 Spy 也具有Mock的能力。
 *
 */
class SpockSpyServiceTest extends Specification {

    /**
     * 被测试的方法中会调用当前类的另外一个方法，但另外一个方法里面的逻辑很复杂或调用了外部依赖，
     * 那么我们可以通过Spy可以让第一个方法内部不调用另外一个方法，这样就只测试第一个方法
     */
    def "test methodA"() {
        given:
        def spy = Spy(SpockSpyService)
        // spy.methodB 调用时返回 2，并且只执行一次
        1 * spy.methodB() >> 2

        when:
        def result = spy.methodA(-1)

        then:
        result > 0
    }
}
