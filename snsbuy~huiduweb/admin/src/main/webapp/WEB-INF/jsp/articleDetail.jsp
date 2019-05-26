<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="viewport" value="viewport"/> 
<%@include file="common/header.jsp" %>
<style type="text/css">
      /* Custom container */
      .container {
        margin: 0 auto;
        max-width: 640px;
		min-width: 200px;
		text-size:16px;
		line-height: 28px;
		text-align: justify;
      }
</style>
<script type="text/javascript">
<!--
var url = window.location.href;
if(window  != parent){
	  parent.navigate(url);
}
//-->
</script>
<c:if test="${article != null}">
 <div class="container content-body"> 
    <h5>${article.author}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
    <c:if test="${article.publishDate != null}">
	  <fmt:formatDate value="${article.publishDate}"  pattern="yyyy-MM-dd HH:mm:ss"/>
   </c:if>  
   </h5>
  <h3 style="text-indent:0;line-height:150%">${article.title}</h3> 
  <hr style="margin: 0px 0;">
  ${article.content}
  <br/><br/>
 <div class="container" style="text-align:center">
      <table align="center" width="100%">
            <tr>
               <td> 
                  <a style="width:80%" class="btn btn-large" href="${article.url}"  target="_blank">查看原文</a> 
               </td>
            </tr>
            <tr>
               <td>  
                 <div style="height:16px"></div>
                 <a style="width:80%" class="btn btn-large btn-success" href="http://huidu.lanxijun.com"  target="_blank">下载汇读</a>
               </td>
            </tr>
        </table>
       <span style="color:gray"> 汇读 (程序员/设计师/产品经理必备的专业阅读工具)</span>
 </div>
</div>
<input type="hidden"  id="highLightMenu" value="h_index"/>    
 <script type="text/javascript">
   function loadPrettify(){
	   var head= document.getElementsByTagName('head')[0];  
	   var script= document.createElement('script');  
	   script.type= 'text/javascript';  
	   script.onreadystatechange= function () {  
	        if (this.readyState == 'complete') 
	       
	         }     
	   script.onload = function(){  
	      
	    }  
	   script.src= 'http://lanasset.qiniudn.com/js/prettify/run_prettify.js?skin=github';  
	   head.appendChild(script); 
   }
    $(document).ready(function(){
	    setTimeout("loadPrettify()", 500); //延迟800ms加载
    });
 </script> 
 <div id="scriptLazyLoader"></div>
 </c:if>
 <c:if test="${article == null}">
  <div class="container warn">
      <div class="alert alert-info">亲，文章不存在。</div>
      <script type="text/javascript">
          setTimeout( "gotoMain()", 1000);
          function gotoMain(){
        	  window.location.href = "http://huidu.lanxijun.com/";
          }
      </script>
  </div>
 </c:if>
 <div style="visibility: hidden;">
    <script src="http://s20.cnzz.com/stat.php?id=5641054&web_id=5641054&show=pic1" language="JavaScript"></script>
 </div>
<%@include file="common/footer.jsp" %>