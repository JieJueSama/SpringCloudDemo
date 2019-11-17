package cn.how2j.springcloud;

import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;
import cn.how2j.myrule.MySelfRule;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
//表示用于Feign方式
@EnableFeignClients
//把信息共享给断路器监控中心
@EnableCircuitBreaker
public class ProductViewServiceFeignApplication {

	public static void main(String[] args) {
		//判断rabbitmq是否启动
		int rabbitmqPort = 5672;
		if(NetUtil.isUsableLocalPort(rabbitmqPort)) {
			System.err.printf("未在端口%d发现 rabbitmq服务，请检查rabbitmq是否启动", rabbitmqPort);
			System.exit(1);
		}
		
		
		int port = 0;
		int defaultPort = 8012;
		Future<Integer> future = ThreadUtil.execAsync(()->{
			int p = 0;
			System.out.println("请于5秒钟内输入端口号， 推荐 8012 8013 或者 8014 8015， 超过五秒将默认使用" + defaultPort);
			Scanner scanner = new Scanner(System.in);
			while(true) {
				String strPort = scanner.nextLine();
				if(!NumberUtil.isInteger(strPort)) {
					System.err.println("只能是数字");
					continue;
				}
				else {
					p = Convert.toInt(strPort);
					scanner.close();
					break;
				}
			}
			return p;
		});
		try {
			
			port = future.get(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO: handle exception
			port = defaultPort;
		}
		if(!NetUtil.isUsableLocalPort(port)) {
			System.err.printf("端口号%d被占用了，无法启动%n", port);
			System.exit(1);
		}
		
		new SpringApplicationBuilder(ProductViewServiceFeignApplication.class).properties("server.port="+port).run(args);
	}
	
//	@Bean
//	public Sampler defaultSampler() {
//		
//		//ALWAYS_SAMPLE表示持续抽样
//		return Sampler.ALWAYS_SAMPLE;
//	}
}
