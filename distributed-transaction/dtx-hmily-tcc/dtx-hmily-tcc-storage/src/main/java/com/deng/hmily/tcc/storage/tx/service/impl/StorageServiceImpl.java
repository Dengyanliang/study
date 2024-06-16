package com.deng.hmily.tcc.storage.tx.service.impl;

import com.alibaba.fastjson.JSON;
import com.deng.hmily.tcc.storage.tx.dao.mapper.ProductMapper;
import com.deng.hmily.tcc.storage.tx.dao.mapper.TccLocalCancelLogMapper;
import com.deng.hmily.tcc.storage.tx.dao.mapper.TccLocalConfirmLogMapper;
import com.deng.hmily.tcc.storage.tx.dao.mapper.TccLocalTryLogMapper;
import com.deng.hmily.tcc.storage.tx.dao.po.Product;
import com.deng.hmily.tcc.storage.tx.dao.po.TccLocalCancelLog;
import com.deng.hmily.tcc.storage.tx.dao.po.TccLocalConfirmLog;
import com.deng.hmily.tcc.storage.tx.dao.po.TccLocalTryLog;
import com.deng.hmily.tcc.storage.tx.facade.request.StorageRequest;
import com.deng.hmily.tcc.storage.tx.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.core.concurrent.threadlocal.HmilyTransactionContextLocal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private TccLocalTryLogMapper tccLocalTryLogMapper;

    @Resource
    private TccLocalConfirmLogMapper tccLocalConfirmLogMapper;

    @Resource
    private TccLocalCancelLogMapper tccLocalCancelLogMapper;

    @Override
    @Hmily(confirmMethod = "commitFreezeStock",cancelMethod = "rollbackFreezeStock")
    @Transactional(rollbackFor = Exception.class)
    public void tryFreezeStock(StorageRequest request) {
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("freezeStock try 开始执行，txNo:{}",txNo);

        if(Objects.nonNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("freezeStock try 已经执行，无需重复执行...txNo:{}",txNo);
            return;
        }

        // try悬挂判断，如果confirm、cancel有一个已经执行了，try不再执行  todo 这里应该只用判断cancel的吧？
        if(Objects.nonNull(tccLocalConfirmLogMapper.selectByPrimaryKey(txNo)) || Objects.nonNull(tccLocalCancelLogMapper.selectByPrimaryKey(txNo))){
            log.info("freezeStock try悬挂处理，confirm或者cancel有一个已经执行了，try不能再执行，txNo:{}",txNo);
            return;
        }
        // 测试
        Product dbProduct = getProduct(request,txNo);

        // try 冻结库存=冻结库存+count
        int count = productMapper.addFreezeStockAndCheckStockById(dbProduct.getId(),request.getCount());
        if(count <= 0){
            throw new RuntimeException("增加冻结库存失败");
        }

        // 添加try日志
        TccLocalTryLog tryLog = new TccLocalTryLog();
        tryLog.setTxNo(txNo);
        tryLog.setCreateTime(new Date());
        tccLocalTryLogMapper.insert(tryLog);
        log.info("freezeStock try 执行结束，txNo:{}",txNo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void commitFreezeStock(StorageRequest request){
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("freezeStock confirm 开始执行，txNo:{}",txNo);

        // confirm实现幂等
        if(Objects.nonNull(tccLocalConfirmLogMapper.selectByPrimaryKey(txNo))){
            log.info("freezeStock confirm 已经执行，无需重复执行...txNo:{}",txNo);
            return;
        }

        TccLocalConfirmLog confirmLog = new TccLocalConfirmLog();
        confirmLog.setTxNo(txNo);
        confirmLog.setCreateTime(new Date());
        tccLocalConfirmLogMapper.insert(confirmLog);

        Product dbProduct = getProduct(request,txNo);

        // confirm 冻结库存=冻结库存-count，库存=库存-count
        int count = productMapper.deductStockAndFreezeStockById(dbProduct.getId(),request.getCount());
        if(count <= 0){
            throw new RuntimeException("扣减库存和冻结库存失败");
        }

        log.info("freezeStock confirm 执行结束，txNo:{}",txNo);
    }


    @Transactional(rollbackFor = Exception.class)
    public void rollbackFreezeStock(StorageRequest request){
        String txNo = HmilyTransactionContextLocal.getInstance().get().getTransId();
        log.info("freezeStock cancel 开始执行，txNo:{}",txNo);

        // cancel空回滚处理，如果try没有执行，cancel不允许执行
        if(Objects.isNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
            log.info("freezeStock 空回滚处理，try没有执行，不允许cancel执行，txNo:{}",txNo);
            return;
        }

        // cancel幂等校验
        if(Objects.nonNull(tccLocalCancelLogMapper.selectByPrimaryKey(txNo))){
            log.info("freezeStock cancel 已经执行，无需重复执行，txNo:{}",txNo);
            return;
        }

        TccLocalCancelLog cancelLog = new TccLocalCancelLog();
        cancelLog.setTxNo(txNo);
        cancelLog.setCreateTime(new Date());
        tccLocalCancelLogMapper.insert(cancelLog);

        Product dbProduct = getProduct(request,txNo);

        // cancel  冻结库存=冻结库存-count
        int count = productMapper.deductFreezeStockById(dbProduct.getId(),request.getCount());
        if(count <= 0){
            throw new RuntimeException("扣减冻结库存失败");
        }

        log.info("freezeStock cancel 执行结束，txNo:{}",txNo);
    }

    private Product getProduct(StorageRequest request, String txNo){
        Product product = productMapper.selectByPrimaryKey(request.getProductId());
        if (Objects.isNull(product)) {
            log.info("商品信息为空，txNo:{},request:{}", txNo, JSON.toJSON(request));
            throw new RuntimeException("商品信息为空");
        }
        return product;
    }
}
