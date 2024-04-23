import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.deng.study.es.EsApplication;
import com.deng.study.es.dao.ProductDao;
import com.deng.study.es.domain.ProductDomain;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.Lists;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import javax.annotation.Resource;
import java.util.*;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/9 23:14
 */
@Slf4j
@SpringBootTest(classes = EsApplication.class)
public class SpringDataEsTest {

    // 主要是为了查询
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    // 主要为了简单的CRUD
    @Resource
    private ProductDao productDao;

    @Test
    public void createIndex(){
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(ProductDomain.class);
        boolean create = indexOperations.create();
        System.out.println("创建索引:" + create);
    }

    @Test
    public void deleteIndex(){
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(ProductDomain.class);
        boolean delete = indexOperations.delete();
        System.out.println("删除索引：" + delete);

    }

    @Test
    public void save(){
        ProductDomain productDomain = new ProductDomain();
        productDomain.setId(11L);
        productDomain.setTitle("联想电脑");
        productDomain.setCategory("电脑");
        productDomain.setPrice(3999.00);
        productDomain.setImages("http://localhost:8081/image.jpg");
        productDomain.setCreateTime(new Date().getTime());
        productDomain.setUpdateTime(new Date());
        productDao.save(productDomain);

//        productDomain = new ProductDomain();
//        productDomain.setId(2L);
//        productDomain.setTitle("操作系统");
//        productDomain.setCategory("书");
//        productDomain.setPrice(99.00);
//        productDomain.setImages("http://localhost:8081/image.jpg");
//        productDomain.setCreateTime(new Date().getTime());
//        productDao.save(productDomain);
//
//
//        productDomain = new ProductDomain();
//        productDomain.setId(3L);
//        productDomain.setTitle("计算机网络");
//        productDomain.setCategory("书");
//        productDomain.setPrice(88.00);
//        productDomain.setImages("http://localhost:8081/image.jpg");
//        productDomain.setCreateTime(new Date().getTime());
//        productDao.save(productDomain);
    }

    @Test
    public void saveAll(){
        List<ProductDomain> productDomains = new ArrayList<>();
        for (int i = 4; i <= 10; i++) {
            ProductDomain productDomain = new ProductDomain();
            productDomain.setId((long) i);
            productDomain.setTitle("小米手机-"+ i);
            productDomain.setCategory("小米");
            productDomain.setPrice(1999.00 + i);
            productDomain.setImages("http://www.baidu.com");

            productDomains.add(productDomain);
        }
        productDao.saveAll(productDomains);
    }


    @Test
    public void update(){
//        for (int i = 4; i <= 10; i++) {
//            ProductDomain productDomain = productDao.findById((long)i).orElse(null);
//            if(Objects.nonNull(productDomain)){
//                productDomain.setCreateTime(new Date().getTime());
//                productDao.save(productDomain);
//            }
//        }
        ProductDomain productDomain = new ProductDomain();
        productDomain.setId(4L);
        productDomain.setTitle("苹果手机");
        productDomain.setCategory("苹果");
        productDomain.setPrice(4999.00);
        productDomain.setImages("http://localhost:8081/image.jpg");
        productDomain.setCreateTime(1713779123666L);

        productDao.save(productDomain);
    }

    @Test
    public void findById(){
        ProductDomain productDomain = productDao.findById(1L).orElse(null);
        System.out.println(productDomain);
    }

    @Test
    public void findAll(){
        Iterable<ProductDomain> products = productDao.findAll();
        for (ProductDomain productDomain : products) {
//            System.out.println(DateUtil.format(new Date(productDomain.getCreateTime()),DatePattern.NORM_DATETIME_FORMAT));
            System.out.println(productDomain);
        }
    }



    @Test
    public void findByPage(){
        // 按照id进行排序
        Sort sort = Sort.by(Sort.Direction.DESC,"id");

        int currentPage = 0; // es是从0开始搜索的
        int pageSize = 10;

        // 分页和排序，这种把分页和排序写在一起的方式不太友好
        Pageable pageRequest = PageRequest.of(currentPage,pageSize,sort);

        Page<ProductDomain> productPage = productDao.findAll(pageRequest);
        for (ProductDomain productDomain : productPage) {
            System.out.println(productDomain);
        }
    }

    /**
     * 多条件查询
     */
    @Test
    public void multipleConditionQuery(){

        // keypoint 使用BoolQueryBuilder这种方式，多条件查询，es用的7.x

        // 按照id进行排序
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort("id").order(SortOrder.DESC);

        int currentPage = 0; // es是从0开始搜索的
        int pageSize = 10;

        // 分页
        Pageable pageable = PageRequest.of(currentPage,pageSize);

        String queryDateStr = "2024-04-22";
        DateTime beginDate = DateUtil.parse(queryDateStr, DatePattern.NORM_DATE_PATTERN);
        Date endDate = DateUtils.addDays(beginDate, 1);

        // 范围查询
        RangeQueryBuilder timeQueryBuilder = QueryBuilders.rangeQuery("createTime").gte(beginDate.getTime()).lt(endDate.getTime());

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        MatchQueryBuilder titleMatchQuery = QueryBuilders.matchQuery("title", "小米手机");

        // 多个field对应一个text
//        QueryBuilders.multiMatchQuery("古都","北京","西安");

        // 如果不设置operator为AND，那么这里搜索小米手机，会把苹果手机也搜索出来，原因如下：
        // 默认取并集，也就是operator默认为OR，如果设置为AND，表示取交集
        // 这里查询[小米手机]，默认为or，那么查出的数据=查[小米]得到的 || 查[手机]得到的
        // 如果改为AND，那么查出的数据=查[小米]得到的 && 查[手机]得到的
        titleMatchQuery.operator(Operator.AND);

        queryBuilder.must(titleMatchQuery);

        // 嵌套查询 select * from product  where (title like %计算机% and category='书') or (category = '苹果')
        // todo 这里有bug
//        queryBuilder.should(
//                queryBuilder.must(QueryBuilders.matchQuery("title", "计算机"))
//                            .must(QueryBuilders.termQuery("category", "书"))
//                );
//        queryBuilder.should(QueryBuilders.termQuery("category","苹果"));

        // 嵌套查询 select * from product  where (title like %计算机% or category='书') and (category = '苹果')

        // termQuery 单个条件查询，termsQuery多条件查询
//        queryBuilder.must(QueryBuilders.termQuery("category", "小米"));
        queryBuilder.must(QueryBuilders.termsQuery("category", Lists.newArrayList("小米","苹果")));
        queryBuilder.must(timeQueryBuilder);

        SearchHits<ProductDomain> searchHits = searchProductDomain(queryBuilder, sortBuilder, pageable);
        for (SearchHit<ProductDomain> searchHit : searchHits.getSearchHits()) {
            ProductDomain productDomain = searchHit.getContent();

            List<String> category = searchHit.getHighlightField("category");
            StringBuilder highText = new StringBuilder();
            for (String s : category) {
                highText = new StringBuilder(highText.append(s));
            }
            productDomain.setCategory(highText.toString());

            System.out.println(productDomain);
        }

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category","小米");
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().
                                        withQuery(termQueryBuilder).build();

        searchHits = elasticsearchRestTemplate.search(searchQuery, ProductDomain.class);
        for (SearchHit<ProductDomain> searchHit : searchHits.getSearchHits()) {
            ProductDomain productDomain = searchHit.getContent();
            System.out.println("------" + productDomain);
        }

        // todo fuzzyQuery 和 wildcardQuery 还是不知道怎么使用
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("title", "大小米");
        searchQuery = new NativeSearchQueryBuilder().withQuery(fuzzyQueryBuilder).build();
        searchHits = elasticsearchRestTemplate.search(searchQuery, ProductDomain.class);
        for (SearchHit<ProductDomain> searchHit : searchHits.getSearchHits()) {
            ProductDomain productDomain = searchHit.getContent();
            System.out.println("========" + productDomain);
        }

        WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("title", "小米*");
        searchQuery = new NativeSearchQueryBuilder().withQuery(wildcardQueryBuilder).build();
        searchHits = elasticsearchRestTemplate.search(searchQuery, ProductDomain.class);
        for (SearchHit<ProductDomain> searchHit : searchHits.getSearchHits()) {
            ProductDomain productDomain = searchHit.getContent();
            System.out.println("**********" + productDomain);
        }
    }

    /**
     * 多条件查询
     * @param queryBuilder 搜索条件
     * @param sortBuilder  排序条件
     * @param pageable     分页条件
     * @return
     */
    private SearchHits<ProductDomain> searchProductDomain(QueryBuilder queryBuilder, FieldSortBuilder sortBuilder, Pageable pageable){
        // 获取总的记录数、分页数据
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(queryBuilder);
        if(Objects.nonNull(sortBuilder)){
            searchQueryBuilder.withSort(sortBuilder);
        }
        if(Objects.nonNull(pageable)){
            searchQueryBuilder.withPageable(pageable);
        }

        NativeSearchQuery searchQuery = searchQueryBuilder.build();

        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("category");
        highlightBuilder.requireFieldMatch(false); // 多个高亮关闭
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");

        searchQuery.setHighlightQuery(new HighlightQuery(highlightBuilder));


        return elasticsearchRestTemplate.search(searchQuery,ProductDomain.class);
    }

}
