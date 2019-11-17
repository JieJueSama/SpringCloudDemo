package cn.how2j.springcloud;

import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RetryRule;
import com.netflix.loadbalancer.RoundRobinRule;

import cn.how2j.myrule.MySelfRule;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;

@SpringBootApplication
@EnableEurekaClient
//表示用于发现eureka注册中心的微服务
@EnableDiscoveryClient
//在启动该微服务的时候能去加载自定义的的Ribbon配置类
@RibbonClient(name="PRODUCT-DATA-SERVICE", configuration=MySelfRule.class)
public class ProductViewServiceRibbonApplication {
	
	public static void main(String[] args) {
		int port = 0;
		int defaultPort = 8010;
		Future<Integer> future = ThreadUtil.execAsync(()->{
			int p = 0;
			System.out.println("请于5秒钟内输入端口号， 推荐使用8010  超过5秒后默认使用 " + defaultPort);
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
		}catch(Exception e) {
			port = defaultPort;
		}
		if(!NetUtil.isUsableLocalPort(port)) {
			System.err.printf("端口%d被占用，无法启动%n", port);
			System.exit(1);
		}
		new SpringApplicationBuilder(ProductViewServiceRibbonApplication.class).properties("server.port="+port).run(args);
	}
	
	
	


}
