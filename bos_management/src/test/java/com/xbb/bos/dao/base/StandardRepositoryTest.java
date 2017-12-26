package com.xbb.bos.dao.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring data Query 条件查询命名规则测试
 * Created by xbb on 2017/12/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class StandardRepositoryTest {

    @Autowired
    private StandardRepository standardRepository;

    //1.根据方法命名规则自动生成
    @Test
    public void testQuery(){
        //System.out.println(standardRepository.findByName("20-30公斤"));
        //System.out.println(standardRepository.findByNameLike("20-30公斤"));
        //System.out.println(standardRepository.queryName("20-30公斤"));
        //System.out.println(standardRepository.queryName2("20-30公斤"));
    }

    //使用单体测试,测试dao,要添加事务,要想数据保留,设置事务不会滚
   /* @Test
    @Transactional
    @Rollback(false)
    public void testUpdate(){
        standardRepository.updataMinLength(1,10);
    }*/

}
