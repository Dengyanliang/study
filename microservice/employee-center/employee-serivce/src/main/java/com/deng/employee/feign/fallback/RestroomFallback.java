package com.deng.employee.feign.fallback;

import com.deng.employee.feign.RestroomFeignClient;
import com.deng.employee.feign.response.Toilet;
import com.deng.employee.feign.response.ToiletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 降级策略
 */
@Component
@Slf4j
public class RestroomFallback implements RestroomFeignClient {

    @Override
    public Toilet getToilet(Long id) {
        log.info("getToilet fallback.......");
        return null;
    }

    @Override
    public List<Toilet> getAvailableToilet() {
        log.info("getAvailableToilet fallback.......");
        return null;
    }

    @Override
    public Toilet occupy(Long id) {
        log.info("occupy fallback.......");
        return null;
    }

    @Override
    public Toilet release(Long id) {
        log.info("getAvailableToilet release.......");
        return null;
    }

    @Override
    public void test(Long id) {
        log.info("getAvailableToilet test.......");
    }

    @Override
    public ResponseEntity<byte[]> test2(String id) {
        log.info("getAvailableToilet test2.......");
        return null;
    }

    @Override
    public ToiletResponse proto(String id) {
        log.info("getAvailableToilet proto.......");
        return null;
    }

//    @Override
//    public Toilet releaseTCC(BusinessActionContext actionContext, Long id) {
//        return null;
//    }

    @Override
    public Toilet releaseTCC(Long id) {
        log.info("getAvailableToilet releaseTCC.......");
        return null;
    }
}
