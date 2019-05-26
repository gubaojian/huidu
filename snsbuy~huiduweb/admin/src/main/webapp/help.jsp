<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@include file="WEB-INF/jsp/common/header.jsp" %>
<%@include file="WEB-INF/jsp/common/productNav.jsp" %>
<div class="container content-body">
       <style>
		    ol li{
				 font-size:18px;
				 margin-top:10px;
		    }
		</style>
        <h3>微店铺帮助指南</h3>
        <ol >
           <li>
                <a href="turtorial-start.jsp"  target="_blank">如何开通微店铺，如何拥有自己的微信淘宝店？</a>
               <br/>
           </li>
           <li>
                <a href="help-search-item.jsp"  target="_blank">店铺有商品，但微信上查询不到商品？</a>
           </li>
           <li>
                <a href="help-weixin-menu.jsp"  target="_blank">创建了微信自定义菜单，但微信客户端查看不到菜单？</a>
           </li>
        </ol>
</div>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<input type="hidden"  id="highLightMenu" value="h_product_help"/>   
<%@include file="WEB-INF/jsp/common/footer.jsp" %>