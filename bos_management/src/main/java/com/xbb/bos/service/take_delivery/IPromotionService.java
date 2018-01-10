package com.xbb.bos.service.take_delivery;

import com.xbb.bos.domain.page.PageBean;
import com.xbb.bos.domain.take_delivery.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import javax.ws.rs.*;
import java.util.Date;

/**
 * 宣传任务业务层接口
 * Created by xbb on 2018/1/5.
 */
public interface IPromotionService {

    /**
     * 宣传任务保存
     * @param model
     */
    void save(Promotion model);

    /**
     * 宣传信息分页查询
     * @param pageable
     * @return
     */
    Page<Promotion> findPageDate(Pageable pageable);

    /**
     * 宣传信息分页查询
     * @param page
     * @param rows
     * @return
     */
    @Path("/pageQuery")
    @GET
    @Produces({"application/xml","application/json"})
    PageBean<Promotion> findPageData(@QueryParam("page") int page,
                                     @QueryParam("rows") int rows);

    /**
     * 根据id查询宣传信息
     * @param id
     * @return
     */
    @Path("/promotion/{id}")
    @GET
    @Produces({"application/xml","application/json"})
    Promotion findById(@PathParam("id") Integer id);

    /**
     * 定时设置宣传任务状态
     * @param date
     */
    void updateStatus(Date date);
}
