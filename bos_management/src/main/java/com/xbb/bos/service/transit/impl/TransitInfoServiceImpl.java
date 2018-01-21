package com.xbb.bos.service.transit.impl;

import com.xbb.bos.dao.take_delivery.WayBillRepository;
import com.xbb.bos.dao.transit.TransitInfoRepository;
import com.xbb.bos.domain.take_delivery.WayBill;
import com.xbb.bos.domain.transit.TransitInfo;
import com.xbb.bos.index.WayBillIndexRepository;
import com.xbb.bos.service.transit.ITransitInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 运单中转配送管理的service实现类
 * Created by xbb on 2018/1/17.
 */
@Service
@Transactional
public class TransitInfoServiceImpl implements ITransitInfoService {

    @Autowired
    private WayBillRepository wayBillRepository;

    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;

    @Autowired
    private TransitInfoRepository transitInfoRepository;



    /**
     * 保存tansitInfo信息
     * @param wayBillIds
     */
    @Override
    public void createTransits(String wayBillIds) {
        if(StringUtils.isNotBlank(wayBillIds)){
            //处理运单
            for (String wayBillId : wayBillIds.split(",")) {
                WayBill wayBill = wayBillRepository.findOne(Integer.parseInt(wayBillId));
                //判断运单状态是否为待发货
                if(wayBill.getSignStatus() == 1){
                    //代发货
                    //生成TransitInfo信息
                    TransitInfo transitInfo = new TransitInfo();
                    transitInfo.setWayBill(wayBill);
                    transitInfo.setStatus("出入库中转");
                    transitInfoRepository.save(transitInfo);
                    //更改运单状态
                    wayBill.setSignStatus(2);

                    //同步索引库
                    wayBillIndexRepository.save(wayBill);
                }
            }
        }

    }

    /**
     * 分页查询运输配送信息
     * @param pageable
     * @return
     */
    @Override
    public Page<TransitInfo> findPageData(Pageable pageable) {
        return transitInfoRepository.findAll(pageable);
    }
}
