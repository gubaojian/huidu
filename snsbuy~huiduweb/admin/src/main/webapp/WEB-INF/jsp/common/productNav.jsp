<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
 <!--header -->
   <style type="text/css">
      body {
        padding-top: 10px;
        padding-bottom: 40px;
      }
      
      
      .content-body{
		  min-width:1000px;
		  width:1000px;
	  }

      /* Custom container */
      .container {
          margin: 0 auto;
          max-width: 1000px;
      }
      .container > hr {
        margin: 20px 0;
      }

     
      
      /* Customize the navbar links to be fill the entire space of the .navbar */
      .navbar .navbar-inner {
        padding: 0;
      }
      .navbar .nav {
        margin: 0;
        display: table;
        width: 100%;
      }
      .navbar .nav li {
        display: table-cell;
        width: 1%;
        float: none;
      }
      .navbar .nav li a {
        font-weight: bold;
        text-align: center;
        border-left: 1px solid rgba(255,255,255,.75);
        border-right: 1px solid rgba(0,0,0,.1);
      }
      .navbar .nav li:first-child a {
        border-left: 0;
        border-radius: 3px 0 0 3px;
      }
      .navbar .nav li:last-child a {
        border-right: 0;
        border-radius: 0 3px 3px 0;
      }
    </style>
<!--[if lt IE 9]>
     <style >
            .navbar{
              display:none;
            }
     </style>
<![endif]-->
 <div class="container" style="padding:0px;margin:0px auto 0px auto;">
       <div class="masthead">
        <div class="muted"><h2 style="display:inline;color:#000">汇读</h2>
        <span>(程序员/设计师/产品经理必备的专业阅读工具，了解业界最新最专业最优质的分享)</span></div>
        <div class="navbar">
          <div class="navbar-inner">
            <div class="container">
              <ul class="nav">
                <li id="h_index"><a href="${configure.serverUrl}/index.jsp">首页</a></li>
                <li id="h_product_aboutMe"><a href="${configure.serverUrl}/about.jsp">关于汇读</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
 </div>