package com.dream.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author: huzejun
 * @Date: 2021/1/1 18:09
 */
public class JmsProduce_Topic_Persist {

    public static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    public static final String TOPIC_NAME = "Topic-Persist";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer messageProducer = session.createProducer(topic);

        connection.start();
        for (int i = 1; i <= 3; i++) {
            //7 创建消息,好比学生按照老师的要求写好的面试题消息
            TextMessage textMessage = session.createTextMessage("msg-persist---" + i);//理解为一个字符串
            //8 通过messageProducer发送消息
            messageProducer.send(textMessage);

        }
        //9 关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("*****TOPIC_NAME 消息发布到MQ完成");


    }
}
