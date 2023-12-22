package com.deng.restroom.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/21 16:50
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Toilet {

    private Long id;

    private boolean clean;

    private boolean available;

}
