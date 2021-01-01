package com.dream.activemq.queue.tx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author: huzejun
 * @Date: 2021/1/1 18:42
 */
public class JmsProduce_TX {

    public static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    public static final String QUEUE_NAME = "tx01";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工场,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //两个参数，第一叫事务，第二叫签收
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //6 通过使用 messageProducer 消息生产者生产3条消息发送到MQ的队列里面
        for (int i = 1; i <= 3; i++) {
            //7 创建消息,好比学生按照老师的要求写好的面试题消息
            TextMessage textMessage = session.createTextMessage("tx msg---" + i);//理解为一个字符串
            //8 通过messageProducer发送消息mq
            messageProducer.send(textMessage);
        }
        //9 关闭资源
        messageProducer.close();
        session.commit();
        session.close();
        connection.close();
        System.out.println("*****tx消息发布到MQ完成");

    }
}
