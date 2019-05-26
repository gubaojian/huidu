<%@page import="net.java.efurture.judu.web.util.*" %>
<%@page import="net.java.efurture.judu.web.constants.*" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page session="false" %>
<%
if(request.getRemoteHost().contains("ta") 
		|| FromUtils.isFromTA(request)){
	response.sendRedirect("http://ta.lanxijun.com/d.html");
	return;
}
ResponseCacheUtils.noneCache(response);
%>
<%@include file="WEB-INF/jsp/common/header.jsp" %>
<%@include file="WEB-INF/jsp/common/productNav.jsp" %>
<style>
   /* Main marketing message and sign up button */
     .carousel{
          min-width:100%;
		  width:100%;
     }
	 .item{
		  margin-left:10px;
		  margin-right:10px;
     }
    
     .jumbotron {
         margin-top: 10px;
         text-align:center;
      }
      
      .iphone-div{
         height:538px;
         width:284px;
         float:left;
         display:inline;
         padding-top:98px;
         padding-left:36px;
         text-align:left;
         -moz-background-size: 100% 100%;  
         -o-background-size: 100% 100%;  
         -webkit-background-size: 100% 100%;  
         background-size: 100% 100%;  
         -moz-border-image: url( http://lanasset.qiniudn.com/image/huidu/image/hardware_iphone.png) 0;  
         background-image:url( http://lanasset.qiniudn.com/image/huidu/image/hardware_iphone.png);  
         background-repeat:no-repeat;  
         filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=' http://lanasset.qiniudn.com/image/huidu/image/hardware_iphone.png', sizingMethod='scale');   
      }
      
     
      .screen-iphone{
        width:240px;
        height:426;
      }
      .screen-android-pad{
        width:364px;
        height:464px;
      }
      
      .screen-ipad{
        width:384px;
        height:512px;
      }
      
      
      .android-div{
         height:560px;
         width:298px;
         float:left;
         display:inline;
         padding-top: 58px;
         padding-left: 25px;
         text-align:left;
         -moz-background-size: 100% 100%;  
         -o-background-size: 100% 100%;  
         -webkit-background-size: 100% 100%;  
         background-size: 100% 100%;  
         -moz-border-image: url( http://lanasset.qiniudn.com/image/huidu/image/android_phone.png) 0;  
         background-image:url( http://lanasset.qiniudn.com/image/huidu/image/android_phone.png);  
         background-repeat:no-repeat;  
         filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=' http://lanasset.qiniudn.com/image/huidu/image/android_phone.png', sizingMethod='scale'); 
      }
      
      .screen-android{
          height:484px;
       }
      
      .ipad-div{
         height: 575px;
         width:432px;
         float:left;
         background-size: 100% 100%;
         padding-top: 64px;
         padding-left: 62px;
         text-align:left;
         -moz-background-size: 100% 100%;  
         -o-background-size: 100% 100%;  
         -webkit-background-size: 100% 100%;  
         background-size: 100% 100%;  
         -moz-border-image: url( http://lanasset.qiniudn.com/image/huidu/image/ipad.png) 0;  
         background-image:url( http://lanasset.qiniudn.com/image/huidu/image/ipad.png);  
         background-repeat:no-repeat;  
         filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=' http://lanasset.qiniudn.com/image/huidu/image/ipad.png', sizingMethod='scale'); 
         margin-left: 140px;
      }
      
      .android-pad-div{
         height:560px;
         width:425px;
         float:left;
         background-size: 100% 100%;
         padding-top: 64px;
         padding-left: 58px;
         text-align:left;
         -moz-background-size: 100% 100%;  
         -o-background-size: 100% 100%;  
         -webkit-background-size: 100% 100%;  
         background-size: 100% 100%;  
         -moz-border-image: url( http://lanasset.qiniudn.com/image/huidu/image/android_pad.png) 0;  
         background-image:url( http://lanasset.qiniudn.com/image/huidu/image/android_pad.png);  
         background-repeat:no-repeat;  
         filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=' http://lanasset.qiniudn.com/image/huidu/image/android_pad.png', sizingMethod='scale'); 
         margin-left: 120px;
      }
      
      .image-container{
         margin-left: 60px;
      }
      
      .aligncenter {
		  clear: both;
		  display: block;
		  margin:auto;
	  }
     
      .qrcode_ios{
         width:140px;
         height:140px;
      }
      .qrcode_android{
         width:160px;
         height:160px;
       }

    .qrcode_tip{
        color:#999999;
        margin-top: 10px;
        font-size: 14px;
     }
  
  .carousel-indicators{
     top: 24px;
      right: 40px;
  }
  .carousel-indicators li {
     background-color: #999;
  }

  .carousel-indicators .active {
     background-color: #222;
   }
  
</style>
<div class="container content-body">
 <table width="100%" style="overflow:visible;">
     <tr>
        <td width="80%">
        
          <!--  视图切换  -->
		<div id="myCarousel" class="carousel slide">
		  <ol class="carousel-indicators">
		         <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		         <li data-target="#myCarousel" data-slide-to="1"></li>
		         <li data-target="#myCarousel" data-slide-to="2"></li>
		         <li data-target="#myCarousel" data-slide-to="3"></li>
		    </ol>
		  <!-- Carousel items -->
		  <div class="carousel-inner">
		  <div class="active item">
		        <div class="jumbotron">
		           <h3>汇读 &nbsp;最专业的阅读工具</h3>
		         <div class="image-container">
		             <div class="android-div"> 	    
		                <img  class="screen-android" src=" http://lanasset.qiniudn.com/image/huidu/image/main.png"/>
		            </div>
		             <div  class="iphone-div">  
		                <img class="screen-iphone"  src=" http://lanasset.qiniudn.com/image/huidu/image/search.png"/>   
		            </div>
		         </div>
		      </div>
		    </div>
		    <div class="item">
		        <div class="jumbotron">
		            <h3>个性定制 &nbsp;关注兴趣</h3>
		           <div class="image-container">
		                <div class="android-div"> 	    
		                  <img class="screen-android" src=" http://lanasset.qiniudn.com/image/huidu/image/favorite-tag.png"/>
		                </div>
		               <div  class="iphone-div"> 
		                  <img class="screen-iphone" src=" http://lanasset.qiniudn.com/image/huidu/image/favorite-article.png"/>
		                </div>
		            </div>
		          <br/>
		        </div>
		    </div>
		    <div class="item">
		        <div class="jumbotron">
		            <h3>Andoid Pad 洞悉未来浪潮</h3>
		            <div  class="android-pad-div"> 
		                <img class="screen-android-pad" src=" http://lanasset.qiniudn.com/image/huidu/image/wulanwang.png"/>
		            </div>
		        </div>
		    </div>
		    <div class="item">
		        <div class="jumbotron">
		            <h3>iPad 汇集智慧 在阅读中成长</h3>
		            <div  class="ipad-div"> 
		                <img class="screen-ipad"  src=" http://lanasset.qiniudn.com/image/huidu/image/java7.png"/>
		            </div>
		        </div>
		    </div>
		  </div>
		   <!-- Carousel nav -->
		  <a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
		  <a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
		</div>
		<!--切换视图结束-->
        </td>
         <td width="20%"  valign="top"  align="center">
              <br/>
              <br/>
              <br/>
              <br/>
              <a class="btn btn-large btn-success" style="width:80%" href="http://lanasset.qiniudn.com/file/Huidu.apk"   target="_blank">Android/Pad</a>
              <br/>  
              <br/> 
              <img class="aligncenter qrcode_android" src=" http://lanasset.qiniudn.com/image/huidu/image/android.png" width="160" height="160"/>
              <br/>
              <span  class="qrcode_tip">
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机扫描二维码下载
              </span>
              <br/>
             <br/>
             <br/>
             <a class="btn btn-large btn-success" style="width:80%" href="https://itunes.apple.com/cn/app/hui-du-cheng-xu-yuan-she-ji/id692997116?ls=1&mt=8"  target="_blank" >iPhone/iPad</a>
              <br/>  
              <br/> 
               <img class="aligncenter qrcode_android" src=" http://lanasset.qiniudn.com/image/huidu/image/qrcode.png" width="160" height="160"/>
               <br/>
               <span  class="qrcode_tip">
                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机扫描二维码下载
               </span>
               <br/>
         </td>
     </tr>
 </table>

 <div class="container" style="text-align:center;">
         <table align="center" width="98%">
            <tr>
               <td width="25%"> 
                  <a class="btn btn-large btn-success" style="width:80%;" href="http://lanasset.qiniudn.com/file/Huidu.apk"  target="_blank">Android</a> 
               </td>
               <td width="25%"> 
                  <a class="btn btn-large btn-success" style="width:80%;" href="https://itunes.apple.com/cn/app/hui-du-cheng-xu-yuan-she-ji/id692997116?ls=1&mt=8"  target="_blank">iPhone</a> 
               </td>
               <td width="25%">   
                 <a class="btn btn-large btn-success" style="width:80%;" href="https://itunes.apple.com/cn/app/hui-du-cheng-xu-yuan-she-ji/id692997116?ls=1&mt=8"  target="_blank">iPad</a>
               </td>
               <td width="25%">   
                 <a class="btn btn-large btn-success" style="width:80%;" href="http://lanasset.qiniudn.com/file/Huidu.apk"  target="_blank">Android Pad</a>
               </td>
            </tr>
            <tr>
               <td>
                  <br/>
                  <img  class="qrcode_android" src=" http://lanasset.qiniudn.com/image/huidu/image/android.png" width="160" height="160"/>
                  <br/>
                  <br/>
                  <span  class="qrcode_tip">
                      手机扫描二维码下载
                  </span>
                  <br/>
               </td>
               <td> 
                   <br/>  
                   <img   class="qrcode_android" src=" http://lanasset.qiniudn.com/image/huidu/image/qrcode.png" width="160" height="160"/>
                  <br/><br/>
                  <span  class="qrcode_tip">
                      手机扫描二维码下载
                  </span>
                  <br/>
               </td>
                <td> 
                   <br/>  
                   <img  class="qrcode_android" src=" http://lanasset.qiniudn.com/image/huidu/image/qrcode.png"  width="160" height="160"/>
                  <br/><br/>
                  <span  class="qrcode_tip">
                      手机扫描二维码下载
                  </span>
                  <br/>
               </td>
               <td> 
                   <br/>  
                   <img  class="qrcode_android" src=" http://lanasset.qiniudn.com/image/huidu/image/android.png" width="160" height="160"/>
                  <br/>
                  <br/>
                  <span  class="qrcode_tip">
                      手机扫描二维码下载
                  </span>
                  <br/>
               </td>
               
            </tr>
         </table>
 </div>
</div>
<br/><br/>
<input type="hidden"  id="highLightMenu" value="h_index"/>  
 <div style="visibility: hidden;">
    <script src="http://s20.cnzz.com/stat.php?id=5641054&web_id=5641054&show=pic1" language="JavaScript"></script>
 </div>
<%@include file="WEB-INF/jsp/common/footer.jsp" %>

<!-- 
<%= request.getRemoteHost() %>
 -->

