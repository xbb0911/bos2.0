package com.xbb.bos.service.take_delivery.impl;

import com.xbb.bos.dao.take_delivery.PromotionRepository;
import com.xbb.bos.domain.page.PageBean;
import com.xbb.bos.domain.take_delivery.Promotion;
import com.xbb.bos.service.take_delivery.IPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 宣传任务业务层实现类
 * Created by xbb on 2018/1/5.
 */
@Service
@Transactional
public class PromotionServiceImpl implements IPromotionService {

    //dao层注入
    @Autowired
    private PromotionRepository promotionRepository;

    /**
     * 宣传任务保存方法
     * @param model
     */
    @Override
    public void save(Promotion model) {
        promotionRepository.save(model);
    }

    /**
     * 宣传信息分页查询
     * @param pageable
     * @return
     */
    @Override
    public Page<Promotion> findPageDate(Pageable pageable) {
        return promotionRepository.findAll(pageable);
    }

    /**
     * 宣传信息分页查询
     * @param page
     * @param rows
     * @return
     */
    @Override
    public PageBean<Promotion> findPageData(int page, int rows) {
        //System.out.println(page+"--"+rows);
        Pageable pageable = new PageRequest(page-1,rows);
        Page<Promotion> pageData = promotionRepository.findAll(pageable);
        //封装page对象
        PageBean<Promotion> pageBean = new PageBean<Promotion>();
        pageBean.setTotalCount(pageData.getTotalElements());
        pageBean.setPageData(pageData.getContent());

        return pageBean;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public Promotion findById(Integer id) {
        System.out.println(id);
        return promotionRepository.findOne(id);
    }

    /**
     * 定时设置宣传任务状态
     * @param date
     */
    @Override
    public void updateStatus(Date date) {
        promotionRepository.updateStatus(date);
    }

}
