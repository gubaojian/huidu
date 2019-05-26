<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/sellerNav.jsp" %>
 <div class="container content-body">
 <div align="right">
 <a href="${configure.serverUrl}/me/articleDetail.html?id=${article.id}&source=${!source}">
   <c:if test="${source}">
       切换到处理后
   </c:if>
   <c:if test="${!source}">
       切换到原内容
   </c:if>
</a>
&nbsp;&nbsp;&nbsp;&nbsp;
<a href="${configure.serverUrl}/articleDetail.html?id=${article.id}">
       前台文章
</a>
</div>
 <br/>
 
 
 <h4>${article.title}</h4>
 <hr/>
 ${article.content}
 
 
  <br/>
 <div align="right">
 <a href="${configure.serverUrl}/me/articleDetail.html?id=${article.id}&source=${!source}">
   <c:if test="${source}">
       切换到处理后
   </c:if>
   <c:if test="${!source}">
       切换到原内容
   </c:if>
</a>
 </div>
</div>
<input type="hidden"  id="highLightMenu" value="h_index"/>    


<c:if test="${!source}">
   <style type="text/css">
        pre{
            white-space:pre-wrap;/*css-3*/
            white-space:-moz-pre-wrap;/*Mozilla,since1999*/
            white-space:-pre-wrap;/*Opera4-6*/
            white-space:-o-pre-wrap;/*Opera7*/
            word-wrap:break-word;/*InternetExplorer5.5+*/　　
        } 
   </style>
  <script src="http://lanasset.qiniudn.com/js/prettify/run_prettify.js?skin=github"></script>
</c:if>
    
<%@include file="../common/footer.jsp" %>