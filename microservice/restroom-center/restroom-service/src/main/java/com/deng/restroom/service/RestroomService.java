package com.deng.restroom.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.deng.restroom.annotation.SignVerify;
import com.deng.restroom.convert.ToiletConverter;
import com.deng.restroom.dao.ToiletDao;
import com.deng.restroom.entity.ToiletEntity;
import com.deng.restroom.pojo.Toilet;
import com.deng.restroom.response.ToiletResponse;
import com.google.common.collect.Lists;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/21 18:58
 */
@Slf4j
@RestController
@RequestMapping("toilet-service")
public class RestroomService implements IRestroomTccService{

    @SignVerify
    @GetMapping("/get")
    public Toilet getToilet(Long id){
        ToiletEntity toiletEntity = toiletDao.findById(id).
                orElseThrow(() -> new RuntimeException("toilet not found"));

        return ToiletConverter.convert(toiletEntity);
    }

    @Resource
    private ToiletDao toiletDao;


    @Override
    @GetMapping("checkAvailable")
    @SentinelResource(value = "checkAvailableBySentinel",fallback = "getAvailableToiletFallback") // 限流
    public List<Toilet> getAvailableToilet() {
        throw new RuntimeException("test checkAvailable 限流和熔断");
//        List<ToiletEntity> list = toiletDao.findByCleanAndAvailable(true, true);
//
//        return list.stream().
//                map(ToiletConverter::convert).
//                collect(Collectors.toList());
    }

    public List<Toilet> getAvailableToiletFallback() {
        log.info("getAvailableToiletFallback......");
        return Lists.newArrayList();
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/occupy")
    @ResponseBody
    public Toilet occupy(Long id) {
        ToiletEntity toiletEntity = toiletDao.findById(id).orElseThrow(() -> new RuntimeException("Toilet not found"));
        if(!toiletEntity.isAvailable() || !toiletEntity.isClean()){
            throw new RuntimeException("restromm is unavailable or unclean");
        }

        toiletEntity.setClean(false);
        toiletEntity.setAvailable(false);

        toiletDao.save(toiletEntity);

        return ToiletConverter.convert(toiletEntity);
    }

    @Override
    @Transactional
    @PostMapping("/release")
    public Toilet release(Long id) {
        try {
            ToiletEntity entity = toiletDao.findById(id)
                    .orElseThrow(() -> new RuntimeException("toilet not found"));
            entity.setAvailable(true);
            entity.setClean(true);
            toiletDao.save(entity);

            return ToiletConverter.convert(entity);
        } catch (Exception e) {
            log.error("cannot occupy the restromm", e);
            throw e;
        }
    }

    @Override
    public void test(Long id) {

    }

    @Override
    public Toilet test2(String id) {
        return null;
    }

    @Override
    public ToiletResponse testProto(Long id) {
        return null;
    }


    @Override
    @Transactional
    @PostMapping("/releaseTCC")
    public Toilet releaseTCC(Long id) {
        log.info("**** Try release TCC **** id={}",id);
        ToiletEntity entity = toiletDao.findById(id)
                .filter(s -> !s.isReserved())
                .orElseThrow(() -> new RuntimeException("toilet not found"));

        entity.setReserved(true);
        toiletDao.save(entity);

        return ToiletConverter.convert(entity);
    }

    @Override
    @Transactional
    public boolean releaseCommit(BusinessActionContext actionContext) {
        try {
            log.info("class = {}" , actionContext.getActionContext("id").getClass());

            Long id = Long.parseLong(actionContext.getActionContext("id").toString());
            log.info("**** Confirm release TCC **** id={}, xid={}", id, actionContext);

            Optional<ToiletEntity> optional = toiletDao.findById(id);
            if(optional.isPresent()){
                ToiletEntity toiletEntity = optional.get();
                toiletEntity.setClean(true);
                toiletEntity.setAvailable(true);
                toiletEntity.setReserved(false);
                toiletDao.save(toiletEntity);
            }
            return true;
        } catch (Exception e) {
            log.error("releaseCommit the restroom occur execption",e);
            return false;
        }
    }

    @Override
    public boolean releaseCancel(BusinessActionContext actionContext) {
        try {
            log.info("class = {}" , actionContext.getActionContext("id").getClass()); // class java.lang.Integer

            Long id = Long.parseLong(actionContext.getActionContext("id").toString());
            log.info("**** Confirm release TCC **** id={}, xid={}", id, actionContext);

            Optional<ToiletEntity> optional = toiletDao.findById(id);
            if(optional.isPresent()){
                ToiletEntity toiletEntity = optional.get();
                toiletEntity.setClean(false);
                toiletEntity.setAvailable(false);
                toiletEntity.setReserved(false);
                toiletDao.save(toiletEntity);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("releaseCancel the restroom occur execption",e);
            return false;
        }
    }
}
