package cn.how2j.springcloud.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.how2j.springcloud.client.ProductClientRibbon;
import cn.how2j.springcloud.pojo.Product;

@Service
public class ProductService {

	@Autowired
	ProductClientRibbon productClientRibbon;
	
	
	
	public List<Product> listProducts(){
//		for(int i = 0; i < productClientRibbon.listProducts().size(); i++) {
//			String name = productClientRibbon.listProducts().get(i).getName();
//			name = name + "/ ribbon : " + port;
//			productClientRibbon.listProducts().get(i).setName(name);
//		}
		return productClientRibbon.listProducts();
	}
}
