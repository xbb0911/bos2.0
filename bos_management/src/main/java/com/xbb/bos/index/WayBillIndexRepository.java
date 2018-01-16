package com.xbb.bos.index;

import com.xbb.bos.domain.take_delivery.WayBill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 搜索引擎的dao
 * Created by xbb on 2018/1/13.
 */
public interface WayBillIndexRepository extends ElasticsearchRepository<WayBill,Integer>{
}
