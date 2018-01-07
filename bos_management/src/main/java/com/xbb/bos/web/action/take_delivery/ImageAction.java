package com.xbb.bos.web.action.take_delivery;

import com.opensymphony.xwork2.ActionContext;
import com.xbb.bos.constant.Constants;
import com.xbb.bos.web.common.BaseAction;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static javax.swing.text.html.HTML.Tag.OL;

/**
 * kindeditor图片上传的action
 * Created by xbb on 2018/1/5.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class ImageAction extends BaseAction<Object>{

    private File imgFile;
    private String imgFileFileName;
    private String imgFileContentType;

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }
    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }
    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    /**
     * kindeditor图片上传功能
     * @return
     */
    @Action(value = "image_upload",results = @Result(name = "success",type = "json"))
    public String upload() throws IOException {
        System.out.println("文件:"+imgFile);
        System.out.println("文件名:"+imgFileFileName);
        System.out.println("文件类型"+imgFileContentType);

        //根目录路径,可以指定绝对路径,比如磁盘根路径d:/xxx/upload/xxx.jpg
        //String savePath = ServletActionContext.getRequest().getRealPath("/")+"upload/";
        String savePath = "D:\\myupload\\bos";

        //根目录URL,可以指定绝对路径,比如http://www.yoursite.com/attached/
        //String saveUrl = ServletActionContext.getRequest().getContextPath()+"/upload/";
        //String saveUrl = "http://localhost:9001/upload/";
        String saveUrl = "/upload/";
        System.out.println(saveUrl);

        //生成随机图片名

        //String uuid = UUID.randomUUID().toString().replace("-","");
        String uuid = RandomStringUtils.randomNumeric(8);
        String ext = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
        String randomFileName = uuid + ext;

        //保存图片(绝对路径)
        File destFile = new File(savePath,randomFileName);
        System.out.println(destFile);
        FileUtils.copyFile(imgFile,destFile);

        //通知浏览器文件上传成功
        Map<String,Object> resault = new HashMap<String,Object>();
        resault.put("error",0);
        //返回相对路径
        resault.put("url",saveUrl+randomFileName);
        ActionContext.getContext().getValueStack().push(resault);

        return SUCCESS;
    }

    /**
     * 浏览已上传图片的方法
     * @return
     */
    @Action(value = "image_manage",results = @Result(name = "success",type = "json"))
    public String manage(){
        //根目录路径,可以指定绝对路径
        //String rootPath = ServletActionContext.getRequest().getRealPath("/")+"upload/";
        String rootPath = "D:\\myupload\\bos";

        //根目录URL,可以指定绝对路径
        //String rootUrl = ServletActionContext.getRequest().getContextPath()+"/upload/";
        //String rootUrl = "http://localhost:9001/upload/";
        String rootUrl = "/upload/";

        //遍历目录获取文件的信息
        List<Map<String,Object>> fileList = new ArrayList<Map<String, Object>>();
        //当前上传目录
        File currentPathFile = new File(rootPath);
        //图片扩展名
        String[] fileTypes = new String[]{ "gif", "jpg", "jpeg", "png", "bmp" };

        if(currentPathFile.listFiles() != null){
            for (File file:currentPathFile.listFiles()){
                Map<String, Object> hash = new HashMap<String, Object>();
                String fileName = file.getName();
                if(file.isDirectory()){
                    hash.put("is_dir",true);
                    hash.put("has_file",(file.listFiles() != null));
                    hash.put("filesize",OL);
                    hash.put("is_photo",false);
                    hash.put("filetype","");
                }else {
                    String fileExt = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
                    hash.put("is_dir",false);
                    hash.put("has_file",false);
                    hash.put("filesize",file.length());
                    hash.put("is_photo",Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype",fileExt);
                }
                hash.put("filename",fileName);
                hash.put("datetime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("moveup_dir_path", "");
        result.put("current_dir_path", rootPath);
        result.put("current_url", rootUrl);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);
        ActionContext.getContext().getValueStack().push(result);

        return SUCCESS;
    }

}
