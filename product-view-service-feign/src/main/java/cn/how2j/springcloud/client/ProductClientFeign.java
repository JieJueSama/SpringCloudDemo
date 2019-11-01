package cn.how2j.springcloud.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import cn.how2j.springcloud.pojo.Product;


//Feign客户端  通过注解方式  访问  PRODUCT-DATA-SERVICE服务的products路径
//product-data-service既不是域名也不是ip地址  而是数据服务在eureka注册中心的名称
//这里只是指定了要访问的微服务名称  并没有指定端口号8001   或者    8002
//@FeignClient(value = "PRODUCT-DATA-SERVICE")
//支持断路器之后注解
@FeignClient(value = "PRODUCT-DATA-SERVICE",fallback = ProductClientFeignHystrix.class)
public interface ProductClientFeign {
	
	
	@GetMapping("/products")
	public List<Product> listProducts();

}
