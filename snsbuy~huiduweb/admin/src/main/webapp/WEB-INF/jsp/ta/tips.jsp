<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
    <c:when test="${lang  eq 'zh'}">
        <c:set value="TA在哪" var="title"/>
        <c:set value="您的链接已过期，请重新发起远程定位" var="time_out_tips"/>
        <c:set value="下载TA在哪" var="download_app"/>
        <c:set value="© 2015 TA在哪" var="app_copy_right"/>
    </c:when>
    <c:otherwise>
        <c:set value="Where U" var="title"/>
        <c:set value="Request Link Was Expired，Please Create New Remote GPS Link" var="time_out_tips"/>
        <c:set value="Download Where U" var="download_app"/>
        <c:set value="© 2015 Where U" var="app_copy_right"/>
     </c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
<title>${title}</title>
<link rel="icon"  href="http://lanxijun.oss-cn-shenzhen.aliyuncs.com/ta/favicon.ico" />
<link rel="shortcut icon"  href="http://lanxijun.oss-cn-shenzhen.aliyuncs.com/ta/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<style>
* {
	margin: 0 auto;
	padding: 0 auto;
}

body {
	max-width: 600px;
	min-height: 300px;
	background:#4CAF50;
    color: #FFFFFF;
}
</style>
</head>
<body>
	<div id="content" class="content-font">
	    <br/><br/>
		<h3 align="center">${time_out_tips}</h3>
		<br/><br/>
		<h3 align="center">	
		 <a  style="color:#FFFFFF" href="http://ta.lanxijun.com/d.html?lang=${lang}">${download_app}</a>
		 <br/> <br/><br/><br/><br/> <br/><br/>  <br/><br/> <br/><br/>  <br/><br/>  <br/>
         <p  align="center"  style="margin:0;margin-top:10px;font-size:12px;">${app_copy_right}</p>
	</h3>
		<script type="text/javascript">
		 function goHome(){
			  window.location.replace("http://ta.lanxijun.com/d.html?lang=${lang}");
	     }
		 setTimeout("goHome()", 900);
	</script>
	</div>
</body>
</html>