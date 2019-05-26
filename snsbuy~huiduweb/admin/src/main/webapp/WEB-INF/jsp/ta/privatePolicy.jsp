<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
    <c:when test="${lang  eq 'zh'}">
        <c:set value="TA在哪" var="title"/>
        <c:set value="隐私声明" var="private_policy_tips"/>
        <c:set value="TA在哪非常尊重对您的隐私的保护，不会收集关于您的任何隐私信息。远程定位时，软件采用独特安全的技术，确保您的位置信息不被泄露，请放心使用本软件。" var="private_policy_content"/>
    </c:when>
    <c:otherwise>
        <c:set value="Where U" var="title"/>
        <c:set value="Privacy Statement" var="private_policy_tips"/>
        <c:set value="Where U offers great attention to protect of your privacy, any of your privacy information won't be collected. We use powerful security technology to prevent your location from leaking, so you can rest assured of using it." var="private_policy_content"/>
     </c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
    <head>
         <title>${title}</title>
         <link rel="icon"  href="http://lanxijun.oss-cn-shenzhen.aliyuncs.com/ta/favicon.ico" />
         <link rel="shortcut icon"  href="http://lanxijun.oss-cn-shenzhen.aliyuncs.com/ta/favicon.ico" />
         
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style>
            *{margin:0 auto; padding:0 auto;}
             body { 
                max-width: 1000px;
                min-height: 300px;
            }
           .content-font{
                font-size: 20px;
                margin-top: 20px;
            }
            .content-info-div{
                 margin-top: 6px;
                 margin-left: 6px;
                 margin-right: 6px;
                 text-indent:2em; 
                 line-height:120%;
                 font-size: 18px;
                 text-align:justify;   
                 text-justify:inter-ideograph;
            }
        </style>
    </head>
<body >
    <div id="content" class="content-font">
        <h3  align="center">
            ${private_policy_tips}
        </h3>
        <div id="content-info-div"  class="content-info-div">
            ${private_policy_content}
        </div>
    </div>
</body>
</html>