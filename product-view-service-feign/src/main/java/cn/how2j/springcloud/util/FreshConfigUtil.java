package cn.how2j.springcloud.util;

import java.util.HashMap;

import cn.hutool.http.HttpUtil;

public class FreshConfigUtil {
	
	public static void main(String[] args) {
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json;charset=utf-8");
		System.out.println("因为要去git获取，还要刷新config-server，会比较卡，所以一般需要好几秒才能完成，请耐心等待");
		//使用post方式访问，http://localhost:8012/actuator/bus-refresh不支持git方式访问
		//如果直接把这个地址放在浏览器会抛出405错误
		String result = "";
		try {
			result = HttpUtil.createPost("http://localhost:8012/actuator/bus-refresh").addHeaders(headers).execute().body();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("result:" + result);
		System.out.println("refresh 完成");
	}

}
