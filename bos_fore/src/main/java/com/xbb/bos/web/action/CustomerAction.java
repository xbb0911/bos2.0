package com.xbb.bos.web.action;

import cn.itcast.crm.domain.Customer;
import com.xbb.bos.constant.Constants;
import com.xbb.bos.utils.MailUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * 用户注册登录的action
 * Created by xbb on 2018/1/3.
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class CustomerAction extends BaseAction<Customer>{

    //注入jsmTemplate注入Customer_sendSms
    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 短信验证码发送
     * @return
     */
    @Action(value = "customer_sendSms")
    public String sendSMs() throws UnsupportedEncodingException {
        //手机号保存在Customer对象
        //生成短信验证码
        String randomCode = RandomStringUtils.randomNumeric(4);
        //将短信验证码保存到session中
        ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(),randomCode);
        //控制台模拟短信发送
        System.out.println("手机验证码:"+randomCode);
        //编辑短信内容
        final String msg = "尊敬的用户您好,本次获取的验证码为:"+randomCode+",服务电话:4006184000";

        //调用SMS服务发送短信
        //String result = SmsUtils.sendSmsByHTTP(model.getTelephone(),msg);
        //模拟短息发送
        /*String result = "000/xxxx";
        if(result.startsWith("000")){
            //发送成功
            return NONE;
        }else{
            //发送失败
            throw new RuntimeException("短信发送失败,信息码:"+result);
        }*/

        //调用MQ服务,发送一条消息
        jmsTemplate.send("bos_sms", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("telephone",model.getTelephone());
                mapMessage.setString("msg",msg);
                return mapMessage;
            }
        });
        return NONE;
    }

    //属性驱动
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    //注入redis模板
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 用户注册
     * @return
     */
    @Action(value = "customer_regist",results = {
            @Result(name = "success",type = "redirect",location = "signup-success.html"),
            @Result(name = "input",type = "redirect",location = "signup.html")})
    public String regist(){
        //先校验短信验证码,如果不通过,返回注册页面
        //从session中获取短信验证码
        String checkcodeSession = (String) ServletActionContext.getRequest().getSession()
                .getAttribute(model.getTelephone());
        if(checkcodeSession == null || !checkcodeSession.equals(checkcode)){
            //短信验证码错误
            return INPUT;
        }
        //调用webService连接CRM,保存客户信息
        WebClient.create("http://localhost:9002/crm_management/services"
                + "/customerService/customer").type(MediaType.APPLICATION_JSON)
                .post(model);

        System.out.println("客户注册成功");

        //发送激活邮件
        //生成激活码
        String activecode = RandomStringUtils.randomNumeric(32);
        //将激活码保存到redis,设置有效时长为24小时
        redisTemplate.opsForValue().set(model.getTelephone(),activecode,24, TimeUnit.HOURS);
        //调用MailUtils发送激活邮件
        final String content = "尊敬的客户您好,请于24小时之内,进行邮箱账号绑定,点击下面地址完成绑定:" +
                "<br/><a href='"+ MailUtils.activeUrl+"?telephone="+model.getTelephone()+"&activecode="+activecode+"'>邮箱绑定地址</a>";

        //调用MailUtils发送短息
        //MailUtils.sendMail("快递账号激活邮件",content,model.getEmail());

        //调用MQ服务,发送激活邮件
        jmsTemplate.send("bos_mail", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("email",model.getEmail());
                mapMessage.setString("content",content);
                return mapMessage;
            }
        });

        return SUCCESS;
    }


    //属性驱动
    private String activecode;

    public void setActivecode(String activecode) {
        this.activecode = activecode;
    }

    /**
     * 邮箱激活
     * @return
     */
    @Action(value = "customer_activeMail")
    public String activeMail() throws IOException {
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        //判断激活码是否有效
        String activecodeRedis = redisTemplate.opsForValue().get(model.getTelephone());
        if(activecodeRedis == null || !activecodeRedis.equals(activecode)){
            //激活失败
            ServletActionContext.getResponse().getWriter()
                    .println("激活无效,请登录系统,重新绑定邮箱");
        }else{
            //激活码有效,防止重复绑定,调用CRM webService,查询客户信息,判断是否已经绑定
            Customer customer = WebClient
                    .create("http://localhost:9002/crm_management/services"
                            + "/customerService/customer/telephone/"+model.getTelephone())
                            .accept(MediaType.APPLICATION_JSON).get(Customer.class);
            if(customer.getType() == null || customer.getType() != 1){
                //邮箱未绑定,进行激活绑定
                WebClient.create("http://localhost:9002/crm_management/services"
                        + "/customerService/customer/updatetype/"+model.getTelephone()).get();
                ServletActionContext.getResponse().getWriter().println("邮箱绑定成功");
            }else {
                //已经激活绑定成功
                ServletActionContext.getResponse().getWriter().println("邮箱已经绑定,不能重复操作");
            }
            //删除redis的激活码
            redisTemplate.delete(model.getTelephone());
        }
        return NONE;
    }

    /**
     * 用户登录
     * @return
     */
    @Action(value = "customer_login",results ={
        @Result(name = "login",location = "login.html",type = "redirect"),
        @Result(name = "success",location = "index.html#/myhome",type = "redirect")})
    public String login(){
        Customer customer = WebClient
                .create(Constants.CRM_MANAGEMENT_URL+"/services/customerService/customer/login?telephone="+model.getTelephone()
                        +"&password="+model.getPassword()).accept(MediaType.APPLICATION_JSON).get(Customer.class);
        if(customer == null){
            //登录失败
            return LOGIN;
        }else {
            //登录成功
            ServletActionContext.getRequest().getSession().setAttribute("customer",customer);
            return SUCCESS;
        }

    }

}
