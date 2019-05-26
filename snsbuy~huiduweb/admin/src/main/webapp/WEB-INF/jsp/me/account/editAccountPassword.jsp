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
      <h3>修改微店铺密码</h3>
     <form class="form-horizontal" method="post">
       ${csrfToken.hiddenField}
       <table align="left" width="600px" height="220px">
         <tr>
             <td width="20%"><h4>原始密码：</h4></td>
             <td>
              <input name="oldPassword" class="input-xlarge" type="password" 
              placeholder="请输入原始密码" style="height:30px;font-size:18px;">
             </td>
             <td width="30%"></td>
         </tr>
         <tr>
             <td><h4>新密码：</h4></td>
             <td ><input name="newPassword" class="input-xlarge" type="password" 
              placeholder="请输入新密码" style="height:30px;font-size:18px;">
             </td>
             <td> <span style="font-size:14px;">英文数字下划线,长度6-20</span></td>
         </tr>
         <tr>
             <td><h4>确认新密码：</h4></td>
             <td ><input name="confirmNewPassword" class="input-xlarge" type="password" 
              placeholder="请确认新密码" style="height:30px;font-size:18px;">
             </td>
             <td></td>
         </tr>
         <tr>
             <td><button  onclick="window.location.href='${configure.serverUrl}/seller/account/sellerInfo.html';return false;" class="btn btn-large" type="submit">取消</button></td>
             <td><button class="btn btn-large btn-primary" type="submit">修改</button>
                 &nbsp; &nbsp; </td>
              <td></td>
         </tr>
       </table>
     </form>
</div>

  
<%@include file="../../common/footer.jsp" %>