package com.deng.restroom.convert;

import com.deng.restroom.entity.ToiletEntity;
import com.deng.restroom.pojo.Toilet;

public class ToiletConverter {

    public static Toilet convert(ToiletEntity entity) {
        return Toilet.builder()
                .id(entity.getId())
                .clean(entity.isClean())
                .available(entity.isAvailable())
                .build();
    }

}
