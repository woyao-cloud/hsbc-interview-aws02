package com.myke.util

import com.myke.util.IDNumberUtils
import spock.lang.Specification
import spock.lang.Unroll

class IDNumberUtilsTest extends Specification {

    /**
     * 不同的分支逻辑测试
     */
    //@Unroll注解表示展开where标签下面的每一行测试，作为单独的case跑
    @Unroll
    def "身份证号:#idNo 的生日，性别，年龄:#result"() {
        //期望的行为，when-then的精简版,即 "什么时候做什么 + 然后验证什么结果" 组合起来
        expect: "when + then 组合"
        IDNumberUtils.getBirAgeSex(idNo) == result

//        方式二
//        when:
//        def rt = IDNumberUtils.getBirAgeSex(idNo)
//        then:
//        rt == result

        //反复调用
        where: "表格方式测试不同的分支逻辑"
        idNo                 || result
        "410823199110090218" || ["birthday": "1991-10-09", "sex": "男", "age": "34"]
        "310168199809187333" || ["birthday": "1998-09-18", "sex": "男", "age": "27"]
        "320168200212084268" || ["birthday": "2002-12-08", "sex": "女", "age": "23"]
        "330168199301214267" || ["birthday": "1993-01-21", "sex": "女", "age": "32"]
        "411281870628201"    || ["birthday": "1987-06-28", "sex": "男", "age": "38"]
        "427281730307862"    || ["birthday": "1973-03-07", "sex": "女", "age": "52"]
        "479281691111377"    || ["birthday": "1969-11-11", "sex": "男", "age": "51"]
    }
}
