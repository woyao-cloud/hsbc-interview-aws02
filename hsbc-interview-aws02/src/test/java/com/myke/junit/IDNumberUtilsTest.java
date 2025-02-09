package com.myke.junit;

import com.myke.util.IDNumberUtils;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;
import java.util.function.Predicate;

/**
 * 使用junit的Parameters参数化注解测试多分支的方法，
 * <p>
 * JUnitParams做参数化单元测试
 *
 * @author zhangjianbin
 * @date 2021年10月09日12:41
 */
@RunWith(JUnitParamsRunner.class)
public class IDNumberUtilsTest {

    @Test
    @Parameters(method = "getBirAgeSexParams")
    public void getBirAgeSex(String certificateNo, Predicate<Map<String, String>> predicate) {
        Map<String, String> minuteMap = IDNumberUtils.getBirAgeSex(certificateNo);
        Assert.assertTrue(predicate.test(minuteMap));
    }

    public Object[] getBirAgeSexParams() {
        return new Object[]{
                new Object[]{
                        "410823199110090218", (Predicate<Map<String, String>>) map ->
                        "{birthday=1991-10-09, sex=male, age=22}".equals(map.toString())
                },
                new Object[]{
                        "410823199110090218", (Predicate<Map<String, String>>) map ->
                        "{birthday=1991-10-09, sex=male, age=30}".equals(map.toString())
                },

        };
    }
}