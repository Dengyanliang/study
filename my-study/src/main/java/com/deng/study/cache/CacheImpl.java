package com.deng.study.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/1/29 14:27
 */
public class CacheImpl implements Cache {

    /**
     * 批量获取数据
     * 1、根据集合id从缓存中获取所有数据
     * 2、筛选出缓存中存在的数据
     * 3、筛选出缓存中不存在的数据
     * 4、根据不存在的数据去查询数据库
     * 5、把查询到的数据放入到缓存中
     */
    public void test(){
//        List<String> orgList = Lists.newArrayList();
//        try {
//            orgList= cache.batchGetOrg(tenantId,orgIds);
//            List<String> cachedOrgIdList = orgList.stream().map(Org::getOrgId).collect(Collectors.toList());
//            List<String> unCachedOrgIdList = orgIds.stream().filter(mis -> !cachedOrgIdList.contains(mis)).collect(Collectors.toList());
//            // 未使用缓存
//            if (CollectionUtils.isNotEmpty(unCachedOrgIdList)) {
//                OrgRequestContext.setRequestHeader(RequestHeader.of().setDataScope(DataScope.of().setTenantId(tenantId).setSources("ALL")));
//
//                List<Org> unCachedOrgList = orgRpcHolder.getOrgService(tenantId).batchQuery(orgIds, null);
//                orgList.addAll(unCachedOrgList);
//                cache.batchSetOrg(tenantId, unCachedOrgList);
//            }
//        } catch (MDMThriftException e) {
//            LOGGER.warn("OrgRpc.getByOrgIds error, orgIds:{}.", orgIds, e);
//        }
    }


    public void test2(){
        String key = "";
        ListeningExecutorService backgroundRefreshPools =
                MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(20));
        LoadingCache<String, Optional<List<String>>> caches = CacheBuilder.newBuilder()
                .maximumSize(100)
                .refreshAfterWrite(1000, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, Optional<List<String>>>() {
                    @Override
                    public Optional<List<String>> load(String key) throws Exception {
                        return Optional.ofNullable(MockDB.getCityListFromDB(key));
                    }

                    @Override
                    public ListenableFuture<Optional<List<String>>> reload(String key,
                                                                           Optional<List<String>> optionalStrings) throws Exception {
                        return backgroundRefreshPools.submit(new java.util.concurrent.Callable<Optional<List<String>>>() {
                            @Override
                            public Optional<List<String>> call() throws Exception {
                                System.out.println(Thread.currentThread().getName()+"定时异步去更新");
                                return Optional.ofNullable(MockDB.getCityListFromDB(key));
                            }
                        });
                    }
                });


        try {
            Thread.sleep(2000);
            System.out.println("第一次拿："+caches.get(key));
            Thread.sleep(2000);
            System.out.println("第二次拿："+caches.get(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
