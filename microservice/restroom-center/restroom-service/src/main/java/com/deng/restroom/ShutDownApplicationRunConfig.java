package com.deng.restroom;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;
import com.alibaba.nacos.common.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.omg.SendingContext.RunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * ApplicationRunner 作用是在Spring应用程序完全启动之后，执行一些任务或处理逻辑
 *
 * @Desc: 优雅停机
 * @Date: 2024/4/2 19:57
 * @Auther: dengyanliang
 */
@Slf4j
@Component
public class ShutDownApplicationRunConfig implements ApplicationRunner {

    @Autowired
    private NacosAutoServiceRegistration nacosAutoServiceRegistration; // nacos自动注册功能

    private final Integer waitTime = 3000; // 等待时间

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Thread shutdown = new Thread(()->{
            log.info("接收到命令，开始注销nacos注册");
            nacosAutoServiceRegistration.destroy(); // 关闭服务，服务下线
            log.info("注销nacos注册成功");

            log.info("sleep{}毫秒，等待其他服务刷新nacos缓存",waitTime);
            ThreadUtils.sleep(waitTime);
        });

        Runtime.getRuntime().addShutdownHook(shutdown);
    }
}
