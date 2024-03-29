package cn.how2j.springcloud.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.how2j.springcloud.pojo.Product;
import cn.how2j.springcloud.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	ProductService productService;

	@Value("${version}")
	String version;

	// 把端口号放进产品信息里，这个数据服务做集群，可以分辨这个数据从不同微服务获取
	@Value("${server.port}")
	String port;

	@RequestMapping("/products")
	public Object products(Model m) {

		List<Product> ps = productService.listProducts();
		m.addAttribute("version", version);
		m.addAttribute("ps", ps);
		m.addAttribute("port", port);
		return "products";
	}
}
