<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/sellerNav.jsp" %>


 <div class="container content-body">
 
    <c:if test="${weixinApp == null}">
       <div class="alert alert-warn">系统错误，找不到您店铺的应用配置信息，请联系系统管理员解决问题。</div>
    </c:if>
    <c:if test="${weixinApp != null}">
       <table class="table" align="center" style="width:900px;">
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
                 <td>${weixinApp.signToken}</td>
            </tr>
            <tr>
                 <td>自定义菜单凭证appid</td>
                 <td>
                    <c:choose>
                      <c:when test="${weixinApp.appId != null}">
                         ${weixinApp.appId}
                      </c:when>
                      <c:otherwise>
                         您还未填写<a href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%80%9A%E7%94%A8%E6%8E%A5%E5%8F%A3%E6%96%87%E6%A1%A3" target="_blank">自定义菜单凭证</a>，未填写会导致自定义菜单不可用，赶紧填写吧!
                      </c:otherwise>
                   </c:choose>
                 </td>
            </tr>
            <tr>
                 <td>自定义菜单凭证appsecret</td>
                 <td >
                   <c:choose>
                      <c:when test="${weixinApp.appSecret != null}">
                         ${weixinApp.appSecret}
                      </c:when>
                      <c:otherwise>
                         您还未填写<a href="http://mp.weixin.qq.com/wiki/index.php?title=%E9%80%9A%E7%94%A8%E6%8E%A5%E5%8F%A3%E6%96%87%E6%A1%A3" target="_blank">自定义菜单凭证</a>，未填写此项会导致自定义菜单不可用，赶紧填写吧！  
                      </c:otherwise>
                    </c:choose> 
                 </td>
            </tr>
            <tr>
                 <td>自定义菜单凭证grant_type</td>
                 <td>client_credential</td>
            </tr>
            
            <tr>
                 <td><button class="btn btn-large" type="submit"
                 onClick="window.location.href='editAppConfig.html';">修改</button></td>
                 <td>
                       <br/>
                        <span><a href="https://mp.weixin.qq.com" target="_blank">如何配置微信公众平台?</a></span>&nbsp;&nbsp;
                        <span>查看<a href="https://mp.weixin.qq.com" target="_blank">微信公共平台</a>配置是否正确?</span>
                 </td>
            </tr>
       </table>

    </c:if>
</div>
        



<input type="hidden"  id="highLightMenu" value="h_appConfig"/>   
<%@include file="../common/footer.jsp" %>