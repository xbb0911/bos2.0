package com.xbb.bos.service.base.impl;

import com.opensymphony.xwork2.ModelDriven;
import com.xbb.bos.dao.base.CourierRepository;
import com.xbb.bos.domain.base.Courier;
import com.xbb.bos.service.base.ICourierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 快递员service实现类
 * Created by xbb on 2017/12/25.
 */
@Service
@Transactional
public class CourierServiceImpl implements ICourierService,ModelDriven<Courier>{

    private Courier courier = new Courier();
    @Override
    public Courier getModel() {
        return courier;
    }

    //注入Dao对象
    @Autowired
    private CourierRepository courierRepository;

    /**
     * 添加快递员
     * @param courier
     */
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);
    }

    /**
     * 分页查询
     * @param specification
     * @param pageable
     * @return
     */
    @Override
    public Page<Courier> findPageData(Specification<Courier> specification,Pageable pageable) {
        return courierRepository.findAll(specification,pageable);
    }

    /**
     * 批量作废快递员
     * @param idArray
     */
    @Override
    public void delBatch(String[] idArray) {
        //调用dao实现update修改操作,将deltag修改为1
        for (String idStr : idArray){
            Integer id = Integer.parseInt(idStr);
            courierRepository.updateDeltag(id);
        }
    }

    /**
     * 批量还原快递员
     * @param idArray
     */
    @Override
    public void restore(String[] idArray) {
        //调用dao完成还原操作
        for (String str : idArray){
            Integer id = Integer.parseInt(str);
            courierRepository.updateRestore(id);
        }
    }

    /**
     * 查询未关联定区的快递员
     * @return
     */
    @Override
    public List<Courier> findNoAssociation() {
        //封装specification
        Specification<Courier> specification = new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
               //查询条件,判定列表size为空
                Predicate predicate = cb.isEmpty(root.get("fixedAreas").as(Set.class));
                return predicate;
            }
        };
        return courierRepository.findAll(specification);
    }

}
