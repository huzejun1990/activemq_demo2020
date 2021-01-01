package com.dream.activemq.queue.tx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author: huzejun
 * @Date: 2021/1/1 20:16
 */
public class JmsConsumer_TX {
    public static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    public static final String QUEUE_NAME = "tx01";

    public static void main(String[] args) throws JMSException, IOException {
//        System.out.println("***我是2号消费者");

        //1.创建连接工场,按照给定的url地址，采用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工场，获得连接connection并启动访问
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        //3.创建会话session
        //两个参数，第一叫事务，第二叫签收
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        //4.创建目的地（具体是队列还是主题topic）
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        while (true){
            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
            if (null != textMessage){
                System.out.println("****消费者接收到消息："+textMessage.getText());
                textMessage.acknowledge();
            }else {
                break;
            }
        }
        messageConsumer.close();
//        session.commit();
        session.close();
        connection.close();

    }
}
