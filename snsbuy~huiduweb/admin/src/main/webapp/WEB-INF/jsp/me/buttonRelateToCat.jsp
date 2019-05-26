<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/sellerNav.jsp" %>

<style>
table tr td{
  height:5px;
}
</style>
 <div class="container content-body">
     <form method="post">
        ${csrfToken.hiddenField}
        <input type="hidden" name="buttonId" value="${buttonId}">
        <h3 align="center">关联店铺自定义类目</h3>
          <table align="center" width="100%" style="font-size:16px;">
            <c:forEach var="sellerCat" items="${sellerCatList}" varStatus="eachStatus">
               <c:set var="indexMod" value="${(eachStatus.index mod 5)}"></c:set>
               <c:if test="${indexMod == 0}">
                  <tr>
               </c:if>
                  <td>
                       ${sellerCat.name} 
                      <input name="cid" type="checkbox" <c:if test="${sellerCat.status != null}"> checked</c:if>   value="${sellerCat.cid}">
                  </td>
              <c:if test="${indexMod == 4}">
                 </tr>
               </c:if>
            </c:forEach>
             <c:if test="${indexMod != 4}">
                 </tr>
              </c:if>
           <tr>
             <td></td>
             <td></td>
             <td>
                  <button class="btn btn-large" type="submit" onclick="return goToPage();">取消</button>
                    &nbsp;
                   <button class="btn btn-large btn-primary" type="submit">保存</button>
              </td>
             <td></td>
             <td></td>
           </tr>
      </table>
     </form>
     
</div>
        
<script type="text/javascript">
function goToPage(){
	 window.location.href='weixinButtonList.html';
	 return false;
}
</script>

<input type="hidden"  id="highLightMenu" value="h_weixinButtonList"/>  
<%@include file="../common/footer.jsp" %>