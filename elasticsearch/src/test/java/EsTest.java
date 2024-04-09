import com.alibaba.fastjson.JSON;
import com.deng.study.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2021/8/7 16:11
 */
@Slf4j
public class EsTest {

    private final static String INDEX = "es_test";

    @Test
    public void test() throws Exception{
        RestHighLevelClient client = createRestHighLevelClient();
        client.close();
    }

    private RestHighLevelClient createRestHighLevelClient(){
        HttpHost httpHost = new HttpHost("localhost",9200,"http");// 不传http，默认也是
        RestClientBuilder clientBuilder = RestClient.builder(httpHost);

        return new RestHighLevelClient(clientBuilder);
    }

    @Test
    public void createIndex() throws Exception{
        RestHighLevelClient client = createRestHighLevelClient();

        CreateIndexRequest request = new CreateIndexRequest(INDEX);
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

        boolean acknowledged = response.isAcknowledged();
        log.info("acknowledged:{}",acknowledged);
    }

    @Test
    public void getIndex() throws IOException {
        RestHighLevelClient client = createRestHighLevelClient();
        GetIndexRequest request = new GetIndexRequest(INDEX);
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);

        Map<String, List<AliasMetadata>> aliases = response.getAliases();
        Map<String, MappingMetadata> mappings = response.getMappings();
        Map<String, Settings> settings = response.getSettings();

        log.info("aliases:{}",aliases);
        log.info("mappings:{}",mappings);
        log.info("settings:{}",settings);
    }

    @Test
    public void deleteIndex() throws IOException {
        RestHighLevelClient client = createRestHighLevelClient();
        DeleteIndexRequest request = new DeleteIndexRequest(INDEX);
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);

        boolean acknowledged = response.isAcknowledged();

        log.info("acknowledged:{}",acknowledged);
    }

    @Test
    public void createDoc() throws IOException {
        RestHighLevelClient client = createRestHighLevelClient();

        // 创建索引，并复制id
        IndexRequest request = new IndexRequest();
        request.index(INDEX).id("1012");

        // 准备数据
        User user1 = new User();
        user1.setName("lisi6666");
        user1.setAge(19);
        user1.setPersonId(109);
        String userJson = JSON.toJSONString(user1);

        // 往es中添加数据
        request.source(userJson, XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = response.getResult();
        log.info("result:{}",result);
    }

    @Test
    public void updateDoc() throws IOException {
        RestHighLevelClient client = createRestHighLevelClient();

        UpdateRequest request = new UpdateRequest();
        request.index(INDEX).id("1001");
        request.doc(XContentType.JSON,"name","wangwu");

        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        DocWriteResponse.Result result = response.getResult();
        log.info("result:{}",result);
    }

    @Test
    public void queryDocById() throws IOException {
        RestHighLevelClient client = createRestHighLevelClient();
        GetRequest request = new GetRequest();
        request.index(INDEX).id("1002");

        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        String sourceAsString = response.getSourceAsString();
        log.info("result:{}",sourceAsString);
    }

    @Test
    public void deleteDoc() throws IOException {
        RestHighLevelClient client = createRestHighLevelClient();
        DeleteRequest request = new DeleteRequest();
        request.index(INDEX).id("1002");

        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        log.info("result:{}",response.toString());
    }

    /**
     * 批量添加
     * @throws IOException
     */
    @Test
    public void batchInsertDoc() throws IOException {
        RestHighLevelClient client = createRestHighLevelClient();

        BulkRequest request = new BulkRequest();

        User user1 = new User();
        user1.setName("lisi");
        user1.setAge(15);
        user1.setPersonId(105);
        String user1Json = JSON.toJSONString(user1);
        IndexRequest source1 = new IndexRequest().index(INDEX).id("1007").source(user1Json,XContentType.JSON);

        User user2 = new User();
        user2.setName("tianba");
        user2.setAge(25);
        user2.setPersonId(205);
        String user2Json = JSON.toJSONString(user2);
        IndexRequest source2 = new IndexRequest().index(INDEX).id("1008").source(user2Json,XContentType.JSON);

        User user3 = new User();
        user3.setName("liujiu");
        user3.setAge(35);
        user3.setPersonId(305);
        String user3Json = JSON.toJSONString(user3);
        IndexRequest source3 = new IndexRequest().index(INDEX).id("1009").source(user3Json,XContentType.JSON);

        request.add(source1);
        request.add(source2);
        request.add(source3);

        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);

        TimeValue took = response.getTook();
        BulkItemResponse[] items = response.getItems();
        log.info("took:{}",took);
        log.info("items:{}",items);
    }

    /**
     * 批量删除
     * @throws IOException
     */
    @Test
    public void batchDeleteDoc() throws IOException {
        RestHighLevelClient client = createRestHighLevelClient();

        BulkRequest request = new BulkRequest();

        DeleteRequest deleteRequest1 = new DeleteRequest().index(INDEX).id("1004");
        DeleteRequest deleteRequest2 = new DeleteRequest().index(INDEX).id("1005");

        request.add(deleteRequest1).add(deleteRequest2);

        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        TimeValue took = response.getTook();
        BulkItemResponse[] items = response.getItems();
        log.info("took:{}",took);
        log.info("items:{}",items);

    }

    @Test
    public void search() throws IOException {
        RestHighLevelClient client = createRestHighLevelClient();

        SearchRequest request = new SearchRequest();
        request.indices(INDEX);

        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 全量查询
//        MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();

        // 精准查询
//        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("name", "wangwu");

        // 组合查询
//        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        // 组合查询-两个条件必须满足
//        queryBuilder.must(QueryBuilders.matchQuery("age",11));
//        queryBuilder.must(QueryBuilders.matchQuery("name","lisi"));

        // 组合查询-必须不是
//        queryBuilder.mustNot(QueryBuilders.matchQuery("age",11));

        // 组合查询-两个条件满足任何一个
//        queryBuilder.should(QueryBuilders.matchQuery("age",11));
//        queryBuilder.should(QueryBuilders.matchQuery("age",12));

        // 组合查询-范围查询
//        RangeQueryBuilder queryBuilder = QueryBuilders.rangeQuery("age");
//        queryBuilder.gt(10);
//        queryBuilder.lt(20);

        // 模糊查询  Fuzziness差几个字符以内匹配成功
//        FuzzyQueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("name", "lisi").fuzziness(Fuzziness.AUTO);

        // 高亮查询 TermsQueryBuilder 和 TermQueryBuilder 的区别
//        TermsQueryBuilder queryBuilder = QueryBuilders.termsQuery("name", "lisi");
//        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("name", "lisi");
//
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.preTags("<font color='red'>");
//        highlightBuilder.postTags("</font>");
//        highlightBuilder.field("name");
//
//        builder.highlighter(highlightBuilder);

//        builder.query(queryBuilder);

        // 聚合查询-最大值
//        MaxAggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");

        // 分组查询
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("avgGroup").field("age");

        builder.aggregation(aggregationBuilder);

        // 分页查询 （当前页面-1）* 每页显示的条数
//        builder.from(2);
//        builder.size(2);

        // 排除 & 包含
        String[] excludes = {};        // 排除某字段
        String[] includes = {};        // 只显示该字段
//        String[] includes = {"age"}; // 只显示该字段
        builder.fetchSource(includes,excludes);

        // 排序
        builder.sort("age", SortOrder.DESC);

        request.source(builder);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        TimeValue took = response.getTook();
        log.info("took:{}",took); // 耗时

        SearchHits hits = response.getHits();
        TotalHits totalHits = hits.getTotalHits();
        log.info("totalHits:{}",totalHits); // 总记录数

        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }

        System.out.println("聚合数据显示：");


        Aggregations aggregations = response.getAggregations();
        Terms terms = response.getAggregations().get("avgGroup");
        for (Terms.Bucket b : terms.getBuckets()) {
            System.out.println(b.getKey() + "：" + b.getDocCount());
        }

        System.out.println("aggregations:" + aggregations.get("avgGroup"));
    }
}
