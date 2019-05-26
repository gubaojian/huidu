<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../../common/header.jsp" %>
<%@include file="../../common/sellerNav.jsp" %>


<div class="container content-body">
    <c:if test="${message != null}">
      <div class="alert alert-error">${message}</div>
    </c:if>
  
    <h3>微店铺账户名</h3>
    <form class="form-horizontal" method="post">
       ${csrfToken.hiddenField}
       <table align="left" width="500px" height="120px">
         <tr>
             <td width="25%"><h4>新账户名：</h4></td>
             <td>
               <input name="nick" value="${nick}" class="input-xlarge" type="text" 
              placeholder="请输入新的独立微微账户名" style="height:30px;font-size:18px;">
             </td>
             <td width="20%">(6-20个字)</td>
         </tr>
         <tr>
             <td><button class="btn btn-large" type="submit"  onclick="window.location.href='${configure.serverUrl}/seller/account/sellerInfo.html';return false;">取消</button></td>
             <td><button class="btn btn-large btn-primary" type="submit">修改</button></td>
              <td></td>
         </tr>
       </table>
     </form>
</div>

  
<%@include file="../../common/footer.jsp" %>