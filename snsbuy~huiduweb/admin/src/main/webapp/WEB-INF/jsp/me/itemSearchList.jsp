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
            <form class="form-search" action="itemSearchList.html" method="get">
             <input name="keyword" value="${keyword}" type="text" class="input-large" style="height:30px;font-size:18px;" placeholder="请输入商品关键字">
                <button type="submit" class="btn  btn-large">查询商品</button>
            </form>
          </div>
          <c:if test="${itemList == null or fn:length(itemList) <= 0}">
		      <c:if test="${message == null}">
		           <div class="alert alert-info">亲，商品为空。</div>
		      </c:if>
		      <c:if test="${message != null}">
		          <div class="alert alert-info">${message}</div>
		      </c:if>
		  </c:if>
		  <c:if test="${itemList != null and fn:length(itemList) > 0}">
             <table class="table"  width="900px;">
	           <caption><h3>店铺上架商品列表</h3></caption>
	            <thead>
	                <tr>
	                 <th  width="50%">名称</th>
	                 <th width="10%">价格</th>
	                 <th width="10%">库存</th>
	                 <th width="10%">查看</th>
	                 <th width="20%">同步时间</th>
	                </tr>
	            </thead>
	         
	            <c:forEach var="item" items="${itemList}">
		            <tr>
		                 <td>${item.title}</td>
		                 <td>${item.price}</td>
		                 <td>${item.num}</td>
		                 <td>
		                     <a href="${configure.taobaoItemServer}/item.htm?id=${item.itemId}" target="_blank">详情</a>
		                 </td>
		                 <td>
		                    <c:if test="${item.gmtModified != null}">
		                       <fmt:formatDate value="${item.gmtModified}"  pattern="yyyy-MM-dd HH:mm:ss"/>
		                    </c:if>
		                    <c:if test="${item.gmtModified == null}">
		                        <fmt:formatDate value="${item.gmtCreate}"  pattern="yyyy-MM-dd HH:mm:ss"/>
		                    </c:if>
		                 </td>
		            </tr>  
	            </c:forEach>
	          </table>
	          <c:url var="queryUrl" value="itemSearchList.html" >
                 <c:param name="keyword" value="${keyword}" />
               </c:url>
               
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
				    
				  </ul>
			</div>
	          
          </c:if>

</div>
<input type="hidden"  id="highLightMenu" value="h_index"/>        
<%@include file="../common/footer.jsp" %>