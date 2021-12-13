package com.deng.study.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduceQueue {

    private static final String MY_BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws Exception {
        // 创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MY_BROKER_URL);
        // 通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        // 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（具体是队列还是主体）
        Queue queue = session.createQueue(QUEUE_NAME);

        // 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        // 通过使用messageProducer把生产消息发送到MQ的队列里面
        for (int i = 1; i <= 5; i++) {
            // 通过session创建消息
            TextMessage textMessage = session.createTextMessage("message--" + i);
//            textMessage.setStringProperty("c01","vip"); // 设置优先级

            // 设置持久性和非持久性，默认持久性
//            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            messageProducer.send(textMessage);

//            MapMessage mapMessage = session.createMapMessage();
//            mapMessage.setString("k_"+i,"v_"+i);
//            messageProducer.send(mapMessage);
        }

        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*****消息发送到MQ完成*****");
    }
}
