package com.xbb.bos.web.common;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

    }


}
