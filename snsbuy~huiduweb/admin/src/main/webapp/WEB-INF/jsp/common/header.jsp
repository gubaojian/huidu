<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh">
  <head>
    <link rel="icon"  href="http://lanasset.qiniudn.com/image/huidu/logo/favicon.ico" />
    <link rel="shortcut icon"  href="http://lanasset.qiniudn.com/image/huidu/logo/favicon.ico" />
    <link href="http://cdn.staticfile.org/twitter-bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://cdn.staticfile.org/twitter-bootstrap/2.3.1/css/bootstrap-responsive.min.css" rel="stylesheet">
   
     <meta charset="utf-8">
    <c:choose>
       <c:when test="${title == null}">
             <title>汇读—汇集智慧，在阅读中成长</title>
       </c:when>
       <c:otherwise> <title>${title}</title></c:otherwise>
    </c:choose>
    <c:if test="${viewport != null}">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </c:if>
    <meta name="keywords" content="汇读 汇集智慧 在阅读中成长 IT牛人 产品经理 设计师必备 专业移动阅读工具" />
    <style type="text/css">
      body {
        padding-top: 10px;
        padding-bottom: 60px;
      }

      /* Custom container */
      .container {
        margin: 0 auto;
        max-width: 1000px;
		min-width: 200px;
      }
     
     

      /* Supporting marketing content */
      .marketing {
         margin: 40px 0;
      }
      .marketing p + h4 {
        margin-top: 28px;
      }
      
	  .navbar{
		  width:1000px;
	  }
	  table{
		    font-size:16px;
     }
	 input{
		 height:30px;font-size:18px;
	 }
	 .content-body{
		  min-height:500px;
	  }
	</style>
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://cdn.staticfile.org/html5shiv/3.6.1/html5shiv.min.js"></script>
    <![endif]-->
    <script src="http://cdn.staticfile.org/jquery/1.9.1/jquery.min.js"></script>
    <script src="http://cdn.staticfile.org/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
    
  </head>
  <body>