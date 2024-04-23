package com.deng.study.shardingsphere;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.deng.common.source.Stopwatch;
import com.deng.common.util.MyThreadUtil;
import com.deng.study.shardingsphere.dao.mapper.UdictMapper;
import com.deng.study.shardingsphere.dao.po.MyOrder;
import com.deng.study.shardingsphere.dao.po.Order;
import com.deng.study.shardingsphere.dao.po.PayOrder;
import com.deng.study.shardingsphere.dao.po.Udict;
import com.deng.study.shardingsphere.service.MyOrderService;
import com.deng.study.shardingsphere.service.OrderService;
import com.deng.study.shardingsphere.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/14 20:34
 */
@Slf4j

@SpringBootTest(classes = ShardingShpereApplication.class)
public class ShardingJdbcTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MyOrderService myOrderService;

    @Autowired
    private UdictMapper udictMapper;

    @Autowired
    private PayOrderService payOrderService;

    @Test
    public void addMyOrder(){
        Random random = new Random();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            MyOrder myOrder = new MyOrder();
            myOrder.setName("化学--"+i);
            myOrder.setUserId((long) random.nextInt(1000000));
            myOrder.setStatus("normal");
            myOrder.setCreateTime(DateUtil.now());
            myOrder.setUpdateTime(DateUtil.now());

            myOrderService.addMyOrder(myOrder);
        }
        long end = System.currentTimeMillis();
        System.out.println("耗费：" + (end-start));
    }

    @Test
    public void addBatchMyOrder(){
        long start = System.currentTimeMillis();
        int maxSize = 10000000;
        Random random = new Random();
        List<MyOrder> list = new ArrayList<>();
        for (int i = 1; i <= maxSize; i++) {
            MyOrder myOrder = new MyOrder();
            myOrder.setName("测试-" + i);
            myOrder.setUserId((long) random.nextInt(100000000));
            myOrder.setStatus("normal");
            list.add(myOrder);
        }
        myOrderService.addBatchMyOrderByPreparedStatement(list);

        long end = System.currentTimeMillis();
        System.out.println("耗费：" + (end-start));
    }

    /**
     * 分库分表都能使用
     */
    @Test
    public void addOrder(){
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            Order order = new Order();
            order.setOrderNo("Order_No_"+ UUID.randomUUID().toString());
            order.setName("测试事务------"+i);
            order.setUserId((long) random.nextInt(100));
            order.setStatus("normal");
            order.setCreateTime(DateUtil.now());
            order.setUpdateTime(DateUtil.now());

//            courseMapper.insert(order);
            orderService.addOrder(order);
        }
    }

    /**
     * 测试分表不分库
     */
    @Test
    public void getOrderByTable(){
        for (int i = 0; i < 10; i++) {
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_id",1428868022394122241L);
//        Order order = courseMapper.selectOne(queryWrapper);

            Order order = orderService.getOrder(queryWrapper);
            System.out.println(order);

            if(order.getUserId().intValue() == 53){
                System.out.println("=============");
            }
        }
    }

    /**
     * 测试分库分表
     */
    @Test
    public void getOrderByDbAndTable(){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",59);
        queryWrapper.eq("order_id",1433960431667847169L);
        Order order = orderService.getOrder(queryWrapper);
        System.out.println(order);
    }

    @Test
    public void addDict(){
        Udict udict = new Udict();
        udict.setValue("2");
        udict.setStatus("已执行");

        udictMapper.insert(udict);
    }

    @Test
    public void deleteDictById(){
       QueryWrapper<Udict> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("id",1426833054297960450L);
       udictMapper.delete(queryWrapper);
    }

    @Test
    public void addPayOrder(){
        long start = System.currentTimeMillis();
        int max = 10;
        PayOrder order = null;
        for(int i = 1; i <= max; i++){
            order = new PayOrder();
            order.setUserId((long)(Math.random()*10000));
            order.setProductId((long)(Math.random()*100000));
            order.setOrderFee((long)(Math.random()*100));
            order.setPayStatus(1);
            order.setCreateTime(DateUtil.now());
            order.setPayFinishTime(DateUtil.now());
            order.setVersion(1);
            payOrderService.addPayOrder(order);
        }
        long end = System.currentTimeMillis();
        log.info("总的耗时：{}",end-start);
    }


    /**
     * 数据迁移，将一张1000万的数据，迁移到2库2表中，2*2=4
     *
     */
    @Test
    public void transfer(){

        Stopwatch stopwatch = new Stopwatch();
        IPage<MyOrder> queryPage = new Page<>(1,10000);

//        QueryWrapper<MyOrder> queryWrapper = new QueryWrapper<>();
//        queryWrapper.between("create_Time","2023-08-11 21:35:56","2023-08-11 23:59:59");
        IPage<MyOrder> page = null;
        List<MyOrder> records = null;
        
        int count = 0;
        while(true){
            page = myOrderService.getMyOrderByPage(queryPage, null);
            records = page.getRecords();
            if(CollectionUtils.isEmpty(records)){
                break;
            }
            count += records.size();

//            List<Order>  orderList = new ArrayList<>();
            for (MyOrder MyOrder : records) {
                Order order = new Order();
                BeanUtils.copyProperties(MyOrder,order);
//                order.setOrderId(MyOrder.getId());

//                orderList.add(order);
                orderService.addOrder(order);
            }
//            orderService.batchAdd(orderList);

            System.out.println("迁移了---" +count+ "---条记录");

            queryPage.setCurrent(queryPage.getCurrent()+1);
        }


        long elapsedTime = stopwatch.elapsedTime();
        System.out.println("执行任务，耗时：" + elapsedTime);

        MyThreadUtil.sleep(1000);

//        System.out.println(courseAll.size());

    }


    @Test
    public void test(){
        long id = 901582505772056576l;
        boolean result = id % 4 == 0;
        System.out.println(result);
    }

}
