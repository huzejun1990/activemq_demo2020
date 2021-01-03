package com.dream.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author: huzejun
 * @Date: 2021/1/3 0:27
 */
public class JmsConsumer_DB {
//    private static final String ACTIVEMQ_URL = "failover:(tcp://192.168.31.60:61616,tcp://192.168.31.60:61617,tcp://192.168.31.60:61618)?randomize=false";
    private static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    private static final String QUEUE_NAME = "queue-02";

    public static void main(String[] args) throws JMSException, IOException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        while (true){
            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
            if (null != textMessage) {
                System.out.println("******消费者接收到消息： "+textMessage.getText());
            }else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();

    }
}