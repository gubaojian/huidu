<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.weiwei.com/functions"  prefix="wf"%>
<%@include file="../common/header.jsp"%>
<%@include file="../common/sellerNav.jsp"%>

<div class="container content-body">

      <div align="center">
            <form class="form-search" action="categoryList.html" method="get">
              <input name="pinyin" value="${query.pinyin}" type="text" class="input-xxlarge" style="height:30px;font-size:16px;" placeholder="请输入拼音">
              &nbsp;&nbsp;
              <select name="status" style="width:80px;height:40px;">
                <option value="">全部</option>
                 <option value="0" <c:if test="${query.status eq 0}"> selected="selected" </c:if>>正常</option>
                 <option  value="1" <c:if test="${query.status eq 1}"> selected="selected" </c:if>>结束</option>
                 <option  value="-1" <c:if test="${query.status eq -1}"> selected="selected" </c:if>>已删除</option>
              </select>
              &nbsp;&nbsp;
              <button type="submit" class="btn  btn-large">查询</button>
            </form>
          </div>



    <c:if test="${categoryList == null or fn:length(categoryList) <= 0}">
		      <c:if test="${message == null}">
		           <div class="alert alert-info">亲，没有类目信息。</div>
		      </c:if>
		      <c:if test="${message != null}">
		          <div class="alert alert-info">${message}</div>
		      </c:if>
	 </c:if>
	 
	 <c:if test="${categoryList != null && fn:length(categoryList) > 0}"> 
		   <table class="table">
			 <caption>
				 <h3>文章类目信息</h3>
			 </caption>
			<thead>
				<tr>
				     <th width="10%">编号</th>
					<th width="30%">名字</th>
					<th width="30%">拼音</th>
					<th width="15%">创建时间</th>
					<th width="15%">操作</th>
				</tr>
			</thead>
			<c:forEach var="category" items="${categoryList}">
			   	<tr>
			   	   <td>${category.id}</td>
					<td>
					   ${category.name}
					</td>
					<td>
					    ${category.pinyin}
					</td>
					<td>
					  <span style="font-size:14px;">
					    <c:if test="${category.gmtCreate != null}">
					           <fmt:formatDate value="${category.gmtCreate}"  pattern="yyyy/MM/dd HH:mm:ss"/>
					     </c:if>
					   </span>
					</td>
					<td>
					     <c:if test="${category.status == 0}">
					         <a href="#" class="delete-category-button"  data-id="${category.id}">删除</a>
					     </c:if>
					</td>
					
			   </tr>
			</c:forEach>
		  </table>
		  <c:url var="queryUrl" value="categoryList.html?pinyin=${query.pinyin}&status=${query.status}"/>
		  <%@include file="../common/pagination.jsp" %>
	</c:if>
</div>


<form  id="startTaskForm" method="post" action="updateTask.html">
      <input id="startTaskId" name="id"  value="id" type="hidden"/>
       <input name="status"  value="0" type="hidden"/>
      ${csrfToken.hiddenField}
</form>


<form  id="endTaskForm" method="post" action="updateTask.html">
      <input id="endTaskId" name="id"  value="id" type="hidden"/>
       <input name="status"  value="1" type="hidden"/>
      ${csrfToken.hiddenField}
</form>

<form  id="deleteCategoryForm" method="post" action="deleteCategory.html">
      <input id="deleteCategoryId" name="id"  value="id" type="hidden"/>
       <input name="status"  value="-1" type="hidden"/>
      ${csrfToken.hiddenField}
</form>


 <script type="text/javascript">
	     $(document).ready(function(){
				$(".delete-category-button").click(function(obj){
						if(!confirm("确认删除此类目")){
							return;
						}
						var dataId = $(this).attr("data-id");
						$("#deleteCategoryId").val(dataId);
						$("#deleteCategoryForm").submit();
				});
		  });
</script>    



<input type="hidden"  id="highLightMenu" value="h_categoryList"/>     
<%@include file="../common/footer.jsp"%>