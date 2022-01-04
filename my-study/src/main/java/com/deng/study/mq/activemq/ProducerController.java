//package com.deng.study.mq.activemq;
//
//import com.alibaba.fastjson.JSON;
//import com.deng.study.domain.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.core.JmsMessagingTemplate;
//import org.springframework.web.bind.annotation.*;
//
//import javax.jms.Destination;
//import javax.jms.Queue;
//import javax.jms.Topic;
//
//
//@RestController
//@RequestMapping("jms")
//public class ProducerController {
//
//    @Autowired
//    private JmsMessagingTemplate jmsMessagingTemplate;
//    @Autowired
//    private Queue queue;
//    @Autowired
//    private Topic topic;
//
//    @GetMapping("/queue/test")
//    public String sendQueue(String message){
//        sendMessage(queue,message);
//        return "success";
//    }
//
//    @GetMapping("/topic/test")
//    public String sendTopic(String message){
//        sendMessage(topic,message);
//        return "success";
//    }
//
//    @PostMapping("/topic/test2")
//    public String sendTopic(@RequestBody User user){
//        String userStr = JSON.toJSONString(user); // 将对象转化为JSON，然后再发送出去
//        sendMessage(topic,userStr);
//        return "success";
//    }
//
//    private void sendMessage(Destination destination, String message){
//        jmsMessagingTemplate.convertAndSend(destination,message);
//    }
//
//
//
//}
