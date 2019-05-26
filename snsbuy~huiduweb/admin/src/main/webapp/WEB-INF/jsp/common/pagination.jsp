<%@page pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 设置query变量，以及queryUrl --%>
    <div class="pagination" style="text-align:center">
				  <ul>
				   <!-- 上一页 -->
				    <c:if test="${query.currentPageNum > 1}">
				           <li><a href="${queryUrl}&pageNum=${query.currentPageNum - 1}">上一页</a></li>
				    </c:if>
				    
				    <!-- 前五页 -->
				    <c:set var="pageNum" value="${query.currentPageNum - 5}"></c:set>
				    
				    <c:if test="${pageNum < 1}">
				       <c:set var="pageNum" value="1"/>
				    </c:if>
				    
				   <c:forEach var="num" begin="${pageNum}" end="${query.currentPageNum - 1}" step="1">
				      <li><a href="${queryUrl}&pageNum=${num}">${num}</a></li>
				   </c:forEach>
				   
				   <!-- 当前页 -->
				    <c:if test="${query.totalPageNum > 1}">
				       <li><span>${query.currentPageNum}</span></li>
				    </c:if>
				   
				   <!-- 后五页 -->
				    <c:set var="pageNum" value="${query.currentPageNum + 5}"></c:set>
				    <c:if test="${pageNum > query.totalPageNum}">
				       <c:set var="pageNum" value="${query.totalPageNum}"/>
				    </c:if>
				    <c:forEach var="num"  begin="${query.currentPageNum + 1}"  end="${pageNum}" step="1">
				      <li><a href="${queryUrl}&pageNum=${num}">${num}</a></li>
				    </c:forEach>
				    
				   <!-- 下一页 -->
				    <c:if test="${query.currentPageNum < query.totalPageNum}">
				        <li><a href="${queryUrl}&pageNum=${query.currentPageNum + 1}">下一页</a></li>
				    </c:if>
				    <!-- 总页数 -->
				    <li> <span>共${query.totalPageNum}页</span></li>
				    <li> <span>总计${query.totalCount}条</span></li>
				    
				  </ul>
	</div>