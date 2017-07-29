<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String target = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ws_service/BookService_WS";
String base = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("target",target); // 使用 base 标签标记请求ws地址能反转js 跨域请求问题
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
 	<script type="text/javascript" src="js/jquery-1.8.3.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$("#btn2").click(function(){
    			url = "http://localhost:8080/ws_service/BookService_WS";
    			url = "${target}";
    			data = '<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Header><crident><username>tom</username><password>cat</password></crident></soap:Header><soap:Body><ns2:getById xmlns:ns2="http://service.ws.atguigu.com/"><arg0>3</arg0></ns2:getById></soap:Body></soap:Envelope>';
				$.post(
					url,		
					data,
					function(msg){
						var txt = $(msg);
						alert(txt.find("return").text());
					},
					'xml'
				);    			
    		});
    	});
    
    	
    	function reqWebService(){
    		// 1.创建请求对象实例
    		req = getHttpRequest();
    		// 2.打开http请求
    		// url = "http://localhost:8080/ws_service/BookService_WS";
    		url = "${target}";
    		req.open("post",url);
    		// 3.添加请求头信息
    		req.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    		// 4.装配请求报文soap-message
    		var data='<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Header><crident><username>tom</username><password>cat</password></crident></soap:Header><soap:Body><ns2:getById xmlns:ns2="http://service.ws.atguigu.com/"><arg0>3</arg0></ns2:getById></soap:Body></soap:Envelope>';
    		// 5.发送请求
    		req.send(data);
    		alert("ajax req send");
    		// 6.监控请求状态,解析返回数据
    		
   		 	if (req.readyState==4 && req.status==200){
   			 	result = req.responseXML;
   			 	returnElem = result.getElementsByTagName("return")[0];
   			 	alert(returnElem.firstChild.firstChild.data);
   		 	};
    	};
    	
		//声明创建http请求方法
		function getHttpRequest(){
			var xmlhttp = null;
			if (window.XMLHttpRequest){
				// code for IE7+, Firefox, Chrome, Opera, Safari
			  	xmlhttp=new XMLHttpRequest();
			  }else{
				try{// code for IE4
					xmlhttp=new ActiveXObject("Msxml2.XMLHTTP");
				}catch(e){// code for IE6, IE5
			  		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			 	 };
			}	
			return xmlhttp;
		};	
		
    </script>
</head>
  
  <body>
  	<button id="btn1" onclick="reqWebService()">dom+js发生ajax请求ws</button><br/>
  	<button id="btn2">jquery发生ajax请求ws</button>
  </body>
</html>
