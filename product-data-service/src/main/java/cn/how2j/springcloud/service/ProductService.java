package cn.how2j.springcloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.how2j.springcloud.pojo.Product;

@Service
public class ProductService {
	
	
	//把端口号放进产品信息里，这个数据服务做集群，可以分辨这个数据从不同微服务获取
	@Value("${server.port}")
	String port;
	
		
	public List<Product> listPorducts(){
		List<Product> ps = new ArrayList<>();
		ps.add(new Product(1, "product a from port:"+port, 50));
		ps.add(new Product(2, "product b from port:"+port, 150));
		ps.add(new Product(3, "product c from port:"+port, 250));
		return ps;
	}
}
