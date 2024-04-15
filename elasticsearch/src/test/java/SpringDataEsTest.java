import com.deng.study.es.dao.ProductDao;
import com.deng.study.es.domain.ProductDomain;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.index.query.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/9 23:14
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataEsTest {

    // 主要是为了查询
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    // 主要为了简单的CRUD
    @Resource
    private ProductDao productDao;

    @Test
    public void createIndex(){
        System.out.println("创建索引");
    }

    @Test
    public void deleteIndex(){
        boolean flag = elasticsearchRestTemplate.deleteIndex(ProductDomain.class);
        System.out.println("删除索引：" + flag);

    }

    @Test
    public void save(){
        ProductDomain productDomain = new ProductDomain();
        productDomain.setId(2L);
        productDomain.setTitle("华为手机");
        productDomain.setCategory("手机");
        productDomain.setPrice(2999.00);
        productDomain.setImages("http://localhost:8081/image.jpg");
        productDao.save(productDomain);
    }


    @Test
    public void update(){
        ProductDomain productDomain = new ProductDomain();
        productDomain.setId(1L);
        productDomain.setTitle("苹果手机");
        productDomain.setCategory("手机");
        productDomain.setPrice(4999.00);
        productDomain.setImages("http://localhost:8081/image.jpg");

        productDao.save(productDomain);
    }

    @Test
    public void findById(){
        ProductDomain productDomain = productDao.findById(1L).get();
        System.out.println(productDomain);
    }

    @Test
    public void findAll(){
        Iterable<ProductDomain> products = productDao.findAll();
        for (ProductDomain productDomain : products) {
            System.out.println(productDomain);
        }
    }

    @Test
    public void saveAll(){
        List<ProductDomain> productDomains = new ArrayList();
        for (int i = 0; i < 10; i++) {
            ProductDomain productDomain = new ProductDomain();
            productDomain.setId(Long.valueOf(i));
            productDomain.setTitle("小米手机-"+ (i+1));
            productDomain.setCategory("小米");
            productDomain.setPrice(1999.00 + i);
            productDomain.setImages("http://www.baidu.com");

            productDomains.add(productDomain);
        }
        productDao.saveAll(productDomains);
    }

    @Test
    public void findByPage(){
        Sort sort = Sort.by(Sort.Direction.ASC,"id");
        int currentPage = 0;
        int pageSize = 5;

        PageRequest pageRequest = PageRequest.of(currentPage,pageSize,sort);

        Page<ProductDomain> productPage = productDao.findAll(pageRequest);
        for (ProductDomain productDomain : productPage) {
            System.out.println(productDomain);
        }
    }

    @Test
    public void termQuery(){
        // keypoint 使用BoolQueryBuilder这种方式，多条件查询，es用的7.x
//        Date beginDate = DateUtil.parseDate(queryDate);
//        Date endDate = DateUtils.addDays(beginDate, 1);
//        // 范围查询
//        RangeQueryBuilder timeRangeQueryBuilder = QueryBuilders.rangeQuery("sendTime").gte(beginDate.getTime()).lt(endDate.getTime());
//
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        // 如果传了手机号，则把该手机号带入查询条件；如果没有传手机号，则查询所有
//        if(StringUtils.isNotBlank(phoneNumber)){
//            queryBuilder.must(QueryBuilders.matchQuery("mobile", phoneNumber));
//        }
//        queryBuilder.must(QueryBuilders.matchQuery("sendStatus", SendStatusEnum.SENDING.getValue()));
//        queryBuilder.must(timeRangeQueryBuilder);
//
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).build();
//        SearchHits<SmsSendRecord> searchHits = elasticsearchRestTemplate.search(searchQuery, SmsSendRecord.class);

        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("category","小米");
        Iterable<ProductDomain> products = productDao.search(queryBuilder);
        for (ProductDomain productDomain : products) {
            System.out.println("------" + productDomain);
        }

        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("title", "小米");
        Iterable<ProductDomain> products2 = productDao.search(fuzzyQueryBuilder);
        for (ProductDomain productDomain : products2) {
            System.out.println("========" + productDomain);
        }
    }

    @Test
    public void termQueryByPage(){
        int currentPage = 0;
        int pageSize = 5;
        PageRequest pageRequest = PageRequest.of(currentPage,pageSize);
        TermsQueryBuilder queryBuilder = QueryBuilders.termsQuery("category", "小米");

        Page<ProductDomain> products = productDao.search(queryBuilder, pageRequest);
        for (ProductDomain productDomain : products) {
            System.out.println("========" + productDomain);
        }
    }


}
