package com.deng.study.java.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.support.PagedListHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 集合的分页操作
 */
public class PageTest {

    private final static int pageSize = 2;

    /**
     * 使用spring自带的分页工具进行处理
     * 这里只能是同步处理
     * @param list
     */
    private static void method1(List<Integer> list){
        PagedListHolder<Integer> pagedListHolder = new PagedListHolder(list);
        pagedListHolder.setPageSize(pageSize);
        while(true){
            System.out.println("********************");
            pagedListHolder.getPageList().stream().forEach(System.out::println);
            if(pagedListHolder.isLastPage()){
                break;
            }
            pagedListHolder.nextPage();
        }
    }

    /**
     * 使用谷歌 guava Lists 工具处理
     * 这里可以异步处理，也可以同步处理
     * @param list
     */
    private static void method2(List<Integer> list){
        List<List<Integer>> lists = Lists.partition(list, pageSize);
        List<CompletableFuture<List<Integer>>> resutList = lists.stream().map(pageList -> CompletableFuture.supplyAsync(() -> covertList(pageList))).collect(Collectors.toList());
        for (CompletableFuture<List<Integer>> completableFuture : resutList) {
            List<Integer> futureList = completableFuture.join();
            if(CollectionUtils.isNotEmpty(futureList)){
                System.out.println("===========================");
                futureList.stream().forEach(System.out::println);
            }
        }
    }

    private static List<Integer> covertList(List<Integer> list){
        if(CollectionUtils.isNotEmpty(list)){
            return list.stream().map(i -> (i + 100)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        method1(list);
        System.out.println("--------------------------");
        method2(list);
    }
}
