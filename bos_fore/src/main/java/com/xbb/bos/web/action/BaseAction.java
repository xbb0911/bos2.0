package com.xbb.bos.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.springframework.data.domain.Page;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * action的代码重构优化
 * Created by xbb on 2017/12/27.
 */
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

    //模型驱动
    protected T model;
    @Override
    public T getModel() {
        return model;
    }

    /**
     * 构造器,完成model实例化
     */
    public BaseAction(){
        //构造子类Action对象,获取继承父类型的泛型
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        //获取类型第一个泛型参数
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Class<T> modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        try {
            model = modelClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.out.println("模型构造失败!");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("模型构造失败!");
        }

    }

    //接收分页查询参数
    protected  int page;
    protected  int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    //将分页查询结果数据压入值栈的方法
    protected void pushPageDataToValueStack(Page<T> pageData){
        Map<String, Object> result = new HashMap<String,Object>();
        result.put("total",pageData.getTotalElements());
        result.put("rows",pageData.getContent());
        ActionContext.getContext().getValueStack().push(result);
     }

}
