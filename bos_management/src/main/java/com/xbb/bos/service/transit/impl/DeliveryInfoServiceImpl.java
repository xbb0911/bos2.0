package com.xbb.bos.service.transit.impl;

import com.xbb.bos.dao.transit.DeliveryInfoRepository;
import com.xbb.bos.dao.transit.TransitInfoRepository;
import com.xbb.bos.domain.transit.DeliveryInfo;
import com.xbb.bos.domain.transit.TransitInfo;
import com.xbb.bos.service.transit.IDeliveryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 配送信息管理的service实现类
 * Created by xbb on 2018/1/18.
 */
@Service
@Transactional
public class DeliveryInfoServiceImpl implements IDeliveryInfoService {

    @Autowired
    private DeliveryInfoRepository deliveryInfoRepository;

    @Autowired
    private TransitInfoRepository transitInfoRepository;

    /**
     * 添加配送信息
     * @param transitInfoId
     * @param deliveryInfo
     */
    @Override
    public void save(String transitInfoId, DeliveryInfo deliveryInfo) {
        //保存开始配送信息
        deliveryInfoRepository.save(deliveryInfo);
        //查询运输配送对象
        TransitInfo transitInfo = transitInfoRepository.findOne(Integer.parseInt(transitInfoId));
        transitInfo.setDeliveryInfo(deliveryInfo);
        //更改状态
        transitInfo.setStatus("开始配送");
    }
}
