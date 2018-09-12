package cn.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class RunAppConsumerUser {
	@Bean		//注入模板对象
	@LoadBalanced  //实现负载均衡
	public RestTemplate restTemplate(){
		return new RestTemplate();	//创建模板对象实例
	}

	public static void main(String[] args) {
		SpringApplication.run(RunAppConsumerUser.class, args);
	}

}
