package com.deng.study.rpc.server;

import com.deng.study.rpc.model.RequestModel;
import com.deng.study.rpc.model.ResponseModel;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * PRC服务端--简易版的RPC服务端
 */
public class PRCServerMain {

    public static void main(String[] args) {
        try {
            int num = 0;
            // 启动服务端，并监听8888端口
            ServerSocket serverSocket = new ServerSocket(8888);
            while(true){
                // 服务端启动后，等待客户端进行连接
                System.out.println("监听中。。。。");
                Socket accept = serverSocket.accept();
                num++;
                System.out.println("接收到第"+num+"个线程数据");

                // 获取到客户端的输入流，并将流信息读成object对象，然后强转为数据请求对象模型
                InputStream is =  accept.getInputStream();
                ObjectInputStream ois = new ObjectInputStream(is);
                RequestModel requestModel = (RequestModel) ois.readObject();

                // 获取传入对象的详细数据
                Object object = requestModel.getObject();
                String methodName = requestModel.getMethodName();
                Class<?>[] parameterTypes = requestModel.getParameterTypes();
                Object[] parameters = requestModel.getParameters();

                // 通过方法名和方法参数，得到一个方法对象
                Method method = object.getClass().getMethod(methodName,parameterTypes);

                // 通过方法对象调用目标方法，返回的是目标方法执行后返回的数据
                Object result = method.invoke(object, parameters);
                System.out.println("服务端执行的结果：" + result);

                // 获得服务端的输出流
                OutputStream os = accept.getOutputStream();
                // 建立字节流输出对象
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);

                // 创建一个数据响应模型对象，将服务端的数据传输到客户端
                ResponseModel responseModel = new ResponseModel();
                responseModel.setResult(result);
                // 把数据传输对象写入到输出流中
                oos.writeObject(responseModel);

                byte[] bytes = bos.toByteArray();

                os.write(bytes);
                os.flush();

                bos.close();
                os.close();
                System.out.println("第"+num+"个线程执行完毕");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
