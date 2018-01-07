package com.xbb.bos.mq;

import com.xbb.bos.utils.SmsUtils;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.UnsupportedEncodingException;

/**
 * 短信发送消费者
 * Created by xbb on 2018/1/5.
 */
@Service("smsConsumer")
public class SmsConsumer implements MessageListener{

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        //调用SMS服务发送短信
        try {
            /*String result =
                    SmsUtils.sendSmsByHTTP(mapMessage.getString("telephone"),
                            mapMessage.getString("msg"));*/
            //控制台模拟发送
            String result = "000/XXX";
            if(result.startsWith("000")){
                //发送成功
                System.out.println("发送短息成功,手机号:"+mapMessage.getString("telephone")
                        +",验证码:"+mapMessage.getString("msg"));
            }else {
                //发送失败
                throw new RuntimeException("短信发送失败,信息码:"+result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
