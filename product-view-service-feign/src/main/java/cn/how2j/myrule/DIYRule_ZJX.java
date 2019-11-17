package cn.how2j.myrule;

import java.util.List;
import java.util.Random;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import cn.hutool.core.thread.ThreadUtil;

public class DIYRule_ZJX extends AbstractLoadBalancerRule {
	
//	Random rand;
	
	//总共被调用的次数
	private int total = 0;
	//当前提供服务的机器号
	private int currentIndex = 0;
	
	

	
	public Server choose(ILoadBalancer lb, Object key) {
	
		if(lb == null) {
			return null;
		}
		
		Server server = null;
		
		while(server == null) {
			//判断线程是否被中断
			if(Thread.interrupted()) {
				return null;
			}
			List<Server> upList = lb.getReachableServers();
			List<Server> allList = lb.getAllServers();
			
			
			int serverCount = allList.size();
			if(serverCount == 0) {
				return null;
			}
			//随机算法
//			int index = rand.nextInt(serverCount);
//			server = upList.get(index);
			
			//自定义算法
			if(total < 5) {
				server = upList.get(currentIndex);
				total++;
			}else {
				total = 0;
				currentIndex++;
				if(currentIndex >= upList.size()) {
					currentIndex = 0;
				}
			}
			
			if(server == null) {
				Thread.yield();
				continue;
			}
			
			if(server.isAlive()) {
				return (server);
			}
			
			server = null;
			Thread.yield();
		}
		return server;
	}
	
	
	@Override
	public Server choose(Object key) {
		// TODO Auto-generated method stub
		return choose(getLoadBalancer(), key);
	}

	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {
		// TODO Auto-generated method stub

	}

}
