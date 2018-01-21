package com.xbb.bos.service.transit;

import com.xbb.bos.domain.transit.InOutStorageInfo;

/**
 * 出入库信息管理的service接口
 * Created by xbb on 2018/1/17.
 */
public interface InOutStorageInfoService {

    /**
     * 出入库信息添加
     * @param transitInfoId
     * @param model
     */
    void save(String transitInfoId, InOutStorageInfo model);
}
