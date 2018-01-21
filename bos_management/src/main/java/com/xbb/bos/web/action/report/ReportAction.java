package com.xbb.bos.web.action.report;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.xbb.bos.domain.take_delivery.WayBill;
import com.xbb.bos.service.take_delivery.IWayBillService;
import com.xbb.bos.utils.FileUtils;
import com.xbb.bos.web.common.BaseAction;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletOutputStream;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运单报表导出的action
 * Created by xbb on 2018/1/19.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class ReportAction extends BaseAction<WayBill> {

    @Autowired
    private IWayBillService wayBillService;

    /**
     * 导出Excel表格
     *
     * @return
     */
    @Action("report_exportXls")
    public String exportXls() throws IOException {
        //查询出满足当前条件结果数据
        List<WayBill> wayBills = wayBillService.findWayBills(model);

        //生成Excel文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet sheet = hssfWorkbook.createSheet("运单数据");

        //设置表头居中
        HSSFCellStyle style = hssfWorkbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        //设置表头
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("运单号");
        headRow.createCell(1).setCellValue("寄件人");
        headRow.createCell(2).setCellValue("寄件人电话");
        headRow.createCell(3).setCellValue("寄件人地址");
        headRow.createCell(4).setCellValue("收件人");
        headRow.createCell(5).setCellValue("收件人电话");
        headRow.createCell(6).setCellValue("收件人地址");

        //表格数据
        for (WayBill wayBill : wayBills) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(wayBill.getWayBillNum());
            dataRow.createCell(1).setCellValue(wayBill.getSendName());
            dataRow.createCell(2).setCellValue(wayBill.getSendMobile());
            dataRow.createCell(3).setCellValue(wayBill.getSendAddress());
            dataRow.createCell(4).setCellValue(wayBill.getRecName());
            dataRow.createCell(5).setCellValue(wayBill.getRecMobile());
            dataRow.createCell(6).setCellValue(wayBill.getRecAddress());
        }

        //下载导出
        //设置头信息
        ServletActionContext.getResponse().setContentType("application/vnd.ms-excel");
        String filename = "运单数据.xls";
        String agent = ServletActionContext.getRequest().getHeader("user-agent");
        filename = com.xbb.bos.utils.FileUtils.encodeDownloadFilename(filename, agent);
        ServletActionContext.getResponse().setHeader(
                "Content-Disposition", "attachment;filename=" + filename);
        ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
        hssfWorkbook.write(outputStream);
        //关闭流
        hssfWorkbook.close();
        return NONE;
    }

    /**
     * 导出PDF报表
     * @return
     */
    @Action(value = "report_exportPdf")
    public String exportPdf() throws IOException, DocumentException {
        //查询出满足当前条件结果数据
        List<WayBill> wayBills = wayBillService.findWayBills(model);

        //下载导出
        //设置头信息
        ServletActionContext.getResponse().setContentType("application/pdf");
        String filename = "运单数据.pdf";
        String agent = ServletActionContext.getRequest().getHeader("user-agent");
        filename = FileUtils.encodeDownloadFilename(filename,agent);
        ServletActionContext.getResponse().setHeader(
                "Content-Disposition","attachment;filename="+filename);

        //生成PDF文件
        Document document = new Document();
        PdfWriter.getInstance(document,ServletActionContext.getResponse().getOutputStream());
        document.open();
        //写PDF数据
        //向document生成PDF表格,设置为7列,当cell到第七列时自动换行
        Table table = new Table(7);
        table.setWidth(80);
        table.setBorder(1);
        //水平对齐
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        //垂直对齐方式
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        //设置表格字体
        BaseFont cn = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",
                false);
        Font font = new Font(cn,10,Font.NORMAL, Color.BLUE);

        //写表头
        table.addCell(buildCell("运单号",font));
        table.addCell(buildCell("寄件人",font));
        table.addCell(buildCell("寄件人电话",font));
        table.addCell(buildCell("寄件人地址",font));
        table.addCell(buildCell("收件人",font));
        table.addCell(buildCell("收件人电话",font));
        table.addCell(buildCell("收件人地址",font));

        //写数据
        for (WayBill wayBill : wayBills) {
            table.addCell(buildCell(wayBill.getWayBillNum(),font));
            table.addCell(buildCell(wayBill.getSendName(),font));
            table.addCell(buildCell(wayBill.getSendMobile(),font));
            table.addCell(buildCell(wayBill.getSendAddress(),font));
            table.addCell(buildCell(wayBill.getRecName(),font));
            table.addCell(buildCell(wayBill.getRecMobile(),font));
            table.addCell(buildCell(wayBill.getRecAddress(),font));
        }
        //将表格加入文档
        document.add(table);

        document.close();
        return NONE;
    }

    private Cell buildCell(String content,Font font) throws BadElementException {
        Phrase phrase = new Phrase(content,font);
        return new Cell(phrase);
    }


    /**
     * 结合模板导出PDF报表
     * @return
     */
    @Action(value = "report_exportJasperPdf")
    public String exportJasperPdf() throws IOException, JRException {
        //查询出满足当前条件结果数据
        List<WayBill> wayBills = wayBillService.findWayBills(model);
        //下载导出报表
        //设置头信息
        ServletActionContext.getResponse().setContentType("application/pdf");
        String filename = "运单数据.pdf";
        String agent = ServletActionContext.getRequest().getHeader("user-agent");
        filename = FileUtils.encodeDownloadFilename(filename,agent);
        ServletActionContext.getResponse().setHeader(
                "Content-Disposition","attachment;filename="+filename);

        //根据jasperReport模板,生成PDF
        //读取模板文件
        String jrxml = ServletActionContext.getServletContext().getRealPath("./jasper/waybill.jrxml");
        JasperReport report = JasperCompileManager.compileReport(jrxml);
        //设置模板数据
        //parameter变量
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("company","犇犇快运");
        //Field变量
        JasperPrint jasperPrint = JasperFillManager.fillReport(report,parameters,
                new JRBeanCollectionDataSource(wayBills));
        //生成PDF客户端
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                ServletActionContext.getResponse().getOutputStream());
        exporter.exportReport();//导出
        return NONE;
    }


}
