<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
    <script type="text/javascript">
    
    	function reqWebService(){
    		alert("ajax be trigged...");
    		// 1.创建请求对象实例
    		req = getHttpRequest();
    		// 2.打开http请求
    		req.open("post","http://pc201608140721:8080/ws_service/BookService_WS");
    		// 3.添加请求头信息
    		req.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    		// 4.装配请求报文soap-message
    		var data='<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"><soap:Header><crident><username>tom</username><password>cat</password></crident></soap:Header><soap:Body><ns2:getById xmlns:ns2="http://service.ws.atguigu.com/"><arg0>3</arg0></ns2:getById></soap:Body></soap:Envelope>';
    		// 5.发送请求
    		req.send(data);
    		alert("ajax req send");
    		// 6.监控请求状态,解析返回数据
    		
   		 	if (req.readyState==4 && req.status==200){
   			 	alert("recv resp");
   			 	result = req.responseXML;
   			 	returnElem = result.getElementsByTagName("return")[0];
   			 	alert("rec Id: "+returnElem.firstChild.firstChild.data);
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
  	<button onclick="reqWebService()">发生ajax请求ws</button>
  </body>
</html>
