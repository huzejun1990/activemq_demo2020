package com.dream.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author: huzejun
 * @Date: 2021/1/3 0:27
 */
public class JmsProduceDB {
    //    private static final String ACTIVEMQ_URL = "failover:(tcp://192.168.31.60:61616,tcp://192.168.31.60:61617,tcp://192.168.31.60:61618)?randomize=false";
    private static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
//    cf = new ActiveMQConnectionFactory("tcp://locahost:61616?jms.useAsyncSend=true");
    private static final String QUEUE_NAME = "queue-02";

    public static void main(String[] args) throws JMSException, IOException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
//        activeMQConnectionFactory.setUseAsyncSend(true);    //开启异步投递
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
//        MessageConsumer messageConsumer = session.createConsumer(queue);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("cluster msg---: " + i);
            messageProducer.send(textMessage);

        }
//        session.commit();
        System.out.println("消息发送完成");
        messageProducer.close();
        session.close();
        connection.close();


    }
}


