package com.dmf.boot.elasticsearch.query;

import com.dmf.boot.elasticsearch.base.ElasticsearchClientBase;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.junit.Test;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 *  术语查询：通常用于结构化的数据，如数字、日期和枚举，而不是全文字段
 * @author dmf
 * @date 2018/7/24
 */
public class TermLevelQueries extends ElasticsearchClientBase {

    @Test
    public void testTermQuery() throws Exception {
        QueryBuilder qb = termQuery("age", "36");
        twitterPrepareSearch(qb);
    }

    @Test
    public void testTermsQuery() throws Exception {
        QueryBuilder qb = termsQuery("age", "36","37");
        twitterPrepareSearch(qb);
    }

    @Test
    public void testRangeQuery() throws Exception{
//        gte() :范围查询将匹配字段值大于或等于此参数值的文档。
//        gt() :范围查询将匹配字段值大于此参数值的文档。
//        lte() :范围查询将匹配字段值小于或等于此参数值的文档。
//        lt() :范围查询将匹配字段值小于此参数值的文档。
//        from() 开始值 to() 结束值 这两个函数与includeLower()和includeUpper()函数配套使用。
//        includeLower(true) 表示 from() 查询将匹配字段值大于或等于此参数值的文档。
//        includeLower(false) 表示 from() 查询将匹配字段值大于此参数值的文档。
//        includeUpper(true) 表示 to() 查询将匹配字段值小于或等于此参数值的文档。
//        includeUpper(false) 表示 to() 查询将匹配字段值小于此参数值的文档。

        /*RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
        rangeQuery.from(36).to(40).includeLower(true).includeUpper(true);*/

        //===>等价于
        RangeQueryBuilder queryBuilder = QueryBuilders.rangeQuery("age").gte(36).lte(40);
        twitterPrepareSearch(queryBuilder);
    }

    @Test
    public void testExistsQuery() throws Exception {
        QueryBuilder qb = existsQuery("email");
        twitterPrepareSearch(qb);
    }

    /**
     * 查找指定字段包含以指定的精确前缀开头的值的文档。
     *
     * @throws Exception
     */
    @Test
    public void testPrefixQuery() throws Exception {
        QueryBuilder qb = prefixQuery("email", "ga");
        twitterPrepareSearch(qb);
    }

    /**
     * 通配符查询
     * @throws Exception
     */
    @Test
    public void testWildcardQuery() throws Exception {
        QueryBuilder qb = wildcardQuery("firstName", "L?ra*");
        twitterPrepareSearch(qb);
    }

    /**
     * 正则表达式查询
     * @throws Exception
     */
    @Test
    public void testRegexpQuery() throws Exception {
        QueryBuilder qb = regexpQuery("email", "c.*");
        twitterPrepareSearch(qb);
    }

    @Test
    public void testFuzzyQuery() throws Exception {
        QueryBuilder qb = fuzzyQuery("address", "Street").fuzziness(Fuzziness.TWO);
        twitterPrepareSearch(qb);
    }
}
