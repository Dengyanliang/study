package com.deng.study.domain;


import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

@Getter
public class BaseContext<I> {
    public static final String REQ_KEY = "req";

    private I req;

    private Map<String, Object> runData = Maps.newConcurrentMap();

    public BaseContext(I req) {
        this.req = req;
        put(REQ_KEY,req);
    }

    public void put(String key, Object object) {
        runData.put(key, object);
    }
}
