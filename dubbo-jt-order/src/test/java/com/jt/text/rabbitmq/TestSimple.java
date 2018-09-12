package com.jt.text.rabbitmq;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class TestSimple {
	
	//初始化连接
	private Connection connection;
	//定义队列名称
	String queueName="simple";
	
	@Before
	public void before() throws IOException{
		//连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.176.137");
		factory.setPort(5672);
		factory.setUsername("jtadmin");
		factory.setPassword("jtadmin");
		factory.setVirtualHost("/jt");
		
		connection=factory.newConnection();
	}
	
	@Test
	public void test01(){
		System.out.println("获取连接："+connection);
	}
	
	//实现消息的写入:1定义管理队列对象 2定义消息 3定义队列 4发送消息
	@Test
	public void provider() throws IOException{
		Channel channel = connection.createChannel();
		/**
		 * queue:队列名称
		 * durable:是否持久化  true 和false
		 * exclusive: 如果为true 表示为生产者独有
		 * autoDelete:当消息消费完成后是否自动删除
		 * arguments:是否传递参数 一般为空
		 */
		channel.queueDeclare(queueName, false, false, false, null);
		
		//定义发送的消息
		String msg = "我是单工模式";
		
		//发送消息
		/**
		* exchange:交换机名称   如果没有交换机 添加""串
		* routingKey:路由KEy 信息标识符,没有添加队列名称
		* props 携带的参数
		* body.传递信息的字节码文件
		*/
		channel.basicPublish("", queueName, null, msg.getBytes());
		
		channel.close();
		connection.close();
	}
	
	@Test
	public void consumer() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		//获取通道
		Channel channel = connection.createChannel();
		//定义队列
		channel.queueDeclare(queueName, false, false, false, null);
		//定义消费者对象
		QueueingConsumer consumer = new QueueingConsumer(channel);
		//将消费者与队列绑定
		channel.basicConsume(queueName, true, consumer);
		//循环获取信息
		while(true){
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			
			String msg = new String(delivery.getBody());
			
			System.out.println("获取信息:"+msg);	
		}
		
	}
	
}
