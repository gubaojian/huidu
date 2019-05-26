<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.weiwei.com/functions"  prefix="wf"%>
<%@include file="../common/header.jsp" %>
<%@include file="../common/sellerNav.jsp" %>


 <div class="container content-body">
		 <div align="center">
            <form class="form-search" action="feedList.html" method="get">
              <input name="targetValue" value="${formQuery.targetValue}" type="text" class="input-xxlarge" style="height:30px;font-size:16px;" placeholder="请输入网址">
              &nbsp;&nbsp;
              <select name="type" style="width:80px;height:40px;">
                 <option value="0">主页</option>
                 <option  value="1" <c:if test="${formQuery.type eq 1}"> selected="selected" </c:if>>rss地址</option>
              </select>
              &nbsp;&nbsp;
              <button type="submit" class="btn  btn-large">查询</button>
            </form>
          </div>
          <c:if test="${feedList == null or fn:length(feedList) <= 0}">
		      <c:if test="${message == null}">
		           <div class="alert alert-info">亲，feed为空。</div>
		      </c:if>
		      <c:if test="${message != null}">
		          <div class="alert alert-info">${message}</div>
		      </c:if>
		  </c:if>
		  <c:if test="${feedList != null and fn:length(feedList) > 0}">
             <table class="table table-bordered"  width="900px;">
	           <caption><h3>Feed信息列表</h3></caption>
	            <thead>
	                <tr>
	                 <th  width="5%">编号</th>
	                 <th  width="10%">网址</th>
	                 <th width="20%">Feed</th>
	                 <th width="40%">描述</th>
	                 <th width="25%">操作</th>
	                </tr>
	            </thead>
	         
	            <c:forEach var="feed" items="${feedList}">
		            <tr>
		                 <td rowspan="2">
		                   ${feed.id}
		                 </td>
		                 <td>
		                   <a href="${feed.site}" target="_blank">${feed.site}</a>
		                 </td>
		                 <td> <a href="${feed.url}" target="_blank"> ${feed.url}</a> </td>
		                 <td>${feed.shortDesc}</td>
		                 <td rowspan="2">
		                     <a href="feed.html?id=${feed.id}" target="_blank">编辑</a>
	                         &nbsp;&nbsp;
	                         <a href="#" class="delete-feed-button"  data-id="${feed.id}">删除</a>
	                         <br/>
	                         <a href="dataSynInfo.html?relateId=${feed.id}" target="_blank">同步详情</a>
	                         
	                         &nbsp;&nbsp;
	                         <a target="_blank" href="articleList.html?feedId=${feed.id}">文章</a>
		                 </td>
		            </tr>  
		             <tr>
		                 <td>
		                  <%-- ${wf:nameForFeedType(feed.type)}  --%> 
		                  ${feed.tags}
		                 </td>
		                 <td>
		                    <c:if test="${feed.gmtCreate != null}">
		                        创建时间：
		                       <fmt:formatDate value="${feed.gmtCreate}"  pattern="yyyy-MM-dd HH:mm:ss"/>
		                       <br/>
		                    </c:if>
		                 </td>
		                 <td> 
		                     <c:if test="${feed.gmtModified != null}">
		                         修改时间:
		                        <fmt:formatDate value="${feed.gmtModified}"  pattern="yyyy-MM-dd HH:mm:ss"/>
		                    </c:if>
		                  </td>
		            </tr>  
	            </c:forEach>
	          </table>
	          <c:url var="queryUrl" value="feedList.html" >
                 <c:param name="targetValue" value="${formQuery.targetValue}" />
               </c:url>
               <%@include file="../common/pagination.jsp" %>
	          
	          
          </c:if>
</div>


<form  id="deleteFeedForm" method="post" action="deleteFeed.html">
      <input id="feedButtonId" name="id"  value="id" type="hidden"/>
      ${csrfToken.hiddenField}
</form>


 <script type="text/javascript">
	     $(document).ready(function(){
				$(".delete-feed-button").click(function(obj){
						if(!confirm("确认删除此菜单")){
							return;
						}
						var dataId = $(this).attr("data-id");
						$("#feedButtonId").val(dataId);
						$("#deleteFeedForm").submit();
				});
		  });
</script>    

<input type="hidden"  id="highLightMenu" value="h_feedList"/>       


<%@include file="../common/footer.jsp" %>