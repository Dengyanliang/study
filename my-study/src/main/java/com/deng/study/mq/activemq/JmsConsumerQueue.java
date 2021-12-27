package com.deng.study.mq.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.*;
import java.util.Objects;

public class JmsConsumerQueue {

    private static final String MY_BROKER_URL = "tcp://localhost:61616";
//    private static final String MY_NIO_BROKER_URL = "nio://localhost:61619";
//    private static final String MY_AUTO_NIO_BROKER_URL = "nio://localhost:61608";
//    private static final String MY_AUTO_NIO_BROKER_URL = "tcp://localhost:61608";
    private static final String QUEUE_NAME = "queue01";

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
        Queue queue = session.createQueue(QUEUE_NAME);
        // 创建消息的接收者
        MessageConsumer messageConsumer = session.createConsumer(queue);

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
                    try {
                        if (message instanceof TextMessage) {
                            TextMessage textMessage = (TextMessage) message;
                            String text = textMessage.getText();
                            System.out.println("text:"+text);

//                                String propertyValue = textMessage.getStringProperty("c01");
//                                System.out.println("propertyValue:"+propertyValue);
                        }
//                            if(message instanceof MapMessage){
//                                MapMessage mapMessage = (MapMessage)message;
//                                Enumeration mapNames = mapMessage.getMapNames();
//                                while (mapNames.hasMoreElements()){
//                                    String key = (String)mapNames.nextElement();
//                                    String value = mapMessage.getString(key);
//                                    System.out.println("key:" + key + ",value:" + value);
//                                }
//                            }
                    } catch (JMSException e) {
                        System.out.println("接收消息失败。。。。");
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
         * 1、先生产，只启动1号消费者。问题：1号消费者能消费消息吗？ Y
         *
         * 2、先生产，先启动1号消费者，再启动2号消费者。问题：2号消费者能消费消息吗？
         *   2.1 1号可以消费 Y
         *   2.2 2号可以消费 N
         *
         * 3、先启动2个消费者，再生产6条消息。问题：消费情况如何？
         *   3.1 2个消费者都有6条  N
         *   3.2 先到先得，6条全给其中一个 N
         *   3.3 一人一半  Y
         */
    }


    /**
     * 测试事务和签收
     *  1、事务为false，签收为AUTO_ACKNOWLEDGE 可以自动签收
     *  2、事务为false，签收为CLIENT_ACKNOWLEDGE 需要手动调用message.acknowledge()
     *  3、事务为true，需要手动调用session.commit()，此时签收模式不再起效果，也就是说对于优先级：事务>签收
     *
     * @throws JMSException
     */
    private static void transaction_test() throws JMSException {
        Connection connection = getConnection();

        System.out.println("我是1号消费者");

        // 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        // 创建目的地（具体是队列还是主体）
        Queue queue = session.createQueue(QUEUE_NAME);
        // 创建消息的接收者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        // 通过receive接收消息
        while (true){
            Message message = messageConsumer.receive(500); // 阻塞式的，有过期时间
            if(Objects.nonNull(message)){
                if(message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    String text = textMessage.getText();
                    System.out.println(text);
//                    textMessage.acknowledge(); // 和CLIENT_ACKNOWLEDGE搭配使用
                }
            }else{
                break;
            }
        }

//        session.commit(); // 和createSession第一个参数为true时配合使用
        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("*****从MQ接收消息完成*****");

    }

    /**
     * 测试事务和签收
     *  1、事务为false，签收为AUTO_ACKNOWLEDGE 可以自动签收
     *  2、事务为false，签收为CLIENT_ACKNOWLEDGE 需要手动调用message.acknowledge()
     *  3、事务为true，需要手动调用session.commit()，此时签收模式不再起效果，也就是说对于优先级：事务>签收
     *
     * @throws JMSException
     */
    private static void redelivery_test() throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(MY_BROKER_URL);

        // 设置重发策略
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(3); // 最大重发次数

        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        // 通过连接工厂，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        System.out.println("我是1号消费者");

        // 创建会话session
        // 两个参数，第一个叫事务/第二个叫签收
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        // 创建目的地（具体是队列还是主体）
        Queue queue = session.createQueue(QUEUE_NAME);
        // 创建消息的接收者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        // 通过receive接收消息
        while (true){
            Message message = messageConsumer.receive(500); // 阻塞式的，有过期时间
            if(Objects.nonNull(message)){
                if(message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage)message;
                    String text = textMessage.getText();
                    System.out.println(text);
//                    textMessage.acknowledge(); // 和CLIENT_ACKNOWLEDGE搭配使用
                }
            }else{
                break;
            }
        }

//        session.commit(); // 和createSession第一个参数为true时配合使用
        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("*****从MQ接收消息完成*****");

    }

    public static void main(String[] args) throws Exception{
        redelivery_test();
    }
}
