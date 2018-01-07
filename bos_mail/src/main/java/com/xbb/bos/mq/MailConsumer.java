package com.xbb.bos.mq;

import com.xbb.bos.utils.MailUtils;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 激活邮件发送消费者
 * Created by xbb on 2018/1/5.
 */
@Service("mailConsumer")
public class MailConsumer implements MessageListener{
    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        //调用MailUtils发送短息
        try {
            MailUtils.sendMail("快递账号激活邮件",mapMessage.getString("content"),mapMessage.getString("email"));
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
