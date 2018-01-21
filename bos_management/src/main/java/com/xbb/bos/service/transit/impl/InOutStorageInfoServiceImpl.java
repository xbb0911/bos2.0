package com.xbb.bos.service.transit.impl;

import com.xbb.bos.dao.transit.InOutStorageInfoRepository;
import com.xbb.bos.dao.transit.TransitInfoRepository;
import com.xbb.bos.domain.transit.InOutStorageInfo;
import com.xbb.bos.domain.transit.TransitInfo;
import com.xbb.bos.service.transit.InOutStorageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 出入库信息管理的service实现类
 * Created by xbb on 2018/1/17.
 */
@Service
@Transactional
public class InOutStorageInfoServiceImpl implements InOutStorageInfoService {

    @Autowired
    private InOutStorageInfoRepository inOutStorageInfoRepository;

    @Autowired
    private TransitInfoRepository transitInfoRepository;

    /**
     * 出入库信息添加
     * @param transitInfoId
     * @param inOutStorageInfo
     */
    @Override
    public void save(String transitInfoId, InOutStorageInfo inOutStorageInfo) {
        //保存出入库信息
        inOutStorageInfoRepository.save(inOutStorageInfo);
        //查询TransitInfo
        TransitInfo transitInfo = transitInfoRepository.findOne(Integer.parseInt(transitInfoId));
        //关联出入库信息到运输配送对象
        transitInfo.getInOutStorageInfos().add(inOutStorageInfo);
        //修改状态
        if(inOutStorageInfo.getOperation().equals("到达网点")){
            transitInfo.setStatus("到达网点");
            //更新网点地址
            transitInfo.setOutletAddress(inOutStorageInfo.getAddress());
        }
    }
}
