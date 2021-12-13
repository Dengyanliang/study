package com.deng.study.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduceTopic {

    private static final String MY_BROKER_URL = "tcp://localhost:61616";
    private static final String TOPIC_NAME = "topic01";

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
        Topic topic = session.createTopic(TOPIC_NAME);

        // 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);
        // 通过使用messageProducer把生产消息发送到MQ的队列里面
        for (int i = 1; i <= 5; i++) {
            // 通过session创建消息
            TextMessage textMessage = session.createTextMessage("message--" + i);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            messageProducer.send(textMessage);
        }

        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*****消息发送到MQ完成*****");
    }
}
