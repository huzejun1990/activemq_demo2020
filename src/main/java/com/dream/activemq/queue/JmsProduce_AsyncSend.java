package com.dream.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: huzejun
 * @Date: 2021/1/3 23:46
 */
public class JmsProduce_AsyncSend {

    private static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    private static final String QUEUE_NAME = "queue_Async;";

    public static void main(String[] args) throws JMSException, IOException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        activeMQConnectionFactory.setUseAsyncSend(true);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) session.createProducer(queue);
        TextMessage message = null;
        for (int i = 1; i <= 3; i++) {
            message = session.createTextMessage("message---: " + i);
            message.setJMSMessageID(UUID.randomUUID().toString()+"---orderDream");
            String msgID = message.getJMSMessageID();
            activeMQMessageProducer.send(message, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(msgID+" has been success send");
                }

                @Override
                public void onException(JMSException exception) {
                    System.out.println(msgID+" fail to send to mq");
                }
            });
        }
        activeMQMessageProducer.close();
        session.close();
        connection.close();
        System.out.println("******消息发送到队列完成");


    }
}

