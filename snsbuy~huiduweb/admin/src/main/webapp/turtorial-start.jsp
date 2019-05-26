<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@include file="WEB-INF/jsp/common/header.jsp" %>
<%@include file="WEB-INF/jsp/common/productNav.jsp" %>
<style>
	    .container  img{
			 width:800px;
		}
</style>
<div class="container content-body">

  <h3 align="center">微店铺开通教程，一分钟内拥有自己的微信淘宝店</h3>
      
        <ol type="I">
            <li>
              <h4><a href="#one">创建微店铺账户</a></h4>
            </li>
          <li>
            <h4><a href="#two">开启微信公众平台开发模式</a></h4>
          </li>
            <li>
              <h4><a href="#three">配置微信自定义菜单</a></h4>
            </li>
        </ol>
        
        <br/>
        <div>
         <h3 align="center"> <a name="one">I. 创建微店铺账户</a></h3>
         <ol>
             <li>
                 <h5>去卖家服务市场<a href="http://fuwu.taobao.com/" target="_blank">订阅微店铺服务</a></h5>
             </li>
             <li>
                 <h5>订阅卖家服务后，用卖家账户<a href="http://weiweishoper.sinaapp.com/login.htm" target="_blank">登陆微店铺授权</a></h5>
                 <h5>初次登陆需要用淘宝账户登陆授权，初始化微店铺账户</h5>
                  <img src="http://assertcdn.sinaapp.com/image/weiwei/help/login.jpg"/>
                  <h5>用淘宝账户登陆授权微店铺的权限</h5>
                  <img src="http://assertcdn.sinaapp.com/image/weiwei/help/authorize.jpg"/>
                  <h5>授权成功后，微店铺独立账户详情</h5>
                  <img src="http://assertcdn.sinaapp.com/image/weiwei/help/authorize-success.jpg"/>
             </li>
             <li>
                 <h5>您完成授权后，即成功开通微店铺服务</h5>
                 
             </li>
         </ol>
        </div>
        
        <br/>
        <div>
         <h3 align="center"><a name="two">II. 开启微信公众平台开发模式</a></h3>
         <ol>
             <li>
                 <h5><a href="http://mp.weixin.qq.com" target="_blank">登陆微信公众平台</a>，登录后点击“高级功能”</h5>
                  <img src="http://assertcdn.sinaapp.com/image/weiwei/help/mp.jpg"/>
             </li>
             <li>
                 <h5>点击<a href="http://mp.weixin.qq.com" target="_blank">微信公众平台</a>“编辑模式”，关闭编辑模式</h5>
                  <img src="http://assertcdn.sinaapp.com/image/weiwei/help/mp-normal.jpg"/>
             </li>
             <li>
                 <h5>点击<a href="http://mp.weixin.qq.com" target="_blank">微信公众平台</a>“高级功能”，选择“开发模式”，点击“成为开发者”。</h5>
                  <img src="http://assertcdn.sinaapp.com/image/weiwei/help/mp-developer.jpg"/>
             </li>
             <li>
              <h5>打开<a href="http://weiweishoper.sinaapp.com/seller/appConfig.html"  target="_blank">微店铺公众平台配置</a>；将<a href="http://weiweishoper.sinaapp.com/seller/appConfig.html"  target="_blank">微店铺公众平台配置</a>填写到微信公众平台开发者接口配置信息中。点击提交按钮，完成开发者配置</h5>
                   <img src="http://assertcdn.sinaapp.com/image/weiwei/help/wei-config.jpg"/>
                   <h5>将上页面红框中的项填写到微信开发者接口配置中，如下图：</h5>
                   <img src="http://assertcdn.sinaapp.com/image/weiwei/help/mp-config-writed.jpg"/>
                   
                   
                   
             </li>
              <li>
              <h5>完成开发者模式后，在<a href="http://weiweishoper.sinaapp.com/seller/dataSynInfo.html" target="_blank">微店铺数据同步</a>查看店铺商品同步状况，等待同步完成；即可在手机端查询店铺商品，效果如下：</h5>
                  <img src="http://assertcdn.sinaapp.com/image/weiwei/product-demo-utsing.gif"   width="320" height="480" style="width:320px;height:480px;"/>
             </li>
             <li>
              <h5>如果上述步骤都操作后，在手机微信端查询不到商品，请参考：<a href="help-search-item.jsp" target="_blank">问题解答</a></h5>
             </li>
         </ol>
        </div>
        <br/>
        
    
       <div>
         <h3 align="center"><a name="three">III. 配置微信自定义菜单</a></h3>
         <ol>
             <li>
                 <h5><a href="http://mp.weixin.qq.com" target="_blank">登陆微信公众平台</a>，申请微信公众平台自定义菜单功能，获取appid及appsecret。
                 </h5>
             </li>
             <li>
                 <h5>在微店铺<a href="http://weiweishoper.sinaapp.com/seller/appConfig.html" target="_blank">“微信公众平台配置”</a>中，点击“修改”，填写自定义菜单所需的appid及appsecret；单击“保存”按钮，完成自定义菜单配置。</h5>
                  <img src="http://assertcdn.sinaapp.com/image/weiwei/help/appid.jpg"/>
             </li>
             <li>
                  <h5>完成自定义菜单配置后，在微店铺选择<a href="http://weiweishoper.sinaapp.com/seller/weixinButton.html" target="_blank">自定义菜单</a>或<a href="http://weiweishoper.sinaapp.com/seller/weixinButtonList.html" target="_blank">菜单管理</a>即可添加管理微信自定义菜单.</h5>
                   <h5>创建微信自定义菜单页面</h5>
                   <img src="http://assertcdn.sinaapp.com/image/weiwei/help/addMenu.jpg"/>                <h5>管理微信自定义菜单页面</h5>
                   <img src="http://assertcdn.sinaapp.com/image/weiwei/help/menuList.jpg"/>
             </li>
             <li>
              <h5>完成自定义菜单创建后；等待10分钟，在<a href="http://weiweishoper.sinaapp.com/seller/dataSynInfo.html" target="_blank" >微店铺数据同步</a>中查看菜单同步完成后，即可在手机微信端查看自定义菜单效果，效果图如下：</h5>
                    <img src="http://assertcdn.sinaapp.com/image/weiwei/menu-demo-utsing.gif" width="320" height="480" style="width:320px;height:480px;"/>
             </li>
              <li>
              <h5>如果上述步骤都操作后，在手机微信端却看到公众平台自定义菜单，请参考：<a href="help-weixin-menu.jsp" target="_blank">问题解答</a></h5>
             </li>
             
         </ol>
        </div>    
   
</div>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<input type="hidden"  id="highLightMenu" value="h_product_help"/>   
<%@include file="WEB-INF/jsp/common/footer.jsp" %>