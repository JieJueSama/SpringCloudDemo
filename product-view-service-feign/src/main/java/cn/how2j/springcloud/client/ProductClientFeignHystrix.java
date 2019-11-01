package cn.how2j.springcloud.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.how2j.springcloud.pojo.Product;


@Component
public class ProductClientFeignHystrix implements ProductClientFeign {

	@Override
	public List<Product> listProducts() {
		// TODO Auto-generated method stub
		List<Product> result = new ArrayList<Product>();
		result.add(new Product(0, "产品微服务不可用", 0));
		return result;
	}

}
