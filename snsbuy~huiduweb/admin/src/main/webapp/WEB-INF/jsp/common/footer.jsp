<%@page pageEncoding="UTF-8"%>
<%@page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="footer">
  <p align="center">© 汇读 2013 
  &nbsp;&nbsp;&nbsp;<a href="${configure.serverUrl}/index.jsp" <c:if test="${configure != null}">  target="_blank" </c:if>   >产品首页</a>
  &nbsp;&nbsp;&nbsp;<a href="${configure.serverUrl}/about.jsp" <c:if test="${configure != null}">  target="_blank" </c:if> >关于汇读</a>
  </p>
</div>

<script type="text/javascript">
<!--

var highLightId = $("#highLightMenu").val();
if(!highLightId){
	highLightId = "h_index";
}
$("#" + highLightId).addClass("active");
//-->
</script>

</body>
</html>