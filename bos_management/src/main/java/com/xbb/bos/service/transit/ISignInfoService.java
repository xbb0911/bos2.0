package com.xbb.bos.service.transit;

import com.xbb.bos.domain.transit.SignInfo;

/**
 * 签收录入管理的service接口
 * Created by xbb on 2018/1/18.
 */
public interface ISignInfoService {

    /**
     * 签收录入
     * @param transitInfoId
     * @param model
     */
    void save(String transitInfoId, SignInfo model);
}
