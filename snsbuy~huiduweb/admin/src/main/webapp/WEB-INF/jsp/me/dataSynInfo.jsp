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
            <form class="form-search" action="dataSynInfo.html" method="get">
              <input name="relateId" value="${query.relateId}" type="text" class="input-xxlarge" style="height:30px;font-size:16px;" placeholder="请输入FeedId">
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



    <c:if test="${taskList == null or fn:length(taskList) <= 0}">
		      <c:if test="${message == null}">
		           <div class="alert alert-info">亲，没有数据同步信息。</div>
		      </c:if>
		      <c:if test="${message != null}">
		          <div class="alert alert-info">${message}</div>
		      </c:if>
	 </c:if>
	 
	 <c:if test="${taskList != null && fn:length(taskList) > 0}"> 
		   <table class="table">
			 <caption>
				 <h3>Feed数据同步信息</h3>
			 </caption>
			<thead>
				<tr>
				     <th width="5%">编号</th>
					<th width="15%">更新时间</th>
					<th width="15%">同步状态</th>
					<th width="30%">执行详情</th>
					<th width="20%">执行时间</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<c:forEach var="task" items="${taskList}">
			   	<tr>
			   	   <td>${task.id}</td>
					<td>
					  <%-- ${wf:nameForTaskType(task.type)}  --%>
					   <c:if test="${task.gmtModified != null}">
		                        <fmt:formatDate value="${task.gmtModified}"  pattern="yyyy-MM-dd HH:mm:ss"/>
		                </c:if>
					</td>
					<td>
					  <span style="font-size:14px;">
					    <c:if test="${task.status == 0}">
					        <c:if test="${task.lastExecuteTime == null}">
					               待执行
					        </c:if>
					        <c:if test="${task.lastExecuteTime != null}">
					           <c:choose>
					               <c:when test="${wf:isAfter(task.scheduleTime, task.lastExecuteTime)}">
					                   本次执行结束，下次待执行。
					               </c:when>
					               <c:otherwise>
					                   待执行
					               </c:otherwise>
					           </c:choose>
					        </c:if>
					    </c:if>
					    <c:if test="${task.status == 1}">
					         执行结束
					    </c:if>
					   </span>
					</td>
					<td>
					  <span style="font-size:14px;">
					      ${task.executeInfo}
					   </span>
					</td>
					<td>
					   <span style="font-size:12px;">
					     上:<c:if test="${task.lastExecuteTime != null}">
					           <fmt:formatDate value="${task.lastExecuteTime}"  pattern="MM/dd HH:mm:ss"/>
					     </c:if>
					    </span>
					     <br/>
					     <span style="font-size:12px;">
					     下:<c:if test="${task.scheduleTime  != null}">
					       <fmt:formatDate value="${task.scheduleTime}"  pattern="MM/dd HH:mm:ss"/>
					      </c:if>
					    </span>
					   
					   
					</td>
					<td>
					     <c:if test="${task.status != 0}">
					          <a href="#" class="start-task-button"  data-id="${task.id}">启动</a>
					     </c:if>
					   
					    <br/>
					     <c:if test="${task.status != 1}">
					        <a href="#" class="end-task-button"  data-id="${task.id}">结束</a>
					     </c:if>
					    <br/>
					     <c:if test="${task.status != -1}">
					         <a href="#" class="delete-task-button"  data-id="${task.id}">删除</a>
					     </c:if>
					     <br/>
					       <a target="_blank" href="articleList.html?feedId=${task.relateId}">文章</a>
					</td>
					
			   </tr>
			</c:forEach>
		  </table>
		  <c:url var="queryUrl" value="dataSynInfo.html?feedId=${query.relateId}&status=${query.status}"/>
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

<form  id="deleteTaskForm" method="post" action="updateTask.html">
      <input id="deleteTaskId" name="id"  value="id" type="hidden"/>
       <input name="status"  value="-1" type="hidden"/>
      ${csrfToken.hiddenField}
</form>


 <script type="text/javascript">
	     $(document).ready(function(){
				$(".delete-task-button").click(function(obj){
						if(!confirm("确认删除此任务")){
							return;
						}
						var dataId = $(this).attr("data-id");
						$("#deleteTaskId").val(dataId);
						$("#deleteFeedForm").submit();
				});
				$(".end-task-button").click(function(obj){
					if(!confirm("确认结束此任务")){
						return;
					}
					var dataId = $(this).attr("data-id");
					$("#endTaskId").val(dataId);
					$("#endTaskForm").submit();
			  });
				$(".start-task-button").click(function(obj){
					if(!confirm("确认启动此任务")){
						return;
					}
					var dataId = $(this).attr("data-id");
					$("#startTaskId").val(dataId);
					$("#startTaskForm").submit();
			});
		  });
</script>    



<input type="hidden"  id="highLightMenu" value="h_dataSynInfo"/>     
<%@include file="../common/footer.jsp"%>