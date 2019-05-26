<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../../common/header.jsp" %>
<%@include file="../../common/sellerNav.jsp" %>


<div class="container content-body">
          <table class="table" align="center" style="width:800px;">
           <caption><h3>微店铺账户</h3></caption>
            <tr>
                 <td>淘宝网昵称</td>
                 <td>${seller.taobaoUserNick}</td>
                 <td></td>
            </tr>
            <tr>
                 <td>微店铺账户名</td>
                 <td>${account.nick}</td>
                 <td>
                      <button class="btn" type="submit" onClick="window.location.href='${configure.serverUrl}/seller/account/editAccountNick.html';">修改</button>
                </td>
            </tr>
            <tr>
                 <td>微店铺账户密码</td>
                 <td>******</td>
                 <td> 
                    <button class="btn" type="submit" onClick="window.location.href='${configure.serverUrl}/seller/account/editAccountPassword.html';";>修改</button>
                 </td>
            </tr>
            <tr>
            </tr>
          </table>
       <c:if test="${'96e79218965eb72c92a549dd5a330112' == account.password}">   
          <div  class="alert alert-danger" style="font-size:16px;">
             友情提示：你的微店铺账户初始密码为：111111；为确保您的账户安全，请即及时修改密码。
          </div>
       </c:if>
       <div class="alert alert-warn">退出登录账户？<a href="javascript:void(0);" onclick="submitLoginOut();">退出</a></div>
</div>

<form id="loginOutForm" method="post" action="${configure.serverUrl}/loginOut.htm">
   ${csrfToken.hiddenField}
</form>
<script type="text/javascript">
<!--
   function submitLoginOut(){
	  $("#loginOutForm").submit();
}
//-->
</script>

  
<%@include file="../../common/footer.jsp" %>