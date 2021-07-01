package com.deng.study.rpc.client;

import com.deng.study.rpc.model.RequestModel;
import com.deng.study.rpc.model.ResponseModel;
import com.deng.study.rpc.service.client.DiscoveryService;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/6/12 08:36
 */
public class ZkRemoteInvocationHandler implements InvocationHandler {

    private DiscoveryService discoveryService;
    private String version;

    public ZkRemoteInvocationHandler(DiscoveryService discoveryService, String version) {
        this.discoveryService = discoveryService;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequestModel requestModel = new RequestModel();
        requestModel.setServiceName(method.getDeclaringClass().getName());
        requestModel.setMethodName(method.getName());
        requestModel.setParameters(args);
        requestModel.setVersion(version);

        String serviceName = method.getDeclaringClass().getName();
        if(StringUtils.isNotBlank(version)){
            serviceName = serviceName + "-" + version;
        }

        String address = discoveryService.discovery(serviceName);

        String[] addressArr = address.split(":");
        Socket socket = new Socket(addressArr[0],Integer.valueOf(addressArr[1]));

        // 发送数据
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(requestModel);
        oos.flush();

        // 接收数据
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        ResponseModel responseModel = (ResponseModel)ois.readObject();
        Object result = responseModel.getResult();
        is.close();
        ois.close();

        return result;
    }
}
