package org.sagittarius.common.test;

import org.sagittarius.common.annotation.ParamFilterException;
import org.sagittarius.common.annotation.ParamFilterResolver;
import org.sagittarius.common.test.entity.ParamFitlerTestEntity;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestParamFilter {

    private List<Object> oneList() {
        List<Object> list = new ArrayList<>();
        list.add(new Object());
        return list;
    }

    private Map<String, Object> oneMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", new Object());
        return map;
    }

    @DataProvider
    public Object[][] provider() {
        return new Object[][]{
                {
                        ParamFitlerTestEntity.builder()
                                .name(null).obj(new Object()).list(oneList()).map(oneMap())
                                .build()
                },
                {
                        ParamFitlerTestEntity.builder()
                                .name("").obj(new Object()).list(oneList()).map(oneMap())
                                .build()
                },
                {
                        ParamFitlerTestEntity.builder()
                                .name("a").obj(null).list(oneList()).map(oneMap())
                                .build()
                },
                {
                        ParamFitlerTestEntity.builder()
                                .name("a").obj(new Object()).list(null).map(oneMap())
                                .build()
                },
                {
                        ParamFitlerTestEntity.builder()
                                .name("a").obj(new Object()).list(new ArrayList()).map(oneMap())
                                .build()
                },
                {
                        ParamFitlerTestEntity.builder()
                                .name("a").obj(new Object()).list(oneList()).map(null)
                                .build()
                },
                {
                        ParamFitlerTestEntity.builder()
                                .name("a").obj(new Object()).list(oneList()).map(new HashMap())
                                .build()
                },
                {
                        ParamFitlerTestEntity.builder()
                                .name("a").obj(new Object()).list(oneList()).map(oneMap())
                                .build()
                },
        };
    }

    @Test(dataProvider = "provider")
    public void test(ParamFitlerTestEntity entity) throws ParamFilterException {
        ParamFilterResolver.checkParam(entity);
    }



}
