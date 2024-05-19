package com.deng.seata.tcc.storage.facade.impl;

import com.alibaba.fastjson.JSON;
import com.deng.seata.tcc.storage.facade.StockFacade;
import com.deng.seata.tcc.storage.facade.request.StockRequest;
import com.deng.seata.tcc.storage.facade.response.StockResponse;
import com.deng.seata.tcc.storage.service.StockService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Component("stockFacade")
public class StockFacadeImpl implements StockFacade {

    @Resource
    private StockService stockService;


    @Override
    public StockResponse tryFreezeStock(StockRequest request) {
        String txNo = RootContext.getXID(); // 全局事务id
        log.info("freezeStock try 开始执行，txNo: {}", txNo);

//        if(Objects.nonNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
//            log.info("freezeStock try 已经执行，无需重复执行...txNo:{}",txNo);
//            return;
//        }
//
//        // try悬挂判断，如果confirm、cancel有一个已经执行了，try不再执行  todo 这里应该只用判断cancel的吧？
//        if(Objects.nonNull(tccLocalConfirmLogMapper.selectByPrimaryKey(txNo)) || Objects.nonNull(tccLocalCancelLogMapper.selectByPrimaryKey(txNo))){
//            log.info("freezeStock try悬挂处理，confirm或者cancel有一个已经执行了，try不能再执行，txNo:{}",txNo);
//            return;
//        }

        stockService.tryFreeze(request);

        // 添加try日志
//        TccLocalTryLog tryLog = new TccLocalTryLog();
//        tryLog.setTxNo(txNo);
//        tryLog.setCreateTime(new Date());
//        tccLocalTryLogMapper.insert(tryLog);

        log.info("freezeStock try 执行结束，txNo:{}",txNo);

        StockResponse stockResponse = new StockResponse();
        stockResponse.setCode("1000");
        stockResponse.setMessage("成功");
        return stockResponse;
    }

    public void commitFreezeStock(BusinessActionContext actionContext) {
        String txNo = actionContext.getXid(); // 全局事务id
        log.info("freezeStock confirm 开始执行，txNo:{}",txNo);

        // confirm实现幂等
//        if(Objects.nonNull(tccLocalConfirmLogMapper.selectByPrimaryKey(txNo))){
//            log.info("freezeStock confirm 已经执行，无需重复执行...txNo:{}",txNo);
//            return;
//        }

//        TccLocalConfirmLog confirmLog = new TccLocalConfirmLog();
//        confirmLog.setTxNo(txNo);
//        confirmLog.setCreateTime(new Date());
//        tccLocalConfirmLogMapper.insert(confirmLog);

        String requestString = actionContext.getActionContext("stockRequest").toString();
        log.info("》》》》commitFreezeStock 请求信息：{}", requestString);
        StockRequest stockRequest = JSON.parseObject(requestString, StockRequest.class);

        stockService.commitFreeze(stockRequest);

        log.info("freezeStock confirm 执行结束，txNo:{}",txNo);
    }


    public void cancelFreezeStock(BusinessActionContext actionContext){
        String txNo = actionContext.getXid(); // 全局事务id
        log.info("freezeStock cancel 开始执行，txNo:{}", txNo);

//        // cancel空回滚处理，如果try没有执行，cancel不允许执行
//        if(Objects.isNull(tccLocalTryLogMapper.selectByPrimaryKey(txNo))){
//            log.info("freezeStock 空回滚处理，try没有执行，不允许cancel执行，txNo:{}",txNo);
//            return;
//        }
//
//        // cancel幂等校验
//        if(Objects.nonNull(tccLocalCancelLogMapper.selectByPrimaryKey(txNo))){
//            log.info("freezeStock cancel 已经执行，无需重复执行，txNo:{}",txNo);
//            return;
//        }
//
//        TccLocalCancelLog cancelLog = new TccLocalCancelLog();
//        cancelLog.setTxNo(txNo);
//        cancelLog.setCreateTime(new Date());
//        tccLocalCancelLogMapper.insert(cancelLog);

        String requestString = actionContext.getActionContext("stockRequest").toString();
        log.info("》》》》cancelFreezeStock 请求信息：{}", requestString);
        StockRequest stockRequest = JSON.parseObject(requestString, StockRequest.class);

        stockService.cancelFreeze(stockRequest);

        log.info("freezeStock cancel 执行结束，txNo:{}",txNo);
    }

}
