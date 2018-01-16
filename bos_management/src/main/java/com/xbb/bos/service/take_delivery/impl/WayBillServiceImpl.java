package com.xbb.bos.service.take_delivery.impl;

import com.xbb.bos.dao.take_delivery.WayBillRepository;
import com.xbb.bos.domain.take_delivery.WayBill;
import com.xbb.bos.index.WayBillIndexRepository;
import com.xbb.bos.service.take_delivery.IWayBillService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 运单管理的service实现类
 * Created by xbb on 2018/1/10.
 */
@Service
@Transactional
public class WayBillServiceImpl implements IWayBillService {

    @Autowired
    private WayBillRepository wayBillRepository;

    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;

    /**
     * 运单保存
     * @param wayBill
     */
    @Override
    public void save(WayBill wayBill) {
        //判断运单号是否存在
        WayBill persistWayBill = wayBillRepository.findByWayBillNum(wayBill.getWayBillNum());
        if(persistWayBill == null || persistWayBill.getId() == null) {
            //运单不存在
            wayBillRepository.save(wayBill);
            //保存索引
            wayBillIndexRepository.save(wayBill);
        }else{
            try {
                //运单存在
                Integer id = persistWayBill.getId();
                BeanUtils.copyProperties(persistWayBill,wayBill);
                persistWayBill.setId(id);
                //保存索引
                wayBillIndexRepository.save(persistWayBill);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    /**
     * 运单无条件排序查询
     * 基于索引的条件查询
     * @param wayBill
     * @param pageable
     * @return
     */
    @Override
    public Page<WayBill> findPageData(WayBill wayBill,Pageable pageable) {
        //判断waybill中查询条件是存在
        if(StringUtils.isBlank(wayBill.getWayBillNum())
                &&StringUtils.isBlank(wayBill.getSendAddress())
                &&StringUtils.isBlank(wayBill.getRecAddress())
                &&StringUtils.isBlank(wayBill.getSendProNum())
                &&(wayBill.getSignStatus()==null || wayBill.getSignStatus()==0)){
            //无条件查询,直接查询数据库
            return wayBillRepository.findAll(pageable);
        }else {
            //条件查询
            /*
               must     条件必须成立(and)
               must not 条件必须不成立(not)
               should   条件可以成立(or)
             */
            //布尔查询,多条件组合查询
            BoolQueryBuilder query = new BoolQueryBuilder();
            //向组合查询对象添加查询条件
            if(StringUtils.isNotBlank(wayBill.getWayBillNum())){
                //运单号查询
                QueryBuilder tempQuery = new TermQueryBuilder("wayBillNum",
                        wayBill.getWayBillNum());
                query.must(tempQuery);
            }
            if(StringUtils.isNotBlank(wayBill.getSendAddress())){
                //发货地 地址模糊查询
                //情况一:输入"西"是查询词条的一部分,使用模糊匹配词条查询
                QueryBuilder wildcardQuery = new WildcardQueryBuilder(
                        "sendAddress","*"+wayBill.getSendAddress()+"*");

                //情况二:输入"陕西省西安市"是多个词条组合,进行分词后,每个词条等值匹配查询
                QueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getSendAddress()).field("sendAddress")
                        .defaultOperator(QueryStringQueryBuilder.Operator.AND);

                //两种情况查询结果取or的关系
                BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
                boolQueryBuilder.should(wildcardQuery);
                boolQueryBuilder.should(queryStringQueryBuilder);

                query.must(boolQueryBuilder);
            }
            if(StringUtils.isNotBlank(wayBill.getRecAddress())){
                //收货地 地址模糊查询
                //情况一:输入词条的一部分查询,进行模糊查询
                QueryBuilder wildcardQuery = new WildcardQueryBuilder(
                        "recAddress","*"+wayBill.getRecAddress()+"*");

                //情况二:多个词条组合,进行分词等值查询
                QueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getRecAddress()).field("recAddress")
                        .defaultOperator(QueryStringQueryBuilder.Operator.AND);

                //两种查询结果取或(or)
                BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
                boolQueryBuilder.should(wildcardQuery);
                boolQueryBuilder.should(queryStringQueryBuilder);

                query.must(boolQueryBuilder);
            }
            if(StringUtils.isNotBlank(wayBill.getSendProNum())){
                //快递产品类型,等值查询
                QueryBuilder termQuery = new TermQueryBuilder("sendProNum",
                        wayBill.getSendProNum());
                query.must(termQuery);
            }
            if(wayBill.getSignStatus() != null && wayBill.getSignStatus() != 0){
                //签收状态查询
                QueryBuilder termQuery = new TermQueryBuilder("signStatus",
                        wayBill.getSignStatus());
                query.must(termQuery);
            }
            SearchQuery searchQuery = new NativeSearchQuery(query);
            //分页效果
            searchQuery.setPageable(pageable);
            //有条件查询,查询索引库
            return wayBillIndexRepository.search(searchQuery);
        }
    }

    /**
     * 根据运单号查询运单
     * @param wayBillNum
     * @return
     */
    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
        return wayBillRepository.findByWayBillNum(wayBillNum);
    }
}
