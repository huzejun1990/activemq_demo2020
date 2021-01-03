package com.dream.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

/**
 * @Author: huzejun
 * @Date: 2021/1/4 0:31
 */
public class JmsProduce_DelayAndSchedule {
    private static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    private static final String ACTIVEMQ_QUEUE_NAME = "Queue-DelayAndSchedule";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
        //向上转型到ActiveMQMessageProducer
        MessageProducer messageProducer = session.createProducer(queue);
        long delay = 3 * 1000;      //晚点儿投递的时间
        long period = 4 * 1000;     //重复投递的时间间隔
        int repeat = 5;             //重复投递次数

        for (int i = 0; i < 3; i++) {
            TextMessage textMessage = session.createTextMessage("message-延时投递" + i);
            //给消息设置属性以便MQ服务器读取到这些信息,好做对应的处理
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,delay);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD,period);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT,repeat);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("******消息发送到队列完成");
    }
}

