package com.deng.study.rpc.client;

import com.deng.study.rpc.model.RequestModel;
import com.deng.study.rpc.model.ResponseModel;
import com.deng.study.rpc.service.HelloService;
import com.deng.study.rpc.service.HelloServiceImpl;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * RPC客户端
 */
public class RPCClient {

    public static void main(String[] args) {
        try {
            // 客户端与指定的主机和端口进行连接
            Socket socket = new Socket("127.0.0.1",8888);

            // 创建一个业务接口对象，模拟客户端发起调用
            HelloService helloService = new HelloServiceImpl();

            // 该传输对象存储了客户端发起调用业务对象的一些信息
            RequestModel requestModel = new RequestModel();
            // 将要被调用的对象
            requestModel.setObject(helloService);

            // 获取业务对象的字节码信息
            Class clazz = helloService.getClass();
            // 将要被调用的方法
            Method method = clazz.getMethod("sayHello", String.class);
            // 被调用方法的参数类型
            requestModel.setParameterTypes( method.getParameterTypes());
            // 被调用方法的参数
            requestModel.setParameters(new Object[]{"RPC TEST"});
            requestModel.setMethodName(method.getName());

            // 将数据请求对象传给服务端。将对象转化成流，也就是进行序列化，方便在网络中传输
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(requestModel);
            oos.flush();
            byte[] bytes = bos.toByteArray();

            // 获得socket输出流，通过该流可以将数据传输到服务端
            OutputStream os = socket.getOutputStream();
            // 往流中写入需要进行传输的序列化后的流信息
            os.write(bytes);
            os.flush();

            // 因为socket建立的是长连接，所以将流数据传输到服务端后，可以通过输入流获取到返回的流数据信息
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);

            // 将读到的数据，强转为数据模型传输对象，从对象中获取到返回的数据
            ResponseModel readObject = (ResponseModel)ois.readObject();
            System.out.println("得到结果："+ readObject.getResult());

            socket.close();
            System.out.println("客户端调用结束");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
