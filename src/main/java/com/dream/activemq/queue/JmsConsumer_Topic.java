package com.dream.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author: huzejun
 * @Date: 2021/1/1 12:46
 */
public class JmsConsumer_Topic {
    public static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    public static final String TOPIC_NAME = "topic-dream";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("***我是3号消费者");

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
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(topic);


        //通过监听的方式来消费消息
        messageConsumer.setMessageListener((message) -> {
            if (null != message && message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("****消费者接收Topic到消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();

        /*
         * 1 先生产，    只启动1号消费者，问题：1号消费者还能消费消息吗？
         *   YES
         *
         * 2 先生产，    先启动1号消费者，再启动2号消费者，问题：2号消费者还能消费消息吗？
         *   2.1.1 1号可以消费        Y
         *   2.1.2 2号可以消费吗？      N
         *
         *  3 先启动两个消费者，再生产，请问，消费情况如何
         *  3.1 2个消费者都有6条
         *  3.2 先到先得，6条全给一个
         *  3.3 一人一半     Y
         * */
    }
}
