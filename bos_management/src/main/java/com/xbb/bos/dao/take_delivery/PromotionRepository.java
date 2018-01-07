package com.xbb.bos.dao.take_delivery;

import com.xbb.bos.domain.take_delivery.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 宣传活动dao层接口
 * Created by xbb on 2018/1/5.
 */
public interface PromotionRepository extends JpaRepository<Promotion,Integer>,JpaSpecificationExecutor<Promotion>{

}
