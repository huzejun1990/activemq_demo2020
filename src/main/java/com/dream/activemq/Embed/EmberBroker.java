package com.dream.activemq.Embed;

import org.apache.activemq.broker.BrokerService;

/**
 * @Author: huzejun
 * @Date: 2021/1/1 21:29
 */
public class EmberBroker {

    public static void main(String[] args) throws Exception {
        // 用ActiveMQ Broker作为独立的消息服务器来构建Java应用
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://127.0.0.1:61616");
        brokerService.start();
    }
}
