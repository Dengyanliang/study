package com.deng.employee.feign.fallback;

import com.deng.employee.feign.RestroomFeignClient;
import com.deng.employee.feign.response.Toilet;
import com.deng.employee.feign.response.ToiletResponse;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class RestroomFallback implements RestroomFeignClient {

    @Override
    public Toilet getToilet(Long id) {
        log.info("fallback");
        return null;
    }

    @Override
    public List<Toilet> getAvailableToilet() {
        log.info("fallback");
        return null;
    }

    @Override
    public Toilet occupy(Long id) {
        log.info("fallback");
        return null;
    }

    @Override
    public Toilet release(Long id) {
        log.info("fallback");
        return null;
    }

    @Override
    public void test(Long id) {
        log.info("fallback");
    }

    @Override
    public ResponseEntity<byte[]> test2(String id) {
        log.info("fallback");
        return null;
    }

    @Override
    public ToiletResponse proto(String id) {
        log.info("fallback");
        return null;
    }

//    @Override
//    public Toilet releaseTCC(BusinessActionContext actionContext, Long id) {
//        return null;
//    }

    @Override
    public Toilet releaseTCC(Long id) {
        log.info("fallback");
        return null;
    }
}
