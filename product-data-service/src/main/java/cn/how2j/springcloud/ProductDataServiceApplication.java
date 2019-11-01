package cn.how2j.springcloud;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;



//启动类  因为要做集群   所以让用户自己输入端口   8001 8002 8003


@SpringBootApplication
@EnableEurekaClient
public class ProductDataServiceApplication {
	
	public static void main(String[] args) {
		
		int port = 0;
		int defaultPort = 8001;
		//每次测试都要输入端口很麻烦   所以写个future类   如果5秒内不输入  默认使用8001端口
		Future<Integer> future = ThreadUtil.execAsync(() ->{
			int p = 0;
			System.out.println("请于5秒内输入端口号， 推荐8001、8002或者8003，超过5秒默认使用 " + defaultPort);
			Scanner scanner = new Scanner(System.in);
			while(true) {
				String strPort = scanner.nextLine();
				if(!NumberUtil.isInteger(strPort)) {
					System.err.println("只能是数字");
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
			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO: handle exception
			port = defaultPort;
			
			
			
		}
		
		if(!NetUtil.isUsableLocalPort(port)) {
			System.err.printf("端口号%d被占用了，无法启动%n", port);
			System.exit(1);
		}
		
		new SpringApplicationBuilder(ProductDataServiceApplication.class).properties("server.port="+port).run(args);
		
		
	}
	
	@Bean
	public Sampler defaultSampler() {
		
		//ALWAYS_SAMPLE表示持续抽样
		return  Sampler.ALWAYS_SAMPLE;
	}

}
