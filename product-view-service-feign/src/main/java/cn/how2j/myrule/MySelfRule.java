package cn.how2j.myrule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@Configuration
public class MySelfRule {
	
	
	@Bean
	public IRule ribbonRule() {
	
//		return new RandomRule();
		return new DIYRule_ZJX();//自定义每台机器服务5次
	}

}
