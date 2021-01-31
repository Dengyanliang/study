package com.deng.study.cache;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
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
//        List<Org> orgList = Lists.newArrayList();
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

}
