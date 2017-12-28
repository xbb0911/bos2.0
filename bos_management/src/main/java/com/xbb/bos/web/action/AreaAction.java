package com.xbb.bos.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xbb.bos.domain.base.Area;
import com.xbb.bos.service.base.IAreaService;
import com.xbb.bos.utils.PinYin4jUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.BinaryClient;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域管理action 省-市-区/县
 * Created by xbb on 2017/12/27.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class AreaAction extends ActionSupport implements ModelDriven<Area>{

    //模型驱动
    private Area area = new Area();
    @Override
    public Area getModel() {
        return area;
    }

    //注入业务层对象
    @Autowired
    private IAreaService areaService;

    //接收上传文件参数
    private File file;
    private String fileFileName;

    public void setFile(File file) {
        this.file = file;
    }
    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    /**
     * 批量区域数据导入
     * @return
     * @throws IOException
     */
    @Action(value = "area_batchImport")
    public String batchImport() throws IOException {
        //编写解析代码格式
        /*
            基于.xls格式解析  HSSFWorkbook对象
            基于.xlsx格式解析 XSSFWorkbook对象
        */
        //1.创建Workbook对象
        Workbook workbook = null;
        //判断上传文件的后缀名
        if(fileFileName.endsWith(".xls")){
            workbook = new HSSFWorkbook(new FileInputStream(file));
        }else if(fileFileName.endsWith(".xlsx")){
            workbook = new XSSFWorkbook(new FileInputStream(file));
        }

        List<Area> areas = new ArrayList<Area>();

        //2.获取sheet页
        Sheet sheet = workbook.getSheetAt(0);
        //3.获取row行
        for (Row row : sheet) {
            //跳过第一行
            if(row.getRowNum() == 0){
                continue;
            }

            //跳过空行
            if(row.getCell(0)==null || StringUtils.isBlank(row.getCell(0).getStringCellValue())){
                continue;
            }

            //获取每一个单元格cell,并且获取值
            Area area = new Area();
            area.setId(row.getCell(0).getStringCellValue());
            area.setProvince(row.getCell(1).getStringCellValue());
            area.setCity(row.getCell(2).getStringCellValue());
            area.setDistrict(row.getCell(3).getStringCellValue());
            area.setPostcode(row.getCell(4).getStringCellValue());

            //基于pinyin4j生成城市编码和简码
            String province = area.getProvince();
            String city = area.getCity();
            String district = area.getDistrict();
            province = province.substring(0,province.length()-1);
            city = city.substring(0,city.length()-1);
            district = district.substring(0,district.length()-1);

            //简码
            String[] headArray = PinYin4jUtils.getHeadByString(province+city+district);
            StringBuffer sb = new StringBuffer();
            for(String headStr:headArray){
                sb.append(headStr);
            }
            String shortcode = sb.toString();
            area.setShortcode(shortcode);

            //城市编码
            String citycode = PinYin4jUtils.hanziToPinyin(city,"");
            area.setCitycode(citycode);

            areas.add(area);
        }
        //调用业务层
        areaService.saveBatch(areas);
        return NONE;
    }

    //属性注入
    private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * 区域分页条件查询
     * @return
     */
    @Action(value = "area_pageQuery",results = @Result(name = "success",type = "json"))
    public String pageQuery(){
        //构造分页查询对象
        Pageable pageable = new PageRequest(page-1,rows);
        //构造条件查询对象
        Specification<Area> specification = new Specification<Area>() {
            @Override
            public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //创建保存条件集合对象
                List<Predicate> list = new ArrayList<Predicate>();
                //添加条件
                if(StringUtils.isNotBlank(area.getProvince())){
                    //根据省份模糊查询
                    Predicate p1 = cb.like(root.get("province").as(String.class),"%"+area.getProvince()+"%");
                    list.add(p1);
                }
                if(StringUtils.isNotBlank(area.getCity())){
                    //根据城市模糊查询
                    Predicate p2 = cb.like(root.get("city").as(String.class),"%"+area.getCity()+"%");
                    list.add(p2);
                }
                if(StringUtils.isNotBlank(area.getDistrict())){
                    //根据区域模糊查询
                    Predicate p3 = cb.like(root.get("district").as(String.class),"%"+area.getDistrict()+"%");
                    list.add(p3);
                }

                return cb.and(list.toArray(new Predicate[0]));
            }
        };
        //调用业务层进行查询
        Page<Area> pageData = areaService.findPageData(specification,pageable);

        //根据查询结果,封装datagrid需要数据格式
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("total",pageData.getTotalElements());
        result.put("rows",pageData.getContent());

        //压入值栈
        ActionContext.getContext().getValueStack().push(result);

        return SUCCESS;
    }

}
