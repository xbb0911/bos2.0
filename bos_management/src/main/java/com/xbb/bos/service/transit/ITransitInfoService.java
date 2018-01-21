package com.xbb.bos.service.transit;

import com.xbb.bos.domain.transit.TransitInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 运单中转配送管理的service接口
 * Created by xbb on 2018/1/17.
 */
public interface ITransitInfoService {

    /**
     * 保存tansitInfo信息
     * @param wayBillIds
     */
    void createTransits(String wayBillIds);

    /**
     * 分页查询运输配送信息
     * @param pageable
     * @return
     */
    Page<TransitInfo> findPageData(Pageable pageable);
}
