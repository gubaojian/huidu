<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@include file="WEB-INF/jsp/common/header.jsp" %>
<%@include file="WEB-INF/jsp/common/productNav.jsp" %>
<div class="container content-body">
   
   <div>
         <h4 align="center">店铺有商品，但微信上查询不到商品？</h4>
         <ol>
             <li>
                 <h5>到<a href="http://weiweishoper.sinaapp.com/seller/dataSynInfo.html" target="_blank">微店铺“数据同步”</a>查看商品同步情况；若同步未完成，等待同步完成，再尝试查询操作</h5>
                  
                   <img src="http://assertcdn.sinaapp.com/image/weiwei/help/syninfo.jpg" width="600"/>
                  
             </li>
             <li>
                 <h5>如果商品同步已完成，请查看您的<a href="https://mp.weixin.qq.com" target="_blank">微信公众平台</a>是否开启开发者模式；若未开启，开启开发者模式。<a href="turtorial-start.jsp#two" target="_blank">如何开启微信公众平台开发模式</a></h5>
             </li>
             <li>
                 <h5>若仍有问题，请联系客服解决。QQ：907022331 (服务时间：[周一至周五]晚上20点-22点&nbsp;&nbsp;&nbsp;&nbsp;[周六至周日]上午10点-晚上22点)</h5>
             </li>
         </ol>
        </div>
        
   
</div>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<input type="hidden"  id="highLightMenu" value="h_product_help"/>   
<%@include file="WEB-INF/jsp/common/footer.jsp" %>