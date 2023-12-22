package com.deng.employee.service;

import com.alibaba.fastjson.JSON;
import com.deng.employee.api.IEmployeeActivityService;
import com.deng.employee.dao.EmployeeActivityDao;
import com.deng.employee.entity.EmployeeActivityEntity;
import com.deng.employee.feign.RestroomFeignClient;
import com.deng.employee.feign.response.Toilet;
import com.deng.employee.pojo.ActivityType;
import com.deng.employee.pojo.EmployeeActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/21 17:43
 */
@Slf4j
@RestController
@RequestMapping("employee")
public class EmployeeService implements IEmployeeActivityService {


    @Resource
    private EmployeeActivityDao employeeActivityDao;

    @Resource
    private RestroomFeignClient restroomFeignClient;

    @Resource
    private DiscoveryClient discoveryClient;


    /**
     * 测试元数据
     * 打印出来的数据
     *
     * {
     * 	"host": "192.168.2.18",
     *
     * 	### 这就是元数据
     * 	"metadata": {
     * 		"mylover": "xin",
     *
     * 	     ### 在寻址时，就是通过返回的instanceId，识别出当前是哪个服务，在哪个具体的分区，并且ip地址是什么
     * 		"nacos.instanceId": "192.168.2.18#21000#DEFAULT#DEFAULT_GROUP@@employee-service",
     * 		"nacos.weight": "1.0",
     * 		"nacos.cluster": "DEFAULT",
     * 		"nacos.ephemeral": "true",
     * 		"nacos.healthy": "true",
     * 		"myname": "liang",
     * 		"preserved.register.source": "SPRING_CLOUD"
     *      },
     * 	"port": 21000,
     * 	"secure": false,
     * 	"serviceId": "employee-service",
     * 	"uri": "http://192.168.2.18:21000"
     * }
     */
    @GetMapping("/getInstances")
    public void getInstances() {
        List<ServiceInstance> instances = discoveryClient.getInstances("employee-service");
        for (ServiceInstance instance : instances) {
            log.info("instances = {}", JSON.toJSONString(instance));
        }
    }

    @Override
    @PostMapping("/toilet-break")
    public EmployeeActivity useToilet(Long employeeId) {

        int count = employeeActivityDao.
                countByEmployeeIdAndActivityTypeAndActive(employeeId,
                        ActivityType.TOILET_BREAK,true);
        if(count > 0){
            throw new RuntimeException("快快");
        }
        List<Toilet> toilets = restroomFeignClient.getAvailableToilet();
        if(CollectionUtils.isEmpty(toilets)){
            throw new RuntimeException("shit in uninal");
        }

        // 抢坑，这里需要用分布式事务
        Toilet toilet = restroomFeignClient.occupy(toilets.get(0).getId());

        // 保存如厕记录
        EmployeeActivityEntity toiletBreak = EmployeeActivityEntity.builder().
                employeeId(employeeId).
                active(true).
                activityType(ActivityType.TOILET_BREAK).
                resourceId(toilet.getId()).build();
        employeeActivityDao.save(toiletBreak);

        EmployeeActivity result = new EmployeeActivity();
        BeanUtils.copyProperties(toiletBreak,result);

        return result;
    }

    @Override
    public EmployeeActivity restoreToilet(Long employeeId) {
        return null;
    }
}
