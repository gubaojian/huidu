<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
 <div class="container">
          <!-- 导航栏开始 -->
          <div class="navbar">
            <div class="navbar-inner">
              <a  id="h_index" class="brand" href="${configure.serverUrl}/me/index.html">汇读</a>
              <ul class="nav">
                <li  id="h_feed" >
                     <a href="${configure.serverUrl}/me/feed.html">添加Feed</a>
                </li>
                <li id="h_feedList"><a href="${configure.serverUrl}/me/feedList.html">Feed管理</a></li> 
                <li id="h_dataSynInfo"><a href="${configure.serverUrl}/me/dataSynInfo.html">数据同步</a></li>   
                <li id="h_categoryList"><a href="${configure.serverUrl}/me/categoryList.html">类目信息</a></li>  
                <li id="h_articleList"><a href="${configure.serverUrl}/me/articleList.html">文章列表</a></li>      
               </ul>
             </div>
          </div>
       <!-- 导航栏结束 -->
 </div>