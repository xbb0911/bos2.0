package com.xbb.bos.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.xbb.bos.domain.base.Area;
import com.xbb.bos.service.base.IAreaService;
import com.xbb.bos.utils.PinYin4jUtils;
import com.xbb.bos.web.common.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
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
import javax.servlet.ServletOutputStream;
import java.io.*;
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
public class AreaAction extends BaseAction<Area>{

    //模型驱动
   /* private Area area = new Area();
    @Override
    public Area getModel() {
        return area;
    }*/

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
     * 添加区域数据
     */
    @Action(value = "area_save",results = @Result(name = "success",location = "./pages/base/area.html",type = "redirect"))
    public String save(){
        areaService.save(model);

        return SUCCESS;
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

    /**
     * 数据导出
     */
    @Action(value = "area_export")
    public String export(){
        /*//导出测试
        List<Area> areas = new ArrayList<Area>();
        for(int i=0; i<10000; i++){
            areas.add(new Area("id"+i,"陕西"+i,"延安"+i,"子长"+i,"717311"));
        }*/

        //从数据库中读取出所有的数据
        List<Area> areas = areaService.findAll();

        //将数据写入到excel文件中
        //1.创建workbook对象
        Workbook workbook = new HSSFWorkbook();
        //每个sheet显示条数
        int pageSize = 50;
        int size = areas.size();
        //总sheet数
        int count = ((size%pageSize)==0)?(size/pageSize):(size/pageSize+1);
        for(int i=0; i<count; i++){
            //2.创建sheet
            Sheet sheet = workbook.createSheet("t"+(i+1));
            //3.创建row和表头行
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("区域编号");
            row.createCell(1).setCellValue("省份");
            row.createCell(2).setCellValue("城市");
            row.createCell(3).setCellValue("区域");
            row.createCell(4).setCellValue("邮编");
            //4.遍历集合,创建数据行
            for(int j = i*pageSize; j<(i+1)*pageSize; j++){
                if(j >=  size ){
                    break;
                }
                Row row1 = sheet.createRow(sheet.getLastRowNum()+1);
                row1.createCell(0).setCellValue(areas.get(j).getId());
                row1.createCell(1).setCellValue(areas.get(j).getProvince());
                row1.createCell(2).setCellValue(areas.get(j).getCity());
                row1.createCell(3).setCellValue(areas.get(j).getDistrict());
                row1.createCell(4).setCellValue(areas.get(j).getPostcode());
            }
        }

        try {
            //5.将excel文件写到客户端,提供文件下载操作
            //文件下载操作,一个流,两个头
            String filename = "qysj.xls";
            //输出流
            ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
            //头一:content-type
            ServletActionContext.getResponse().setContentType(ServletActionContext.getServletContext().getMimeType(filename));
            //头二:content-disposition
            ServletActionContext.getResponse().setHeader("content-disposition","attachment;filename="+filename);
            //写出数据
            workbook.write(outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return NONE;
    }

    //属性注入
    /*private int page;
    private int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }*/

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
                if(StringUtils.isNotBlank(model.getProvince())){
                    //根据省份模糊查询
                    Predicate p1 = cb.like(root.get("province").as(String.class),"%"+model.getProvince()+"%");
                    list.add(p1);
                }
                if(StringUtils.isNotBlank(model.getCity())){
                    //根据城市模糊查询
                    Predicate p2 = cb.like(root.get("city").as(String.class),"%"+model.getCity()+"%");
                    list.add(p2);
                }
                if(StringUtils.isNotBlank(model.getDistrict())){
                    //根据区域模糊查询
                    Predicate p3 = cb.like(root.get("district").as(String.class),"%"+model.getDistrict()+"%");
                    list.add(p3);
                }

                return cb.and(list.toArray(new Predicate[0]));
            }
        };
        //调用业务层进行查询
        Page<Area> pageData = areaService.findPageData(specification,pageable);

       /* //根据查询结果,封装datagrid需要数据格式
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("total",pageData.getTotalElements());
        result.put("rows",pageData.getContent());

        //压入值栈
        ActionContext.getContext().getValueStack().push(result);*/

       //调用父类方法完成查询结果的压栈操作
        pushPageDataToValueStack(pageData);

        return SUCCESS;
    }


    /**
     * 查询所有区域
     * @return
     */
    @Action(value = "area_findAll",results = @Result(name = "success",type = "json"))
    public String findAll(){
        //调用业务层查询
        List<Area> areas = areaService.findAll();
        //压入栈顶
        ActionContext.getContext().getValueStack().push(areas);
        return SUCCESS;
    }


    /**
     * 查询所有的省份
     * @return
     */
    @Action(value = "area_findProvince",results = @Result(name = "success",type = "json"))
    public String findProvince(){
        //查询数据
        List<String> provinces = areaService.findProvince();
        //将查询到的省份信息封装到List<Area>集合中,并转化为json数据返回客户端
        List<Area> areas = new ArrayList<Area>();
        for (String province : provinces) {
            Area area = new Area();
            area.setProvince(province);
            areas.add(area);
        }
        //压栈
        ActionContext.getContext().getValueStack().push(areas);
        return SUCCESS;
    }

    /**
     * 根据省份信息查询所有的城市
     * @return
     */
    @Action(value = "area_findCity",results = @Result(name = "success",type = "json"))
    public String findCity(){
        //接收get请求传递的参数
        String province = model.getProvince();
        //System.out.println(province);//åäº¬å¸
        try {
            //get请求中文乱码处理
            province = new String(province.getBytes("ISO-8859-1"),"UTF-8");
           // System.out.println(province);//河北省
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //查询数据
        List<String> citys = areaService.findCity(province);
        List<Area> areas = new ArrayList<Area>();

        //封装城市数据到areas中
        for (String city : citys) {
            Area area = new Area();
            area.setCity(city);
            areas.add(area);
        }

        //压栈
        ActionContext.getContext().getValueStack().push(areas);
        return SUCCESS;
    }

    /**
     * 根据省份和城市信息查询区域信息
     * @return
     */
    @Action(value = "area_findDistrict",results = @Result(name = "success",type = "json"))
    public String findDistrict(){
        //接收参数
        String province = model.getProvince();
        String city = model.getCity();
        //处理中文乱码问题
        try {
            //get请求中文乱码处理
            province = new String(province.getBytes("ISO-8859-1"),"UTF-8");
            city = new String(city.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //System.out.println(province+"--"+city);
        //查询数据
        //List<String> districts = areaService.findDistrict(province,city);
        //List<Area> areas = new ArrayList<Area>();
        List<Area> areas = areaService.findDistrict(province,city);
        System.out.println(areas.get(0));

        //封装数据
        /*for (String district : districts) {
            Area area = new Area();
            area.setDistrict(district);
            areas.add(area);
        }*/



        //压栈
        ActionContext.getContext().getValueStack().push(areas);
        return SUCCESS;
    }

}
