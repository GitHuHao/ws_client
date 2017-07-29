package com.atguigu.ws.interceptor;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.utils.DOMHelper;

public class CridentAddInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

	private String username;
	private String password;
	
	protected static Logger logger = Logger.getLogger(CridentAddInterceptor.class);
	
	/* 子类继承父类,默认创建对象时,调用父类空擦构造器,而 AbstractLoggingInterceptor未提供空参构造器.因此
	 * 需要子类显示创建构造器并在首行显示调用父类的带参构造器
	 */
	public CridentAddInterceptor(String username,String password) {
		super(Phase.PRE_PROTOCOL); // 默认设置预协议化时,创建拦截器
		this.username = username;
		this.password = password;
		logger.info("ws-client CridentAddInterceptor created ... ");
	}
	
	/*
	 * <Envelop>
	 *     <head>
	 * 			<crident>
	 * 				<username>tom</username>
	 * 				<password>cat</password>
	 * 			</crident>
	 * 	   </head>
	 *     <soap:body>
	 *     		<ns2:getMap xmlns:ns2="http://127.0.0.1:8080/getMap"/>
	 *     </soap:body>
	 * </Envelop>
	 * 在上述soap消息基础上添加 
	 *     <head>
	 * 			<crident>
	 * 				<username>tom</username>
	 * 				<password>cat</password>
	 * 			</crident>
	 * 	   </head>
	 * 
	 * */
	public void handleMessage(SoapMessage message) throws Fault {
		Document doc = DOMHelper.createDocument();
		Element cridentElem = doc.createElement("crident");
		
		Element nameElem = doc.createElement("username");
		nameElem.setTextContent(username);
		
		Element pwdElem = doc.createElement("password");
		pwdElem.setTextContent(password);
		
		cridentElem.appendChild(nameElem);
		cridentElem.appendChild(pwdElem);
		
		List<Header> headers = ((SoapMessage)message).getHeaders();
		headers.add(new Header(new QName("crident"), cridentElem));
		
		logger.info("ws-client CridentAddInterceptor done ... ");
	}
	
}
