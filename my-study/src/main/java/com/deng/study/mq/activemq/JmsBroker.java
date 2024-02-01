//package com.deng.study.mq.activemq;
//
//import org.apache.activemq.broker.BrokerService;
//
//public class JmsBroker {
//
//    public static void main(String[] args) throws Exception {
//        BrokerService brokerService = new BrokerService();
//        brokerService.setUseJmx(true);
//        brokerService.addConnector("tcp://localhost:61618"); // 端口不是61616，为了测试
//        brokerService.start();
//    }
//}
