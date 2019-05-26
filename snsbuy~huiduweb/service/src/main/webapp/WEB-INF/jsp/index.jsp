<%@page import="net.java.efurture.judu.web.util.*" %>
<%@page import="net.java.efurture.judu.web.constants.*" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false" %>
<html>
<head>
     <meta charset="utf-8">
</head>
<body>
<h3 align="center"><a href="http://huidu.lanxijun.com/"> redirecting </a></h3>
<%
 String ta = request.getParameter("ta");
 if((ta == null || ta.trim().length() == 0) && (!request.getRequestURI().contains("ta"))){
%>
<script type="text/javascript">
   window.location.href = "http://huidu.lanxijun.com/";
</script>
<%
 }else{
%>
<script type="text/javascript">
  window.location.href = "http://ta.lanxijun.com/d.html";
</script>
<%	 
 }
 %>
</body>
</html>
