package com.dream.activemq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author: huzejun
 * @Date: 2021/1/1 18:12
 */
public class JmsConsumer_Topic_Persist {
    public static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    public static final String TOPIC_NAME = "Topic-Jdbc-Persistence";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("***z4");

        //1.创建连接工场,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("dream01");
        //3.创建会话session
        //两个参数，第一叫事务，第二叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "mq_jdbc");
        connection.start();

        Message message = topicSubscriber.receive();
        while (null != message) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("******收到的持久化topic:" + textMessage.getText());
            message = topicSubscriber.receive();
        }

        session.close();
        connection.close();

        /*
        * 1 一定发先运行一次消费者，等于向MQ注册，类似我订阅了这个主题。
        * 2 然后再运行生产者发送消息，此时，
        * 3 无论消费者是否在线，都会接收到，不在线的话，下次连接的时候，会把没有收到的消息都接收下来
        * */
    }
}
