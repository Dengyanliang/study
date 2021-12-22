package com.deng.study.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.Objects;

public class JmsConsumerTopic {

    private static final String MY_BROKER_URL = "tcp://localhost:61618"; // 本地brokerUrl
//    private static final String MY_BROKER_URL = "tcp://localhost:61616"; // 系统默认的url
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

        System.out.println("我是1号消费者");

        // 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（具体是队列还是主体）
        Topic topic = session.createTopic(TOPIC_NAME);
        // 创建消息的接收者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        // 通过receive接收消息
//        while (true){
////            Message message = messageConsumer.receive(); // 阻塞式的，没有过期时间
//            Message message = messageConsumer.receive(5000); // 阻塞式的，有过期时间
//            if(Objects.nonNull(message)){
//                if(message instanceof TextMessage){
//                    TextMessage textMessage = (TextMessage)message;
//                    String text = textMessage.getText();
//                    System.out.println(text);
//                }
//            }else{
//                break;
//            }
//        }

        // 通过messageListener接收消息
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(Objects.nonNull(message)) {
                    if (message instanceof TextMessage) {
                        try {
                            TextMessage textMessage = (TextMessage) message;
                            String text = textMessage.getText();
                            System.out.println(text);
                        } catch (JMSException e) {
                            System.out.println("接收消息失败。。。。");
                        }
                    }
                }
            }
        });

        System.in.read();// press any key to exit 没有这一行系统会直接处理完，显示不出消息

        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("*****从MQ接收消息完成*****");

        /**
         * 1、先生产，只启动1号消费者。问题：1号消费者能消费消息吗？ N
         *
         * 2、先生产，先启动1号消费者，再启动2号消费者。问题：2号消费者能消费消息吗？
         *   2.1 1号可以消费 N
         *   2.2 2号可以消费 N
         *
         * 3、先启动2个消费者，再生产6条消息。问题：消费情况如何？
         *   3.1 2个消费者都有6条  Y
         *   3.2 先到先得，6条全给其中一个 N
         *   3.3 一人一半  N
         */
    }

    private static void transaction_test() throws JMSException, IOException {
        Connection connection = getConnection();

        System.out.println("我是1号消费者");

        // 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（具体是队列还是主体）
        Topic topic = session.createTopic(TOPIC_NAME);
        // 创建消息的接收者
        MessageConsumer messageConsumer = session.createConsumer(topic);

        //同步阻塞方式receive
        while (true){
            Message message = messageConsumer.receive(); // 阻塞式的，有过期时间
            if(Objects.nonNull(message)){
                if(message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    String text = textMessage.getText();
                    System.out.println(text);
//                    textMessage.acknowledge();
                }
            }else{
                break;
            }
        }
        session.commit();
        System.in.read();// press any key to exit 没有这一行系统会直接处理完，显示不出消息

        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("*****从MQ接收消息完成*****");
    }

    /**
     * 先运行一次消费者，用于注册
     * 后面随时启动，随时消费，就像微信公众号一样
     *
     * @throws JMSException
     * @throws IOException
     */
    private static void persist_test() throws JMSException {
        // 创建连接工厂，按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MY_BROKER_URL);
        // 通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("zhangsan"); // 必须设置，不然会报错 You cannot create a durable subscriber without specifying a unique clientID on a Connection
        connection.start();

        System.out.println("我是1号消费者");

        // 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建目的地（具体是队列还是主体）
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber subscriber = session.createDurableSubscriber(topic, "remark....");

        Message message = subscriber.receive(); // 阻塞式的，有过期时间
        while (Objects.nonNull(message)){
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println(text);
            }
            message = subscriber.receive(5000);
        }

        session.close();
        connection.close();
        System.out.println("*****从MQ接收消息完成*****");

    }


    public static void main(String[] args) throws Exception{
        persist_test();
    }
}
