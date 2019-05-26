<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
    <c:when test="${lang  eq 'zh'}">
        <c:set value="TA在哪" var="title"/>
        <c:set value="有你的地方就是天堂" var="app_slogan"/>
        <c:set value="随时随地与心爱的TA分享位置" var="app_slogan_desc"/>
        <c:set value="扫描二维码，下载TA在哪" var="scan_download"/>
         <c:set value="下载" var="download"/>
        <c:set value="打开TA在哪" var="open_app"/>
        <c:set value="https://itunes.apple.com/cn/app/id1118740190" var="downloadUrl"/>
    </c:when>
    <c:otherwise>
        <c:set value="Where U" var="title"/>
        <c:set value="Locate U, Wherever U" var="app_slogan"/>
        <c:set value="Share Your Location With Your Loves" var="app_slogan_desc"/>
        <c:set value="Scan QRCode, Download App" var="scan_download"/>
         <c:set value="Download" var="download"/>
        <c:set value="Open App" var="open_app"/>
        <c:set value="https://itunes.apple.com/app/id1118740190" var="downloadUrl"/>
     </c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
<link rel="icon"  href="http://lanxijun.oss-cn-shenzhen.aliyuncs.com/ta/favicon.ico" />
<link rel="shortcut icon"  href="http://lanxijun.oss-cn-shenzhen.aliyuncs.com/ta/favicon.ico" />
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="keywords" content="TA在哪 远程定位器 定位器 远程定位" />
    
<style>
    body {
        padding-top: 10px;
        padding-bottom: 60px;
        color:#333333;
        text-align:center;
    }
    .container {
        margin: 0 auto;
        max-width: 1000px;
	    	min-width: 200px;
     }
    .app-button{
        display:block;
        background:#4CAF50;
        color: #FFFFFF;
        min-width:320px;
        max-width:600px;
        padding-top: 10px;
        padding-bottom: 10px;
        text-decoration: none;
        font-size:30px;
        margin: auto;
     }
     .app-title{
        display: block;
        margin: 0;
        margin-top:10px
     }
     .app-title-desc{
        display: block;
        margin: 0;
        margin-top:10px;
        color:#888888;
     }
     .footer{
        text-align:center;
        margin-top: 30px;
     }
</style>
		<title>${title}</title>
		</head>
		<body>
		<div class="container">
		  <h1 class="app-title">${title}</h1>
		  <h4 class="app-title">${app_slogan}</h4>
		  <p  class="app-title">${app_slogan_desc}</p>
		  <p  align="center" >
		    <img  src="http://lanxijun.oss-cn-shenzhen.aliyuncs.com/ta/ta.png" height="240" width="240">
		    <br/>
		    <p  class="app-title-desc">${scan_download}</p>
		    <br/>
		    <c:choose>
			    <c:when test="${hiddenDownload}">
			    </c:when>
			    <c:otherwise>
			         <a  class="app-button" href="${downloadUrl}" target="_blank">${download}</a>
			     </c:otherwise>
			</c:choose>
		  </p>
		</div>
		<c:if test="${appUrl != null and fn:length(appUrl) > 0}">
		<div id="downloadApp" class="app-button" onclick="goApp();">${open_app}</div>
			<script type="text/javascript">
			 var ta = {};
			 if(ta.os == undefined){
			     ta.os = ta.os || {};
			    var ua = window.navigator.userAgent;
			    var matched;   
			   if(!!ua.match(/Safari/) && (matched = ua.match(/Android[\s\/]([\d\.]+)/))) {
			        ta.os = {
			            version: matched[1]
			        }
			        ta.os.name = 'Android';
			        ta.os.isAndroid = true;
			    } else if((matched = ua.match(/(iPhone|iPad|iPod)/))) {
			        ta.os.name = 'iOS';
			        ta.os.isIOS = true;
			    } else {
			         ta.os.name = 'PC';
			         ta.os.isPC = true;
			    }
			 } 
			var iframe;
			ta.goUrl = function goUrl(url) {
			        if (!iframe) {
			            iframe = document.createElement('iframe');
			            iframe.id = 'gourl_iframe_' + Date.now();
			            iframe.frameborder = '0';
			            iframe.style.cssText = 'display:none;border:0;width:0;height:0;';
			            document.body.appendChild(iframe);
			        }
			        iframe.src = url;
			  }
			  ta.goUrl("${appUrl}");
			  function goApp(){location.href='${appUrl}';}
			  if(ta.os.isPC){
				  document.getElementById("downloadApp").style.visibility="hidden";
			  }else{
				  goApp();
			  }
			</script>
			<br/>
		</c:if>
		
		
		<div class="footer">
			<h3>意见反馈</h3>
	        <h4>邮件地址：787277208@qq.com</h4>
		</div>
		</body>
</html>