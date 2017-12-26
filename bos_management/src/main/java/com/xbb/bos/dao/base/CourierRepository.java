package com.xbb.bos.dao.base;

import com.xbb.bos.domain.base.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 快递员dao
 * Created by xbb on 2017/12/25.
 */
public interface CourierRepository extends JpaRepository<Courier,Integer>{
}
