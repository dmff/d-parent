//package com.demo.es.client;
//
//import com.alibaba.fastjson.JSON;
//import com.github.pagehelper.Page;
//import com.google.common.collect.Lists;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequestBuilder;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.sort.FieldSortBuilder;
//import org.elasticsearch.search.sort.SortBuilder;
//import org.elasticsearch.search.sort.SortBuilders;
//import org.elasticsearch.search.sort.SortOrder;
//import org.springframework.util.CollectionUtils;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author dmf
// * @date 2018/9/26
// */
//public abstract class EsBaseSearch<S extends EsBaseSearch<?, ?>, T> {
//
//    private static final int MAX_HITS = 10000;
//    private static final int MAX_PAGE_SIZE = 50;
//    private static final int MIN_PAGE_SIZE = 100;
//    protected String[] excludes = null;
//    protected String[] includes = null;
//    protected int pageNum = 1;
//    protected int pageSize = 10;
//
//    /**
//     * 排序器
//     */
//    protected List<SortBuilder> sortBuilders = Lists.newArrayList();
//
//    /**
//     * 搜索器
//     */
//    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//
//    /**
//     * 转换对象
//     * @param hitFields
//     * @return
//     * @throws Exception
//     */
//    protected abstract T convert2Record(SearchHit hitFields) throws Exception;
//
//    /**
//     * 设置索引
//     * @return
//     */
//    protected abstract String getIndex();
//
//    /**
//     * 设置类型
//     * @return
//     */
//    protected abstract String getElasticType();
//
//    /**
//     * 设置id
//     * @return
//     */
//    protected abstract String getId();
//
//    public boolean save(T t) throws Exception {
//        String json = JSON.toJSONString(t);
//        IndexResponse indexResponse = ElasticsearchClientUtils.getClient()
//                .prepareIndex(getIndex(), getElasticType(), getId())
//                .setSource(json, XContentType.JSON)
//                .execute()
//                .actionGet();
//        return true;
//    }
//
//    public boolean delete(String id) throws Exception {
//        DeleteResponse deleteResponse = ElasticsearchClientUtils.getClient()
//                .prepareDelete(getIndex(), getElasticType(), id)
//                .execute()
//                .actionGet();
//        return true;
//    }
//
//
//    protected void desc(String... field) {
//        Arrays.stream(field).forEach(f -> {
//            FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(f).order(SortOrder.DESC);
//            sortBuilders.add(fieldSortBuilder);
//        });
//    }
//
//    protected void asc(String... field) {
//        Arrays.stream(field).forEach(f -> {
//            FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(f).order(SortOrder.ASC);
//            sortBuilders.add(fieldSortBuilder);
//        });
//    }
//
//
//    protected void withSort(SearchRequestBuilder searchRequestBuilder) {
//        if (!CollectionUtils.isEmpty(sortBuilders)) {
//            sortBuilders.forEach(sortBuilder -> searchRequestBuilder.addSort(sortBuilder));
//        }
//    }
//
//    public S selectProperties(String... properties) {
//        this.includes = properties;
//        return (S) this;
//    }
//
//    public S page(int pageNum, int pageSize) {
//        this.pageNum = pageNum;
//        this.pageSize = pageSize;
//        return (S) this;
//    }
//
//    protected int from(Integer pageNum, Integer pageSize) {
//        //超过10000不支持
//        int from = (pageNum - 1) * pageSize;
//        if (from + pageSize >= MAX_HITS) {
//            from = MAX_HITS - pageSize;
//        }
//
//        return from;
//    }
//
//    protected int pageNum(int pageNum) throws Exception {
//        if (pageNum <= 0) {
//            throw new Exception("页码不可以小于1");
//        }
//        return pageNum;
//    }
//
//    protected int pageSize(int pageSize) throws Exception {
//        if (pageSize < MIN_PAGE_SIZE) {
//            pageSize = 10;
//        }
//        if (pageSize > MAX_PAGE_SIZE) {
//            throw new Exception("每页返回数据不得超过50行。");
//        }
//        return pageSize;
//    }
//
//    protected int countPages(int pageSize, long totalHits) {
//        try {
//            long pages = totalHits / pageSize + 1;
//            return (int) pages;
//        } catch (Exception e) {
//            return 0;
//        }
//    }
//
//
//    public List<T> query() throws Exception {
//        pageNum = pageNum(pageNum);
//        pageSize = pageSize(pageSize);
//        int from = from(pageNum, pageSize);
//        SearchRequestBuilder searchRequestBuilder = ElasticsearchClientUtils.getClient()
//                .prepareSearch(getIndex())
//                .setTypes(getElasticType())
//                .setQuery(queryBuilder)
//                .setFetchSource(includes, excludes)
//                .setFrom(from)
//                .setSize(pageSize);
//
//        withSort(searchRequestBuilder);
//        SearchResponse response = searchRequestBuilder.execute().actionGet();
//        SearchHits hits = response.getHits();
//        Page<T> page = new Page<>();
//        if (hits == null) {
//            return page;
//        }
//
//        for (SearchHit hit : hits.getHits()) {
//            try {
//                page.add(convert2Record(hit));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        page.setPageNum(pageNum);
//        page.setPageSize(pageSize);
//        long totalHits = hits.getTotalHits();
//        if (totalHits > MAX_HITS) {
//            totalHits = MAX_HITS;
//        }
//        page.setTotal(totalHits);
//        page.setPages(countPages(pageSize, totalHits));
//        return page;
//    }
//
//}
