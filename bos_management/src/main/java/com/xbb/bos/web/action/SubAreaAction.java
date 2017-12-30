package com.xbb.bos.web.action;

import com.xbb.bos.domain.base.Area;
import com.xbb.bos.domain.base.FixedArea;
import com.xbb.bos.domain.base.SubArea;
import com.xbb.bos.service.base.ISubAreaService;
import com.xbb.bos.web.common.BaseAction;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import javax.persistence.criteria.*;
import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 分区管理的action
 * Created by xbb on 2017/12/28.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea>{

    //注入service
    @Autowired
    private ISubAreaService subAreaService;

    /**
     * 增加分区的方法
     * @return
     */
    @Action(value = "subArea_save",results = @Result(name = "success",type = "redirect",location = "./pages/base/sub_area.html"))
    public String save(){
        //调用业务层处理数据
        System.out.println(model.getArea().getId());
        //subAreaService.save(model);
        return SUCCESS;
    }

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
     * 批量导入分区信息的方法
     * 优化:自动判断表格中sheet页的个数来进行导入
     * @return
     */
    @Action(value = "subArea_batchImport")
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

        //创建一个集合保存文件中的数据
        List<SubArea> subAreas = new ArrayList<SubArea>();

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

            //获取行中每个单元格的值,封装到subArea中
            SubArea subArea = new SubArea();
            subArea.setId(row.getCell(0).getStringCellValue());

            //封装定区编码
            FixedArea fixedArea = new FixedArea();
            fixedArea.setId(row.getCell(1).getStringCellValue());
            subArea.setFixedArea(fixedArea);

            //封装区域编码
            Area area = new Area();
            area.setId(row.getCell(2).getStringCellValue());
            subArea.setArea(area);

            subArea.setKeyWords(row.getCell(3).getStringCellValue());
            subArea.setStartNum(row.getCell(4).getStringCellValue());
            subArea.setEndNum(row.getCell(5).getStringCellValue());
            subArea.setSingle(row.getCell(6).getStringCellValue().charAt(0));
            subArea.setAssistKeyWords(row.getCell(7).getStringCellValue());

            subAreas.add(subArea);
        }

        //调用业务层处理数据
        subAreaService.saveBatch(subAreas);

        return NONE;
    }

    /**
     * 批量导出数据
     * @return
     */
    @Action(value = "subArea_export")
    public String export(){
        System.out.println("aaa");
        //1.从数据库中读取所有数据
        List<SubArea> subAreas = subAreaService.findAll();

        //将数据写入到excel文件中
        //1.创建workbook对象
        Workbook workbook = new HSSFWorkbook();
        //每个sheet显示条数
        int pageSize = 50;
        int size = subAreas.size();
        //总sheet数
        int count = ((size%pageSize)==0)?(size/pageSize):(size/pageSize+1);
        for (int i = 0; i < count; i++) {
            //2.创建sheet
            Sheet sheet = workbook.createSheet("t"+(i+1));
            //3.创建row和表头行
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("分区编号");
            row.createCell(1).setCellValue("定区编码");
            row.createCell(2).setCellValue("区域编号");
            row.createCell(3).setCellValue("关键字");
            row.createCell(4).setCellValue("起始号");
            row.createCell(5).setCellValue("结束号");
            row.createCell(6).setCellValue("单双号");
            row.createCell(7).setCellValue("位置信息");

            //4.遍历集合,创建数据行
            for(int j = i*pageSize; j<(i+1)*pageSize; j++){
                if(j >=  size ){
                    break;
                }
                Row row1 = sheet.createRow(sheet.getLastRowNum()+1);
                row1.createCell(0).setCellValue(subAreas.get(j).getId());
                row1.createCell(1).setCellValue(subAreas.get(j).getFixedArea().getId());
                row1.createCell(2).setCellValue(subAreas.get(j).getArea().getId());
                row1.createCell(3).setCellValue(subAreas.get(j).getKeyWords());
                row1.createCell(4).setCellValue(subAreas.get(j).getStartNum());
                row1.createCell(5).setCellValue(subAreas.get(j).getEndNum());
                row1.createCell(6).setCellValue(subAreas.get(j).getSingle());
                row1.createCell(7).setCellValue(subAreas.get(j).getAssistKeyWords());
            }
        }
        try {
            //5.将excel文件写到客户端,提供文件下载操作
            //文件下载操作,一个流,两个头
            String filename = "subArea.xls";
            ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
            ServletActionContext.getResponse().setContentType(ServletActionContext.getServletContext().getMimeType(filename));
            ServletActionContext.getResponse().setHeader("content-disposition","attachment;filename="+filename);
            //写出数据
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    /**
     * 分页添加查询
     * @return
     */
    @Action(value = "subArea_pageQuery",results = @Result(name = "success",type = "json"))
    public String pageQuery(){
        //封装Pageable对象
        Pageable pageable = new PageRequest(page-1,rows);
        //添加查询条件
        //根据查询条件构造Specification条件查询对象,(类似hibernate的QBC查询)
        Specification<SubArea> specification = new Specification<SubArea>() {
            @Override
            public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //查询当前Root根对象
                List<Predicate> list = new ArrayList<Predicate>();

                //多表查询省市区
                Join<Object,Object> AreaRoot = root.join("area",JoinType.INNER);
                if(model.getArea() != null &&
                        StringUtils.isNotBlank(model.getArea().getProvince())){
                    //进行省份模糊查找
                    Predicate p1 = cb.like(AreaRoot.get("province").as(String.class),
                            "%"+model.getArea().getProvince()+"%");
                    list.add(p1);
                }
                if(model.getArea() != null &&
                        StringUtils.isNotBlank(model.getArea().getCity())){
                    //进行省份模糊查找
                    Predicate p2= cb.like(AreaRoot.get("city").as(String.class),
                            "%"+model.getArea().getCity()+"%");
                    list.add(p2);
                }
                if(model.getArea() != null &&
                        StringUtils.isNotBlank(model.getArea().getDistrict())){
                    //进行省份模糊查找
                    Predicate p3 = cb.like(AreaRoot.get("district").as(String.class),
                            "%"+model.getArea().getDistrict()+"%");
                    list.add(p3);
                }

                //定区编码等值查询
                Join<Object,Object> fixedAreaRoot = root.join("fixedArea",JoinType.INNER);
                if(model.getFixedArea() != null &&
                        StringUtils.isNotBlank(model.getFixedArea().getId())){
                    //进行省份模糊查找
                    Predicate p4 = cb.equal(fixedAreaRoot.get("id").as(String.class),
                            model.getFixedArea().getId());
                    list.add(p4);
                }

                //单表查询
                if(StringUtils.isNotBlank(model.getKeyWords())){
                    Predicate p3 = cb.like(root.get("keyWords").as(String.class),model.getKeyWords());
                    list.add(p3);
                }
                return cb.and(list.toArray(new Predicate[0]));
            }
        };
        //调用业务层,返回page
        Page<SubArea> pageData = subAreaService.findPageData(specification,pageable);
        //将查询结果插入到值栈中
        pushPageDataToValueStack(pageData);

        return SUCCESS;
    }

}
