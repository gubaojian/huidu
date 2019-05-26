<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@include file="WEB-INF/jsp/common/header.jsp" %>
<%@include file="WEB-INF/jsp/common/productNav.jsp" %>
<div class="container content-body">
   <div>
         <h4 align="center">创建了微信自定义菜单，但微信客户端查看不到菜单？</h4>
         <ol>
             <li>
                 <h5>到<a href="http://weiweishoper.sinaapp.com/seller/dataSynInfo.html" target="_blank">微店铺“数据同步”</a>查看自定义菜单同步情况；若同步未完成，等待同步完成，再查看菜单是否生效。</h5>
                   <img src="http://assertcdn.sinaapp.com/image/weiwei/help/syninfo.jpg" width="600"/>
             </li>
             <li>
                 <h5>如果自定义菜单同步已完成，查看同步执行详情信息，查看自定义菜单同步是否成功。</h5>
             </li>
             <li>
                 <h5>检查微信自定义菜单个数是否符合要求，主菜单个数2-3个；主菜单若有子菜单，子菜单个数需为2-5个</h5>
             </li>
             <li>
                 <h5>检查您的微信版本，请确保微信版本在4.2版本及以上</h5>
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