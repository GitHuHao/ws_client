package com.atguigu.ws.invoke;

import java.util.List;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.message.Message;
import com.atguigu.ws.interceptor.CridentAddInterceptor;
import com.atguigu.ws.service.BookService;
import com.atguigu.ws.service.BookServiceImplService;
import com.atguigu.ws.service.GetMapResponse.Return;
import com.atguigu.ws.service.GetMapResponse.Return.Entry;

public class InvokerGetBeanByNew {

	public static void main(String[] args) {
		// 创建SEI 接口 (工厂模式)
		BookServiceImplService factory = new BookServiceImplService();
		// 创建SEI 实现类
		BookService bookServiceProxy = factory.getBookServiceImplPort();
		// 获取客户端代理终端
		Client client = ClientProxy.getClient(bookServiceProxy);
		// 获取客户端终端(出/入)拦截器容器
		List<Interceptor<? extends Message>> inInterceptors = client.getInInterceptors();
		List<Interceptor<? extends Message>> outInterceptors = client.getOutInterceptors();
		// 往拦截器容器注册各自定义的日志拦截器(此注册必须在调用之前进行)
		inInterceptors.add(new LoggingInInterceptor());
		outInterceptors.add(new LoggingOutInterceptor());
		
		// 装载身份认证信息
		outInterceptors.add(new CridentAddInterceptor("tom","cat"));
		
		// 使用SEI实现类代理对象,发起远程调用
		Return mapReturn = bookServiceProxy.getMap();
		
		for(Entry entry:mapReturn.getEntry()){
			System.out.println(entry.getKey()+"-->"+entry.getValue());
		}
	}
}
