package com.dream.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author: huzejun
 * @Date: 2021/1/4 1:20
 */
public class JmsProduce_DLQ {

    public static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    public static final String QUEUE_NAME = "queue_cluster";


    public static void main(String[] args) throws  Exception{

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);   // 持久化  如果开启
        //       就会存入文件或数据库中
        for (int i = 1; i < 4 ; i++) {
            TextMessage textMessage = session.createTextMessage("msg--" + i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
        // session.commit();
        System.out.println("  **** 消息发送到MQ完成 ****");
    }
}
