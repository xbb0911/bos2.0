package com.xbb.bos.service.transit.impl;

import com.xbb.bos.dao.transit.SignInfoRepository;
import com.xbb.bos.dao.transit.TransitInfoRepository;
import com.xbb.bos.domain.transit.SignInfo;
import com.xbb.bos.domain.transit.TransitInfo;
import com.xbb.bos.index.WayBillIndexRepository;
import com.xbb.bos.service.transit.ISignInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 签收录入管理的service实现类
 * Created by xbb on 2018/1/18.
 */
@Service
@Transactional
public class SignInfoServiceImpl implements ISignInfoService {

    @Autowired
    private SignInfoRepository signInfoRepository;

    @Autowired
    private TransitInfoRepository transitInfoRepository;

    @Autowired
    private WayBillIndexRepository wayBillIndexRepository;

    /**
     * 签收录入
     * @param transitInfoId
     * @param signInfo
     */
    @Override
    public void save(String transitInfoId, SignInfo signInfo) {
        //保存签收录入数据
        signInfoRepository.save(signInfo);
        //查询运输信息
        TransitInfo transitInfo = transitInfoRepository.findOne(Integer.parseInt(transitInfoId));
        //关联运输流程
        transitInfo.setSignInfo(signInfo);

        //更改运单状态
        if(signInfo.getSignType().equals("正常")){
            //正常签收
            transitInfo.setStatus("正常签收");
            //更改运单状态
            transitInfo.getWayBill().setSignStatus(3);
            //更改索引库
            wayBillIndexRepository.save(transitInfo.getWayBill());
        }else{
            //异常
            transitInfo.setStatus("异常");
            //更改运单状态
            transitInfo.getWayBill().setSignStatus(4);
            //更改索引库
            wayBillIndexRepository.save(transitInfo.getWayBill());
        }
    }


}
