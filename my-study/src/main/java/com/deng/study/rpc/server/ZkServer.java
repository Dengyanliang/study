package com.deng.study.rpc.server;


import com.deng.study.rpc.common.RpcAnnotation;
import com.deng.study.rpc.service.server.RegisterService;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;
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
    private RegisterService registerService;

    /**
     * 服务器的发布地址
     */
    private String serverAddress;

    private Map<String,Object> handlerMap = new ConcurrentHashMap<>();

    public ZkServer(RegisterService registerService, String serverAddress) {
        this.registerService = registerService;
        this.serverAddress = serverAddress;
    }

    public void bind(Object...services){
        for (Object service : services) {
            RpcAnnotation annotation = service.getClass().getAnnotation(RpcAnnotation.class);
            if(Objects.nonNull(annotation)){
                String serviceName = annotation.value().getName();
                String version = annotation.version();
                if(StringUtils.isNoneBlank(version)){
                    serviceName = serviceName + "-" + version;
                }

                handlerMap.put(serviceName,service);
            }
        }
    }

    public void publishServer(){
        try {
            // 获取端口号
            String[] str = serverAddress.split(":");
            ServerSocket serverSocket = new ServerSocket(Integer.valueOf(str[1]));
            for (String interfaceName : handlerMap.keySet()) {
                registerService.register(interfaceName,serverAddress);
                System.out.println("注册服务成功，interfaceName:"+interfaceName+",serverAddress:"+serverAddress);
            }

            while (true){
                Socket accept = serverSocket.accept();
                System.out.println("------------accept------------");
                threadPoolExecutor.execute(new ProcessHandler(accept,handlerMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
