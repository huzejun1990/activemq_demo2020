package com.dream.activemq.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.TextMessage;

/**
 * @Author: huzejun
 * @Date: 2021/1/1 22:04
 */
@Service
public class SpringMQ_Producer {

    @Autowired
    private JmsTemplate jmsTemplate;



    public static void main(String[] args) {

//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        SpringMQ_Producer producer = (SpringMQ_Producer) ctx.getBean("springMQ_Producer");

        producer.jmsTemplate.send((session) -> {
            TextMessage textMessage = session.createTextMessage("****spring和ActiveMQ的整合case1111");
            return textMessage;
        });

        System.out.println("*******send task over");

    }
}
