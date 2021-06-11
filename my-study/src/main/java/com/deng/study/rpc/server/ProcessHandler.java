package com.deng.study.rpc.server;

import com.deng.study.rpc.model.RequestModel;
import com.deng.study.rpc.model.ResponseModel;
import org.apache.commons.lang3.StringUtils;

import javax.xml.ws.Response;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

public class ProcessHandler implements Runnable{

    private Socket socket;
    private Map<String,Object> handlerMap;

    public ProcessHandler(Socket socket, Map<String, Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        try {
            InputStream is = socket.getInputStream();
            ois = new ObjectInputStream(is);
            RequestModel requestModel = (RequestModel) ois.readObject();

            Object result = invoke(requestModel);

            ResponseModel responseModel = new ResponseModel();
            responseModel.setResult(result);

            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(responseModel);
            oos.flush();
            oos.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用反射进行调用目标方法
     * @param requestModel
     * @return
     */
    private Object invoke(RequestModel requestModel) {
        try {
            String serviceName = requestModel.getServiceName();
            String methodName = requestModel.getMethodName();
            Class<?>[] parameterTypes = requestModel.getParameterTypes();
            Object[] parameters = requestModel.getParameters();

            String version = requestModel.getVersion();
            if(StringUtils.isNoneBlank(version)){
                serviceName = serviceName + version;
            }
            Object service = handlerMap.get(serviceName);

            // 通过方法名和参数类型列表可以唯一确定一个方法
            Method method = service.getClass().getMethod(methodName,parameterTypes);
            // 传入参数，调用目标方法
            Object result = method.invoke(service, parameters);
            return result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
