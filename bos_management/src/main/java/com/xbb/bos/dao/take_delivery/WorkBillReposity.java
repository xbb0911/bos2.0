package com.xbb.bos.dao.take_delivery;

import com.xbb.bos.domain.take_delivery.WorkBill;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 工单管理的dao
 * Created by xbb on 2018/1/9.
 */
public interface WorkBillReposity extends JpaRepository<WorkBill,Integer>{
}
