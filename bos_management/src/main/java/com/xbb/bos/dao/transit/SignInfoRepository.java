package com.xbb.bos.dao.transit;

import com.xbb.bos.domain.transit.SignInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 签收录入管理的dao
 * Created by xbb on 2018/1/18.
 */
public interface SignInfoRepository extends JpaRepository<SignInfo,Integer>{
}
