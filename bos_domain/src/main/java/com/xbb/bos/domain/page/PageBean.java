package com.xbb.bos.domain.page;

import com.xbb.bos.domain.take_delivery.Promotion;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

/**
 * 宣传信息分页查询的pageBean类
 * Created by xbb on 2018/1/6.
 */
@XmlRootElement(name = "pageBean")
@XmlSeeAlso({Promotion.class})
public class PageBean<T> {

    private long totalCount;//总记录数
    private List<T> pageData;//当前页数据

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getPageData() {
        return pageData;
    }

    public void setPageData(List<T> pageData) {
        this.pageData = pageData;
    }
}
