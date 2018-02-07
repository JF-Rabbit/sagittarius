package org.sagittarius.common.test.entity;

import org.sagittarius.common.annotation.ParamFilter;
import org.sagittarius.common.annotation.ParamFilterEnum;

import java.util.List;
import java.util.Map;

public class ParamFilterTestEntity {

    @ParamFilter(ParamFilterEnum.NOT_EMPTY_STRING)
    private String name;
    @ParamFilter(ParamFilterEnum.NOT_NULL)
    private Object obj;
    @ParamFilter(ParamFilterEnum.NOT_EMPTY_LIST)
    private List<Object> list;
    @ParamFilter(ParamFilterEnum.NOT_EMPTY_MAP)
    private Map<String, Object> map;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "ParamFitlerTestEntity{" +
                "name='" + name + '\'' +
                ", obj=" + obj +
                ", list=" + list +
                ", map=" + map +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        ParamFilterTestEntity entity = new ParamFilterTestEntity();

        public Builder name(String name) {
            this.entity.setName(name);
            return this;
        }

        public Builder obj(Object obj) {
            this.entity.setObj(obj);
            return this;
        }

        public Builder list(List<Object> list) {
            this.entity.setList(list);
            return this;
        }

        public Builder map(Map<String, Object> map) {
            this.entity.setMap(map);
            return this;
        }

        public ParamFilterTestEntity build() {
            return this.entity;
        }
    }
}
