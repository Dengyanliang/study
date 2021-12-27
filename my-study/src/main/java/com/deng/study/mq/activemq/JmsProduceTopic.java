package com.deng.study.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;
import java.util.UUID;

public class JmsProduceTopic {

//    private static final String MY_BROKER_URL = "tcp://localhost:61618"; // 本地brokerUrl
    private static final String MY_BROKER_URL = "tcp://localhost:61616"; // 系统默认的url

    private static final String TOPIC_NAME = "topic01";

    private static Connection getConnection() throws JMSException {
        // 创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MY_BROKER_URL);
        // 通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        return connection;
    }


    private static void normal_test() throws Exception {
        Connection connection = getConnection();

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

    private static void transaction_test() throws JMSException {
        Connection connection = getConnection();

        // 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（具体是队列还是主体）
        Topic topic = session.createTopic(TOPIC_NAME);

        // 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);
        // 通过使用messageProducer把生产消息发送到MQ的队列里面
        for (int i = 1; i <= 5; i++) {
            // 通过session创建消息
            TextMessage textMessage = session.createTextMessage("message--" + i);
//            messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
            messageProducer.send(textMessage);
        }

        session.commit();
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*****消息发送到MQ完成*****");
    }

    private static void persist_test() throws JMSException {
        // 创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MY_BROKER_URL);
        // 通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        // 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（具体是队列还是主体）
        Topic topic = session.createTopic(TOPIC_NAME);

        // 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // 通过使用messageProducer把生产消息发送到MQ的队列里面
        for (int i = 1; i <= 5; i++) {
            // 通过session创建消息
            TextMessage textMessage = session.createTextMessage("message--" + i);
            messageProducer.send(textMessage);
        }

        session.commit();
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*****消息发送到MQ完成*****");
    }

    /**
     * 异步发送
     * @throws JMSException
     */
    private static void async_send_test() throws JMSException {
        // 创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MY_BROKER_URL);
        activeMQConnectionFactory.setUseAsyncSend(true); // 支持异步投递
        // 通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        // 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（具体是队列还是主体）
        Topic topic = session.createTopic(TOPIC_NAME);

        // 创建消息的生产者
        ActiveMQMessageProducer messageProducer = (ActiveMQMessageProducer)session.createProducer(topic);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // 通过使用messageProducer把生产消息发送到MQ的队列里面
        for (int i = 1; i <= 5; i++) {
            // 通过session创建消息
            TextMessage textMessage = session.createTextMessage("message--" + i);
            textMessage.setJMSMessageID(UUID.randomUUID().toString()+"----test");
            String msgID = textMessage.getJMSMessageID();
            messageProducer.send(textMessage, new AsyncCallback() {

                @Override
                public void onException(JMSException e) {
                    System.out.println(msgID + " 发送失败");
                }

                @Override
                public void onSuccess() {
                    System.out.println(msgID + " 已经发送成功");
                }
            });
        }

        session.commit();
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*****消息发送到MQ完成*****");
    }

    /**
     * 延迟重复投递
     * @throws JMSException
     */
    private static void delay_test() throws JMSException {
        // 创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MY_BROKER_URL);
        // 通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        // 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（具体是队列还是主体）
        Topic topic = session.createTopic(TOPIC_NAME);

        // 创建消息的生产者
        ActiveMQMessageProducer messageProducer = (ActiveMQMessageProducer)session.createProducer(topic);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        long delay = 3*1000;
        long period = 4*1000;
        int repeat = 2;


        // 通过使用messageProducer把生产消息发送到MQ的队列里面
        for (int i = 1; i <= 5; i++) {
            // 通过session创建消息
            TextMessage textMessage = session.createTextMessage("delay message--" + i);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,delay); // 延迟投递时间
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD,period); // 重复投递的时间间隔
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT,repeat); // 重复投递次数

            textMessage.setJMSMessageID(UUID.randomUUID().toString()+"----test");
            messageProducer.send(textMessage);
        }

        session.commit();
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*****消息发送到MQ完成*****");
    }

    private static void redelivery_test() throws JMSException {
        // 创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MY_BROKER_URL);
        // 通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        // 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（具体是队列还是主体）
        Topic topic = session.createTopic(TOPIC_NAME);

        // 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // 通过使用messageProducer把生产消息发送到MQ的队列里面
        for (int i = 1; i <= 5; i++) {
            // 通过session创建消息
            TextMessage textMessage = session.createTextMessage("message--" + i);
            messageProducer.send(textMessage);
        }

        session.commit();
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*****消息发送到MQ完成*****");
    }

    public static void main(String[] args) throws Exception {
        redelivery_test();
    }
}
