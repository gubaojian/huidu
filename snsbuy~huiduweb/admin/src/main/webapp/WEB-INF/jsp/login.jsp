<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="common/header.jsp" %>
   <br/>
   <br/>
   <div align="center" style="margin:auto;font-size:32px;font-style:bolder;font-weight: bolder;">汇读<span style="font-size:16px;">(汇集散落的智慧)</span></div>
   <form class="form-horizontal" method="post"   autocomplete="on"> 
       <table align="center" width="400px" height="220px" >
         <tr>
             <td width="30%"><h3>请登陆</h3></td>
             <td>&nbsp;</td>
         </tr>
         <tr>
             <td><h4>用户名：</h4></td>
             <td >
             <input autocomplete="on" name="nick" value="${account.nick}" class="input-xlarge" type="text"  placeholder="用户名" style="height:30px;font-size:18px;">
             </td>
         </tr>
         <tr>
             <td><h4>密码：</h4></td>
             <td><input autocomplete="on" name="password" value="${account.password}" class="input-xlarge warning" type="password" placeholder="密码"  style="height:30px;font-size:18px;"></td>
         </tr>
         <tr>
             <td><button class="btn btn-large btn-primary" type="submit">登录</button></td>
             <td>
              <span class="text-error">
                <c:out value="${message}"/>
              </span>
             </td>
         </tr>
       </table>
     </form>
 
  <br/>
  <br/>
<%@include file="common/footer.jsp" %>