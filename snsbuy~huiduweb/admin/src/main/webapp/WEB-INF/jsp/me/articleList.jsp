<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/header.jsp" %>
<%@include file="../common/sellerNav.jsp" %>


 <div class="container content-body">
		 <div align="center">
            <form class="form-search" action="articleList.html" method="get">
             <input name="title" value="${query.title}" type="text" class="input-xxlarge" style="height:30px;font-size:18px;" placeholder="请输入文章标题">
              &nbsp;&nbsp;
              <select name="status" style="width:80px;height:40px;">
                <option value="">全部</option>
                 <option value="0" <c:if test="${query.status eq 0}"> selected="selected" </c:if>>正常</option>
                 <option  value="-1" <c:if test="${query.status eq -1}"> selected="selected" </c:if>>已删除</option>
              </select>
              &nbsp;&nbsp;
              <button type="submit" class="btn  btn-large">查询</button>
            </form>
          </div>
          <c:if test="${articleList == null or fn:length(articleList) <= 0}">
		      <c:if test="${message == null}">
		           <div class="alert alert-info">亲，文章为空。</div>
		      </c:if>
		      <c:if test="${message != null}">
		          <div class="alert alert-info">${message}</div>
		      </c:if>
		  </c:if>
		  <c:if test="${articleList != null and fn:length(articleList) > 0}">
             <table class="table"  width="900px;">
	           <caption><h3>文章列表</h3></caption>
	            <thead>
	                <tr>
	                 <th  width="50%">标题</th>
	                 <th width="15%">创建时间</th>
	                 <th width="10%">原内容</th>
	                 <th width="10%">处理后</th>
	                 <th width="7.5%">前台</th>
	                  <th width="7.5%">操作</th>
	                </tr>
	            </thead>
	         
	            <c:forEach var="article" items="${articleList}">
		            <tr>
		                 <td> <a href="${article.url}" target="_blank">${article.title}</a></td>
		                 <td>
		                   <span style="font-size:12px;">
		                       <c:if test="${article.publishDate != null}">
		                           <fmt:formatDate value="${article.publishDate}"  pattern="yyyy-MM-dd HH:mm:ss"/>
		                        </c:if>
		                   </span>
		                    
		                 </td>
		                 <td>
		                     <a href="${configure.serverUrl}/me/articleDetail.htm?id=${article.id}" target="_blank">查看</a>
		                 </td>
		                 <td>
		                     <a href="${configure.serverUrl}/me/articleDetail.htm?id=${article.id}&source=false" target="_blank">查看</a>
		                 </td>
		                 <td>
		                     <a href="${configure.serverUrl}/articleDetail.htm?id=${article.id}" target="_blank">查看</a>
		                 </td>
		                 <td >
		                     <c:if test="${article.status < 0}">
		                        已删除
		                     </c:if>
		                     <c:if test="${article.status == 0}">
		                     <a href="#" class="delete-article-button" data-id="${article.id}">删除</a>
		                     </c:if>
		                     <br/>
		                 </td>
		                 
		            </tr>  
	            </c:forEach>
	          </table>
	          <c:url var="queryUrl" value="${configure.serverUrl}/me/articleList.html" >
                 <c:param name="title" value="${query.title}" />
                 <c:param name="status" value="${query.status}"></c:param>
               </c:url>
	           <%@include file="../common/pagination.jsp" %>
	          
          </c:if>
</div>


<form  id="deleteArticleForm" method="post" action="deleteArticle.html">
      <input id="deleteArticleId" name="id"  value="id" type="hidden"/>
      ${csrfToken.hiddenField}
</form>

 <script type="text/javascript">
	     $(document).ready(function(){
				$(".delete-article-button").click(function(obj){
						if(!confirm("确认删除此文章")){
							return;
						}
						var dataId = $(this).attr("data-id");
						$("#deleteArticleId").val(dataId);
						$("#deleteArticleForm").submit();
				});
		  });
</script>    


<input type="hidden"  id="highLightMenu" value="h_articleList"/>        
<%@include file="../common/footer.jsp" %>