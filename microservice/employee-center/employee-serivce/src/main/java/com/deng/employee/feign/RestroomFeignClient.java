package com.deng.employee.feign;

import com.deng.employee.feign.fallback.RestroomFallback;
import com.deng.employee.feign.response.Toilet;
import com.deng.employee.feign.response.ToiletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/21 16:41
 */
@FeignClient(value = "restroom-service", fallback = RestroomFallback.class)
public interface RestroomFeignClient {

    @GetMapping("/toilet-service/checkAvailability")
    Toilet getToilet(@RequestParam("id") Long id);

    @GetMapping("/toilet-service/checkAvailable")
    List<Toilet> getAvailableToilet();

    @PostMapping("/toilet-service/occupy")
    Toilet occupy(@RequestParam("id") Long id);

    @PostMapping("/toilet-service/release")
    Toilet release(@RequestParam("id") Long id);

    // 测试方法
    @GetMapping("/toilet-service/checkAvailability")
    void test(@RequestParam("id") Long id);

    @GetMapping("/toilet-service/test")
    ResponseEntity<byte[]> test2(@RequestParam("id") String id);

    @RequestMapping(value = "/toilet-service/testProto",
            method = RequestMethod.POST,
            consumes = "application/x-protobuf",
            produces = "application/x-protobuf")
    ToiletResponse proto(@RequestParam("id") String id);

//    @PostMapping("/toilet-service/releaseTCC")
//    Toilet releaseTCC(@RequestBody BusinessActionContext actionContext,
//                                     @RequestParam("id") Long id);

    @PostMapping("/toilet-service/releaseTCC")
    Toilet releaseTCC(@RequestParam("id") Long id);
}
