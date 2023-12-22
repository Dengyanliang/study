package com.deng.restroom.api;


import com.deng.restroom.pojo.Toilet;
import com.deng.restroom.response.ToiletResponse;

import java.util.List;

public interface IRestroomService {

    public Toilet getToilet(Long id);

    public List<Toilet> getAvailableToilet();

    public Toilet occupy(Long id);

    public Toilet release(Long id);

    abstract void test(Long id);

    public Toilet test2(String id);

    public ToiletResponse testProto(Long id);

}
