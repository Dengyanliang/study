package com.deng.restroom.service;

import com.deng.restroom.convert.ToiletConverter;
import com.deng.restroom.dao.ToiletDao;
import com.deng.restroom.entity.ToiletEntity;
import com.deng.restroom.pojo.Toilet;
import com.deng.restroom.response.ToiletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/12/21 18:58
 */
@Slf4j
@RestController
@RequestMapping("toilet-service")
public class RestroomService implements IRestroomTccService{

    @Autowired
    private ToiletDao toiletDao;

    @GetMapping("/get")
    public Toilet getToilet(Long id){
        ToiletEntity toiletEntity = toiletDao.findById(id).
                orElseThrow(() -> new RuntimeException("toilet not found"));

        return ToiletConverter.convert(toiletEntity);
    }


    @Override
    @GetMapping("checkAvailable")
    public List<Toilet> getAvailableToilet() {
        List<ToiletEntity> list = toiletDao.findByCleanAndAvailable(true, true);

        return list.stream().
                map(ToiletConverter::convert).
                collect(Collectors.toList());
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









}
