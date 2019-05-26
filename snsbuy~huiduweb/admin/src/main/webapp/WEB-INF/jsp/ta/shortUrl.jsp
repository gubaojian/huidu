<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
    <c:when test="${lang  eq 'zh'}">
        <c:set value="TA在哪" var="title"/>
        <c:set value="您的请求已过期" var="time_out_tips"/>
        <c:set value="下载TA在哪" var="download_app"/>
        <c:set value="处理中" var="process_tips"/>
    </c:when>
    <c:otherwise>
        <c:set value="Where U" var="title"/>
        <c:set value="Link was expired" var="time_out_tips"/>
        <c:set value="Download Where U" var="download_app"/>
        <c:set value="Redirecting" var="process_tips"/>
     </c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
<title>${title}</title>
<link rel="icon"  href="http://lanxijun.oss-cn-shenzhen.aliyuncs.com/ta/favicon.ico" />
<link rel="shortcut icon"  href="http://lanxijun.oss-cn-shenzhen.aliyuncs.com/ta/favicon.ico"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {
	margin: 0 auto;
	padding: 0 auto;
}

body {
	background: #F9F9F9;
	max-width: 600px;
	min-height: 300px;
}
</style>
</head>
<body>

	<c:if test="${url != null and fn:length(url) > 0}">
		<div id="content" class="content-font">
			<br />
			<h3 align="center">${process_tips}</h3>
			<script type="text/javascript">
				window.location.replace("${url}");
			</script>
		</div>
	</c:if>
	<c:if test="${url == null or fn:length(url) <= 0}">
		<div id="content" class="content-font">
			<br />
			<h3 align="center">${time_out_tips}</h3>
			<br />
			<h3 align="center">
				<a href="http://ta.lanxijun.com/d.html?lang=${lang}">${download_app}</a>
			</h3>

			<script type="text/javascript">
				function goHome() {
					window.location.replace("http://ta.lanxijun.com/d.html?lang=${lang}");
				}
				setTimeout("goHome()", 800);
			</script>
		</div>

	</c:if>
</body>
</html>
