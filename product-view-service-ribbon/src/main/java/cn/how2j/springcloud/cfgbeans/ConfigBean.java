package cn.how2j.springcloud.cfgbeans;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;

@Configuration
public class ConfigBean {

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	// 没有显示声明 默认采用轮询算法
	// 显示声明后采用显示声明的算法
	@Bean
	IRule myRule() {
		// 采用随机算法替代轮询算法
//		return new RandomRule();
		return new RetryRule();
	}

}
