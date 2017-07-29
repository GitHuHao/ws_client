package com.atguigu.ws.invoke;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.atguigu.ws.service.BookService;
import com.atguigu.ws.service.GetMapResponse.Return;
import com.atguigu.ws.service.GetMapResponse.Return.Entry;

public class InvokerGetBeanFromIoC {

	public static void main(String[] args) {
		ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
		// 创建SEI 实现类
		BookService bookServiceProxy = (BookService)ioc.getBean("bookService");
		// 使用SEI实现类代理对象,发起远程调用
		Return mapReturn = bookServiceProxy.getMap();
		
		for(Entry entry:mapReturn.getEntry()){
			System.out.println(entry.getKey()+"-->"+entry.getValue());
		}
	}
}
