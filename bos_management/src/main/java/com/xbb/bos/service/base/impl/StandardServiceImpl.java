package com.xbb.bos.service.base.impl;

import com.xbb.bos.dao.base.StandardRepository;
import com.xbb.bos.domain.base.Standard;
import com.xbb.bos.service.base.IStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by xbb on 2017/12/23.
 */
@Service
@Transactional
public class StandardServiceImpl implements IStandardService {
    //注入dao
    @Autowired
    private StandardRepository standardRepository;

    @Override
    public void save(Standard standard) {
        standardRepository.save(standard);
    }
}
