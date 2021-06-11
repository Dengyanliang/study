package com.deng.study.rpc.server;


import com.deng.study.rpc.common.RpcAnnotation;
import com.deng.study.rpc.service.client.IRegisterService;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ZkServer {

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,30,
                    30,TimeUnit.SECONDS,new ArrayBlockingQueue<>(500));

    /**
     * 注册中心
     */
    private IRegisterService registerService;

    /**
     * 服务器的发布地址
     */
    private String address;

    private Map<String,Object> handlerMap = new ConcurrentHashMap<>();

    public ZkServer(IRegisterService registerService, String address) {
        this.registerService = registerService;
        this.address = address;
    }

    public void bind(Object...services){
        for (Object service : services) {
            RpcAnnotation annotation = service.getClass().getAnnotation(RpcAnnotation.class);
            String serviceName = annotation.value().getName();
            String version = annotation.version();
            if(StringUtils.isNoneBlank(version)){
                serviceName = serviceName + "-" + version;
            }

            handlerMap.put(serviceName,service);
        }
    }

    public void publishServer(){
        try {
            // 获取端口号
            String[] str = address.split(":");
            ServerSocket serverSocket = new ServerSocket(Integer.valueOf(str[1]));
            while (true){
                Socket accept = serverSocket.accept();
                threadPoolExecutor.execute(new ProcessHandler(accept,handlerMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
