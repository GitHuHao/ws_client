package com.atguigu.ws.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class WSReqServlet extends HttpServlet{

	protected static Logger logger = Logger.getLogger(WSReqServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("WSReqServlet receive request..");
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		String wsData = "<soap:Envelope xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>" +
							"<soap:Header>" +
								"<crident>" +
									"<username>"+username+"</username>" +
									"<password>"+password+"</password>" +
								"</crident>" +
							"</soap:Header>" +
							"<soap:Body>" +
								"<ns2:getById xmlns:ns2='http://service.ws.atguigu.com/'>" +
									"<arg0>3</arg0>" +
								"</ns2:getById>" +
							"</soap:Body>" +
						"</soap:Envelope>";
		
		URL url = new URL("http://localhost:8080/ws_service/BookService_WS");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-type","application/x-www-form-urlencoded");
		
		OutputStream wsSender = conn.getOutputStream();
		logger.info("WSReqServlet send ws-request..");
		wsSender.write(wsData.getBytes("utf-8"));
		
		int respCode = conn.getResponseCode();
		
		if(respCode==200){
			InputStream is = conn.getInputStream();
			System.out.println(is.available());
			logger.info("WSReqServlet receive ws-resp..");
			resp.setContentType("text/text;charset=utf-8");
			
			ServletOutputStream os = resp.getOutputStream();
			byte[] by = new byte[1024];
			int len = 0;
			while((len = is.read(by))!=-1){
				os.write(by, 0, len);
			}
			os.flush();
		}
		
		logger.info("WSReqServlet sendback ws-resp..");
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}	
}
