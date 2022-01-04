package com.deng.study.mq.kafka;

import com.alibaba.fastjson.JSON;
import com.deng.study.domain.Pet;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Resource
    private KafkaProducer kafkaProducer;

    @PostMapping("/send")
    public String send(@RequestBody Pet pet){
        kafkaProducer.send(JSON.toJSONString(pet));
        return "success";
    }
}
