//package com.deng.study.common.configuration;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.apache.activemq.command.ActiveMQQueue;
//import org.apache.activemq.command.ActiveMQTempTopic;
//import org.apache.activemq.command.ActiveMQTopic;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jms.config.JmsListenerContainerFactory;
//import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
//import org.springframework.jms.core.JmsMessagingTemplate;
//
//import javax.jms.ConnectionFactory;
//import javax.jms.Queue;
//import javax.jms.Topic;
//
//@Configuration
//public class ActiveMqConfig {
//
//    @Value("${spring.activemq.broker-url}")
//    private String borkerUrl;
//
//    @Value("${spring.activemq.user}")
//    private String username;
//
//    @Value("${spring.activemq.password}")
//    private String password;
//
//    @Value("${spring.activemq.queue-name}")
//    private String queueName;
//
//    @Value("${spring.activemq.topic-name}")
//    private String topicName;
//
//    @Bean(name = "topic")
//    public Topic topic(){
//        return new ActiveMQTopic(topicName);
//    }
//
//    @Bean(name = "queue")
//    public Queue queue(){
//        return new ActiveMQQueue(queueName);
//    }
//
//    @Bean
//    public ConnectionFactory connectionFactory(){
//        return new ActiveMQConnectionFactory(username,password,borkerUrl);
//    }
//
//    @Bean
//    public JmsMessagingTemplate jmsMessageTemplate(){
//        return new JmsMessagingTemplate(connectionFactory());
//    }
//
//    @Bean(name = "queueListener")
//    public JmsListenerContainerFactory queueJmsListenerContainerFactory(ConnectionFactory connectionFactory){
//        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setPubSubDomain(false); // 队列模式
//        return factory;
//    }
//
//    @Bean(name = "topicListener")
//    public JmsListenerContainerFactory topicJmsListenerContainerFactory(ConnectionFactory connectionFactory){
//        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setPubSubDomain(true); // 主题模式
//        return factory;
//    }
//}
