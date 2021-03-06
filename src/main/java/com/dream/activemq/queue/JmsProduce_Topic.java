package com.dream.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author: huzejun
 * @Date: 2021/1/1 12:37
 */
public class JmsProduce_Topic {

    public static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    public static final String TOPIC_NAME = "topic-dream";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工场,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //两个参数，第一叫事务，第二叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);
        //6 通过使用 messageProducer 消息生产者生产3条消息发送到MQ的队列里面
        for (int i = 1; i <= 3; i++) {
            //7 创建消息,好比学生按照老师的要求写好的面试题消息
            TextMessage textMessage = session.createTextMessage("TOPIC_NAME---" + i);//理解为一个字符串
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
