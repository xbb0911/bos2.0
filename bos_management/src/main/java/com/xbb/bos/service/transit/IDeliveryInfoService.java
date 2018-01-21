package com.xbb.bos.service.transit;

import com.xbb.bos.domain.transit.DeliveryInfo;

/**
 * 配送信息管理的service接口
 * Created by xbb on 2018/1/18.
 */
public interface IDeliveryInfoService {

    /**
     * 添加配送信息
     * @param transitInfoId
     * @param model
     */
    void save(String transitInfoId, DeliveryInfo model);
}
