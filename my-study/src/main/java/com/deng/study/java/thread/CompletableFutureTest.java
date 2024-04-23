package com.deng.study.java.thread;

import com.deng.common.util.MyThreadUtil;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/2/21 18:41
 */
public class CompletableFutureTest {
    private static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("taobao"),
            new NetMall("dangdang"),
            new NetMall("pinduoduo"),
            new NetMall("tmall")
    );

    private static List<List<NetMall>> lists = new CopyOnWriteArrayList<>();

    static {
        List<NetMall> list1 = new ArrayList<>();
        list1.add(new NetMall("jd","mysql"));
        list1.add(new NetMall("taobao","mysql"));
        list1.add(new NetMall("dangdang","mysql"));

        List<NetMall> list2 = new ArrayList<>();
        list2.add(new NetMall("pinduoduo","mysql"));
        list2.add(new NetMall("tmall","mysql"));
        list2.add(new NetMall("yamaxun","mysql"));

        lists.add(list1);
        lists.add(list2);
    }


    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> mysqlList = getPrice(list, "mysql");
        for (String s : mysqlList) {
            System.out.println(s);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("---串行执行 costTime：" + (endTime - startTime) + "毫秒");

        long startTime2 = System.currentTimeMillis();
        List<String> mysqlList2 = getPriceByCompletableFuture(list, "mysql");
        for (String s : mysqlList2) {
            System.out.println(s);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("---并行执行 costTime：" + (endTime2 - startTime2) + "毫秒");



        startTime2 = System.currentTimeMillis();
        parallelHandle(lists);
        endTime2 = System.currentTimeMillis();
        System.out.println("---外部集合串行，内部集合并行执行 costTime：" + (endTime2 - startTime2) + "毫秒");
    }


    /**
     * 一家一家搜索，效率低下
     *
     * @param list        书籍集合
     * @param productName 需要搜索的书名称
     * @return
     */
    private static List<String> getPrice(List<NetMall> list, String productName) {
        return list.stream().map(netMall -> String.format("%s in %s price is %.2f", productName, netMall.getNetMallName(), netMall.getPrice(productName))).collect(Collectors.toList());
    }

    /**
     * 使用CompletableFuture并行计算
     * @param list
     * @param productName
     * @return
     */
    private static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        List<CompletableFuture<String>> completableFutureList = list.parallelStream()
                .map(netMall -> CompletableFuture.supplyAsync(() -> String.format("%s in %s price is %.2f", productName, netMall.getNetMallName(), netMall.getPrice(productName))))
                .collect(Collectors.toList());
        return completableFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    private static void parallelHandle(List<List<NetMall>> lists){
        for (List<NetMall> netMalls : lists) {
            List<CompletableFuture<Void>> completableFutureList = netMalls.stream()
                    .map(netMall -> CompletableFuture.runAsync(netMall)).collect(Collectors.toList());
            List<Void> collect = completableFutureList.stream().map(CompletableFuture::join).collect(Collectors.toList());
            System.out.println("执行完了....");
        }
    }


    @Test
    public void testMethod() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            MyThreadUtil.sleep(2000);
            return "abc";
        });

//        System.out.println(future.get());
//        System.out.println(future.get(1, TimeUnit.SECONDS));
//        System.out.println(future.join());
//        System.out.println(future.getNow("sss"));

        MyThreadUtil.sleep(1000);

        // 在一定的时间内是否完成了，如果没有完成，则返回参数值； // false abc
        // 如果完成了，则按照结果返回                          // true complete
//        boolean complete = future.complete("complete");
        System.out.println(future.complete("complete2")+" " + future.join());

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(()->{
            System.out.println("当期线程："+Thread.currentThread().getName());
            MyThreadUtil.sleep(1000);
        });
        System.out.println(future2.get());
    }

}

@Data
class NetMall implements Runnable{
    private String netMallName;
    private String productName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public NetMall(String netMallName, String productName) {
        this.netMallName = netMallName;
        this.productName = productName;
    }

    public double getPrice(String productName) {
        MyThreadUtil.sleep(100);
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }

    @Override
    public void run() {
        String printStr = String.format("%s in %s price is %.2f", productName, netMallName, getPrice(productName));
        System.out.println(printStr);
    }
}