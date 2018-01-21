package com.xbb.bos.service.take_delivery;

import com.xbb.bos.domain.take_delivery.WayBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 运单管理的service接口
 * Created by xbb on 2018/1/10.
 */
public interface IWayBillService {

    /**
     * 保存运单
     * @param model
     */
    void save(WayBill model);



    /**
     * 根据运单号查询数据
     * @param wayBillNum
     * @return
     */
    WayBill findByWayBillNum(String wayBillNum);

    /**
     * 运单无条件排序查询
     * 基于索引的条件查询
     * @param model
     * @param pageable
     * @return
     */
    Page<WayBill> findPageData(WayBill model, Pageable pageable);

    /**
     * 同步数据库和索引库
     */
    void syncIndex();

    /**
     * 根据条件查询wayBill
     * @param model
     * @return
     */
    List<WayBill> findWayBills(WayBill model);
}
