package com.myke.service


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Import()
class SpringBootServiceTest extends Specification {

    @Autowired
    private UserService userService;

    @Unroll
    def "test autowired service"() {
        when:
        def response = userService.getUserById(1)
        then:
        with(response) {
            id == 1
            name == "张三"
        }

    }
}