package com.xbb.bos.dao.take_delivery;

import com.xbb.bos.domain.take_delivery.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * 宣传活动dao层接口
 * Created by xbb on 2018/1/5.
 */
public interface PromotionRepository extends JpaRepository<Promotion,Integer>,JpaSpecificationExecutor<Promotion>{

    /**
     * 定时设置宣传任务状态
     * @param date
     */
    @Query("update Promotion set status='2' where endDate<? and status='1'")
    @Modifying
    void updateStatus(Date date);
}
