<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/sellerNav.jsp" %>



 <div class="container content-body">
       <c:if test="${message != null}">
           <div class="alert alert-info">${message}</div>
        </c:if>
        <c:if test="${laterRedirect != null}">
	        <script type="text/javascript">
	           setTimeout("countDown()",1000);   
	           function countDown(){
	        	   window.location.href = "${configure.serverUrl}/${laterRedirect}.html";
	           }
	         </script>
        </c:if>
</div>





<%@include file="../common/footer.jsp" %>