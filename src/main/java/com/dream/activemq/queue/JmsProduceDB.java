package com.dream.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Author: huzejun
 * @Date: 2021/1/3 0:27
 */
public class JmsProduceDB {

   /* private static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    private static final String ACTIVEMQ_QUEUE_NAME = "Queue-JdbcPersistence";

    public static void main(String[] args) throws JMSException
    {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(ACTIVEMQ_QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();
        for (int i = 0; i < 3; i++) {
            TextMessage textMessage = session.createTextMessage("Queue-JdbcPersistence测试消息" + i);
            messageProducer.send(textMessage);
        }
        session.commit();
        System.out.println("消息发送完成");
        messageProducer.close();
        session.close();
        connection.close();
    }
}
*/

    //  linux 上部署的activemq 的 IP 地址 + activemq 的端口号，如果用自己的需要改动
    public static final String ACTIVEMQ_URL = "tcp://192.168.31.60:61616";
    // public static final String ACTIVEMQ_URL = "nio://192.168.17.3:61608";
    public static final String QUEUE_NAME = "jdbc01";


    public static void main(String[] args) throws  Exception{

        // 1 按照给定的url创建连接工程，这个构造器采用默认的用户名密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        // 设置允许有数据丢失
        // activeMQConnectionFactory.setUseAsyncSend(true);

        // 2 通过连接工厂连接 connection  和 启动
        javax.jms.Connection connection = activeMQConnectionFactory.createConnection();
        //  启动
        connection.start();
        // 3 创建回话  session
        // 两个参数，第一个事务， 第二个签收
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        // 4 创建目的地 （两种 ： 队列/主题   这里用队列）
        Queue queue = session.createQueue(QUEUE_NAME);
        // 5 创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        // 非持久化消息 和持久化消息演示
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);   // 持久化  如果开启
        //       就会存入文件或数据库中

        // 6 通过messageProducer 生产 3 条 消息发送到消息队列中
        for (int i = 1; i < 4 ; i++) {
            // 7  创建字消息
            TextMessage textMessage = session.createTextMessage("msg--" + i);
            // 8  通过messageProducer发布消息
            messageProducer.send(textMessage);
        }
        // 9 关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        // session.commit();
        System.out.println("  **** 消息发送到MQ完成 ****");
    }
}


