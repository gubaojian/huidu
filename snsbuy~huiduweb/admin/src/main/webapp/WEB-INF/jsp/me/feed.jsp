<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.weiwei.com/functions"  prefix="wf"%>

<%@include file="../common/header.jsp" %>
<%@include file="../common/sellerNav.jsp" %>

 <div class="container content-body">
     
      <c:if test="${message != null}">
           <div class="alert alert-info">${message}</div>
      </c:if>
     
     
      <c:if test="${feed.id == null}">
          <h4> 添加Feed </h4>
      </c:if>
      <c:if test="${feed.id != null}">
          <h4> 编辑Feed </h4>
      </c:if>
      
       <form class="form-horizontal" method="post">
       <table align="left" width="1000px" height="140px">
        
          <tr>
             <td ><h4>主页：</h4></td>
             <td>
             <input name="site" value="${feed.site}" class="input-xxlarge" type="text"  placeholder="输入主页地址" style="height:30px;font-size:18px;"/>
             </td>
             <td>${wf:errorMessage(errors, "site")}</td>
         </tr>
          <tr>
             <td width="15%"><h4>rss源：</h4></td>
             <td  width="50%">
                <input name="url" value="${feed.url}" class="input-xxlarge" type="text"  placeholder="输入Feed源" style="height:30px;font-size:18px;"/>
             </td>
             <td  width="35%"><span style> ${wf:errorMessage(errors, "url")}</span></td>
         </tr>
          <tr>
             <td ><h4>描述：</h4></td>
             <td>
             <textarea rows="10" cols="200" name="shortDesc"  style="height:200px;width:530px;font-size:18px;"  placeholder="输入描述">${feed.shortDesc}</textarea>
             </td>
             <td>${wf:errorMessage(errors, "shortDesc")}</td>
         </tr>
          <tr>
             <td ><h4>标签：</h4></td>
             <td>
             <input name="tags" value="${feed.tags}" class="input-xxlarge" type="text"  placeholder="输入标签" style="height:30px;font-size:18px;"/>
             </td>
             <td>
                ${wf:errorMessage(errors, "tags")}
             </td>
         </tr>
          <tr>
             <td>   <!--  <h4>类型：</h4> --></td>
             <td>
             <input type="hidden" name="type" value="0"/>
             <!--  
             <select name="type">
                 <option value="0"  <c:if test="${feed.type eq 0}"> selected="selected" </c:if>   >普通博客</option>
                 <option  value="1" <c:if test="${feed.type eq 1}"> selected="selected" </c:if>   >热点博客</option>
                 <option value="2"  <c:if test="${feed.type eq 2}"> selected="selected" </c:if>   >新闻网站</option>
             </select>
             -->
            </td>
             <td>${wf:errorMessage(errors, "type")}</td>
         </tr>
         <tr>
             <td>
                   <button class="btn btn-large" type="submit" onclick="return goToPage();">取消</button>
              </td>
             <td>
	             
	             <c:if test="${feed.id == null}">
	                <button class="btn btn-large btn-primary" type="submit">添加</button>
	             </c:if>
	             
	             <c:if test="${feed.id != null}">
	                <input type="hidden" name="id"  value="${feed.id}"/>
	                <button class="btn btn-large btn-primary" type="submit">编辑</button>
	             </c:if>
	             &nbsp; &nbsp;
              </td>
              <td></td>
         </tr>
       </table>
       ${csrfToken.hiddenField}
       
  
     </form>
</div>



<script type="text/javascript">
function goToPage(){
	 window.location.href='feedList.html';
	 return false;
}
</script>  
 <c:if test="${button.id != null}">
    <input type="hidden"  id="highLightMenu" value="h_feedList"/> 
 </c:if>
<c:if test="${button.id == null}">
<input type="hidden"  id="highLightMenu" value="h_feed"/> 
</c:if>
<%@include file="../common/footer.jsp" %>