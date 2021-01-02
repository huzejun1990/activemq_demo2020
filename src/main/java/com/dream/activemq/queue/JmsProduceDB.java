package com.dream.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author: huzejun
 * @Date: 2021/1/3 0:27
 */
public class JmsProduceDB {
    private static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    private static final String QUEUE_NAME = "jdbc02";

    public static void main(String[] args) throws JMSException, IOException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
//        MessageConsumer messageConsumer = session.createConsumer(queue);
        MessageProducer messageProducer = session.createProducer(queue);
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("jdbc msg---: " + i);
            messageProducer.send(textMessage);

        }
//        session.commit();
        System.out.println("消息发送完成");
        messageProducer.close();
        session.close();
        connection.close();


    }
}


