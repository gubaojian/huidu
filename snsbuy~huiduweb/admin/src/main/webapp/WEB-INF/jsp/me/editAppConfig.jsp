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
   <form method="post">
          ${csrfToken.hiddenField}
          <table class="table"  align="center" style="width:900px;">
           <caption><h3>微信公众平台配置</h3></caption>
            <thead>
               <tr>
                  <td  width="25%">配置项</td>
                  <td  width="75%">配置值</td>
               </tr>
                  
            </thead>
             <tr>
                 <td>开发者接口URL</td>
                 <td>${configure.weiweiServiceBaseUrl}/${weixinApp.signUrl}</td>
            </tr>
            <tr>
                 <td>开发者接口Token</td>
                 <td >${weixinApp.signToken}</td>
            </tr>
             <tr>
                 <td>自定义菜单凭证appid</td>
                 <td >
                    <input name="appId" value="${weixinApp.appId}" class="input-xxlarge warning" type="text" placeholder="请填写自定义菜单凭证appid" style="height:30px;font-size:16px;">
                 </td>
            </tr>
            <tr>
                 <td>自定义菜单凭证appsecret</td>
                 <td>
                    <input name="appSecret" value="${weixinApp.appSecret}"  class="input-xxlarge warning" type="text" placeholder="请填写自定义菜单凭证appsecret" style="height:30px;font-size:16px;">
                 </td>
            </tr>
            
             <tr>
                 <td>自定义菜单凭证grant_type</td>
                 <td>client_credential</td>
            </tr>
            <tr>
                 <td>
                 <button class="btn btn-large"  onClick="window.location.href='${configure.serverUrl}/seller/appConfig.html';return false;" type="submit">取消</button>
                 <button class="btn btn-large" type="submit">保存</button></td>
                 <td></td>
            </tr>
          </table>
        </form>
</div>
<input type="hidden"  id="highLightMenu" value="h_appConfig"/>      
<%@include file="../common/footer.jsp" %>